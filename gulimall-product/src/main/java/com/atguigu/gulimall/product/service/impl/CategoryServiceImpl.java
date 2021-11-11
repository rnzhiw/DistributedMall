package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1. 查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2. 组装成父子结构
        //找出一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1,menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuById(List<Long> asList) {
        // TODO 1.检查当前被删除的菜单，是否有被其他引用
        baseMapper.deleteBatchIds(asList);
    }

    //返回[2,25,225]的格式
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        // 将路径转置
        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @CacheEvict(value = "category",key = "'level1Categorys'")
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    // 每一个需要缓存的数据都要指定放到哪个缓存
    @Cacheable(value = {"category"},key = "'level1Categorys'")  // 代表当前方法需要缓存。如果缓存中有，发放不用调用，如果缓存中没有，会调用方法，最后将结果放入缓存
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        Long l = System.currentTimeMillis();

        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        System.out.println(System.currentTimeMillis() - l);

        return categoryEntities;
    }

    // TODO 会产生堆外内存溢出
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        /**
         * 空结果缓存：解决缓存穿透
         * 设置过期时间（加随机值）：解决缓存雪崩
         * 加锁：解决缓存击穿
         */

        // 加入缓存逻辑，缓存中存的数据是json字符串

        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");

        if(StringUtils.isEmpty(catalogJSON)) {
            // 如果缓存中没有，从数据库查
            System.out.println("缓存不命中，将要查数据库");
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();

            return catalogJsonFromDb;
        }
        System.out.println("缓存命中，直接返回");

        // 转为我们指定的对象
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

        return result;
    }

    /**
     * 缓存中的数据如何与数据库中的数据保持一致性
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {
        //占分布式锁，去redis占坑
        RLock lock2 = redissonClient.getLock("catalogJson-lock");
        lock2.lock();

        Map<String, List<Catelog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock2.unlock();
        }
        return dataFromDb;

    }

    // 使用redis加锁
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //占分布式锁，去redis占坑
        //用uuid占坑，防止锁过期或者删除了别人的锁
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        if(lock) {
            System.out.println("获取分布式锁成功");
            // 加锁成功...执行业务
            // 设置过期时间
            stringRedisTemplate.expire("lock",30,TimeUnit.SECONDS);
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // 业务做完后解锁
//            stringRedisTemplate.delete("lock");
                // 获取值对比+对比成功删除=原子操作，删除锁必须要保证原子性，使用redis+Lua脚本完成
//            String lockValue = stringRedisTemplate.opsForValue().get("lock");
//            if(uuid.equals(lockValue)) {
//                // 删除我自己的锁
//                stringRedisTemplate.delete("lock");
//            }
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                // 删除锁
                Long lock1 = stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            return dataFromDb;
        } else {
            // 加锁失败...重试.synchronized()
            //自旋的方式
            System.out.println("获取分布式锁失败，等待重试");
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
            return getCatalogJsonFromDbWithRedisLock();
        }
    }

    // 从数据库中获取数据
    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if(!StringUtils.isEmpty(catalogJSON)) {
            // 缓存不为null的话直接返回
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

            return result;
        }

        System.out.println("查询了数据库");

        List<CategoryEntity> selectList = baseMapper.selectList(null);

        // 查出所有1级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList,0L);

        // 封装数据
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 通过每一级的一级分类，查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList,v.getCatId());

            // 二级分类集合
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    // 把当前二级分类的三级分类分装成vo
                    List<CategoryEntity> level3Catlog = getParent_cid(selectList,l2.getCatId());
                    if(level3Catlog != null) {
                        List<Catelog2Vo.Category3Vo> collect = level3Catlog.stream().map(l3 -> {
                            // 封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        // 将数据库中查到的数据放入缓存(在方法结束之前将数据放到缓存中）
        String jsonString = JSON.toJSONString(parent_cid);
        stringRedisTemplate.opsForValue().set("catalogJSON",jsonString,1, TimeUnit.DAYS);


        return parent_cid;
    }


    /**
     * 从数据库读数据
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {

        /**
         * 将数据库的多次查询变为一次查询
         */

        // 本地锁：synchronized(this)
        // 在分布式情况下，想要锁住所有，必须要使用分布式锁

        synchronized (this) {

            String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
            if(!StringUtils.isEmpty(catalogJSON)) {
                // 缓存不为null的话直接返回
                Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

                return result;
            }

            System.out.println("查询了数据库");

            List<CategoryEntity> selectList = baseMapper.selectList(null);

            // 查出所有1级分类
            List<CategoryEntity> level1Categorys = getParent_cid(selectList,0L);

            // 封装数据
            Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                // 通过每一级的一级分类，查到这个一级分类的二级分类
                List<CategoryEntity> categoryEntities = getParent_cid(selectList,v.getCatId());

                // 二级分类集合
                List<Catelog2Vo> catelog2Vos = null;
                if (categoryEntities != null) {
                    catelog2Vos = categoryEntities.stream().map(l2 -> {
                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                        // 把当前二级分类的三级分类分装成vo
                        List<CategoryEntity> level3Catlog = getParent_cid(selectList,l2.getCatId());
                        if(level3Catlog != null) {
                            List<Catelog2Vo.Category3Vo> collect = level3Catlog.stream().map(l3 -> {
                                // 封装成指定格式
                                Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                return category3Vo;
                            }).collect(Collectors.toList());
                            catelog2Vo.setCatalog3List(collect);
                        }
                        return catelog2Vo;
                    }).collect(Collectors.toList());
                }

                return catelog2Vos;
            }));

            // 将数据库中查到的数据放入缓存(在方法结束之前将数据放到缓存中）
            String jsonString = JSON.toJSONString(parent_cid);
            stringRedisTemplate.opsForValue().set("catalogJSON",jsonString,1, TimeUnit.DAYS);


            return parent_cid;
        }


    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
        //return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
        return collect;
    }

    // 225，25,2
    private List<Long> findParentPath(Long catelogId,List<Long> paths) {
        //收集当前节点id
        paths.add(catelogId);

        // 根据catelogId查出categoryEntity的信息
        CategoryEntity categoryEntity = this.getById(catelogId);
        // 如果有父分类，递归查找父节点路径
        if (categoryEntity == null) {
            return null;
        }
        if(categoryEntity.getParentCid() != null) {
            findParentPath(categoryEntity.getParentCid(),paths);
        }
        // 返回路径
        return paths;
    }

    /**
     * 递归查询三级分类
     * @param root 当前分类
     * @param all 所有分类集合
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //找到子菜单
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2) -> {
            //菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }



}