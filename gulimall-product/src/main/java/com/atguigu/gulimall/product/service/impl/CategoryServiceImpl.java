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
        //1. ζ₯εΊζζεη±»
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2. η»θ£ζηΆε­η»ζ
        //ζΎεΊδΈηΊ§εη±»
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
        // TODO 1.ζ£ζ₯ε½εθ’«ε ι€ηθεοΌζ―ε¦ζθ’«εΆδ»εΌη¨
        baseMapper.deleteBatchIds(asList);
    }

    //θΏε[2,25,225]ηζ ΌεΌ
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        // ε°θ·―εΎθ½¬η½?
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

    // ζ―δΈδΈͺιθ¦ηΌε­ηζ°ζ?ι½θ¦ζε?ζΎε°εͺδΈͺηΌε­
    @Cacheable(value = {"category"},key = "'level1Categorys'")  // δ»£θ‘¨ε½εζΉζ³ιθ¦ηΌε­γε¦ζηΌε­δΈ­ζοΌεζΎδΈη¨θ°η¨οΌε¦ζηΌε­δΈ­ζ²‘ζοΌδΌθ°η¨ζΉζ³οΌζεε°η»ζζΎε₯ηΌε­
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        Long l = System.currentTimeMillis();

        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        System.out.println(System.currentTimeMillis() - l);

        return categoryEntities;
    }

    // TODO δΌδΊ§ηε ε€εε­ζΊ’εΊ
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        /**
         * η©Ίη»ζηΌε­οΌθ§£ε³ηΌε­η©Ώι
         * θ?Ύη½?θΏζζΆι΄οΌε ιζΊεΌοΌοΌθ§£ε³ηΌε­ιͺε΄©
         * ε ιοΌθ§£ε³ηΌε­ε»η©Ώ
         */

        // ε ε₯ηΌε­ι»θΎοΌηΌε­δΈ­ε­ηζ°ζ?ζ―jsonε­η¬¦δΈ²

        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");

        if(StringUtils.isEmpty(catalogJSON)) {
            // ε¦ζηΌε­δΈ­ζ²‘ζοΌδ»ζ°ζ?εΊζ₯
            System.out.println("ηΌε­δΈε½δΈ­οΌε°θ¦ζ₯ζ°ζ?εΊ");
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();

            return catalogJsonFromDb;
        }
        System.out.println("ηΌε­ε½δΈ­οΌη΄ζ₯θΏε");

        // θ½¬δΈΊζδ»¬ζε?ηε―Ήθ±‘
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

        return result;
    }

    /**
     * ηΌε­δΈ­ηζ°ζ?ε¦δ½δΈζ°ζ?εΊδΈ­ηζ°ζ?δΏζδΈθ΄ζ§
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {
        //ε εεΈεΌιοΌε»redisε ε
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

    // δ½Ώη¨redisε ι
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //ε εεΈεΌιοΌε»redisε ε
        //η¨uuidε εοΌι²ζ­’ιθΏζζθε ι€δΊε«δΊΊηι
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        if(lock) {
            System.out.println("θ·εεεΈεΌιζε");
            // ε ιζε...ζ§θ‘δΈε‘
            // θ?Ύη½?θΏζζΆι΄
            stringRedisTemplate.expire("lock",30,TimeUnit.SECONDS);
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // δΈε‘εε?εθ§£ι
//            stringRedisTemplate.delete("lock");
                // θ·εεΌε―Ήζ―+ε―Ήζ―ζεε ι€=εε­ζδ½οΌε ι€ιεΏι‘»θ¦δΏθ―εε­ζ§οΌδ½Ώη¨redis+Luaθζ¬ε?ζ
//            String lockValue = stringRedisTemplate.opsForValue().get("lock");
//            if(uuid.equals(lockValue)) {
//                // ε ι€ζθͺε·±ηι
//                stringRedisTemplate.delete("lock");
//            }
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                // ε ι€ι
                Long lock1 = stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            return dataFromDb;
        } else {
            // ε ιε€±θ΄₯...ιθ―.synchronized()
            //θͺζηζΉεΌ
            System.out.println("θ·εεεΈεΌιε€±θ΄₯οΌη­εΎιθ―");
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
            return getCatalogJsonFromDbWithRedisLock();
        }
    }

    // δ»ζ°ζ?εΊδΈ­θ·εζ°ζ?
    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if(!StringUtils.isEmpty(catalogJSON)) {
            // ηΌε­δΈδΈΊnullηθ―η΄ζ₯θΏε
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

            return result;
        }

        System.out.println("ζ₯θ―’δΊζ°ζ?εΊ");

        List<CategoryEntity> selectList = baseMapper.selectList(null);

        // ζ₯εΊζζ1ηΊ§εη±»
        List<CategoryEntity> level1Categorys = getParent_cid(selectList,0L);

        // ε°θ£ζ°ζ?
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // ιθΏζ―δΈηΊ§ηδΈηΊ§εη±»οΌζ₯ε°θΏδΈͺδΈηΊ§εη±»ηδΊηΊ§εη±»
            List<CategoryEntity> categoryEntities = getParent_cid(selectList,v.getCatId());

            // δΊηΊ§εη±»ιε
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    // ζε½εδΊηΊ§εη±»ηδΈηΊ§εη±»εθ£ζvo
                    List<CategoryEntity> level3Catlog = getParent_cid(selectList,l2.getCatId());
                    if(level3Catlog != null) {
                        List<Catelog2Vo.Category3Vo> collect = level3Catlog.stream().map(l3 -> {
                            // ε°θ£ζζε?ζ ΌεΌ
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

        // ε°ζ°ζ?εΊδΈ­ζ₯ε°ηζ°ζ?ζΎε₯ηΌε­(ε¨ζΉζ³η»ζδΉεε°ζ°ζ?ζΎε°ηΌε­δΈ­οΌ
        String jsonString = JSON.toJSONString(parent_cid);
        stringRedisTemplate.opsForValue().set("catalogJSON",jsonString,1, TimeUnit.DAYS);


        return parent_cid;
    }


    /**
     * δ»ζ°ζ?εΊθ―»ζ°ζ?
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {

        /**
         * ε°ζ°ζ?εΊηε€ζ¬‘ζ₯θ―’εδΈΊδΈζ¬‘ζ₯θ―’
         */

        // ζ¬ε°ιοΌsynchronized(this)
        // ε¨εεΈεΌζε΅δΈοΌζ³θ¦ιδ½ζζοΌεΏι‘»θ¦δ½Ώη¨εεΈεΌι

        synchronized (this) {

            String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
            if(!StringUtils.isEmpty(catalogJSON)) {
                // ηΌε­δΈδΈΊnullηθ―η΄ζ₯θΏε
                Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

                return result;
            }

            System.out.println("ζ₯θ―’δΊζ°ζ?εΊ");

            List<CategoryEntity> selectList = baseMapper.selectList(null);

            // ζ₯εΊζζ1ηΊ§εη±»
            List<CategoryEntity> level1Categorys = getParent_cid(selectList,0L);

            // ε°θ£ζ°ζ?
            Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                // ιθΏζ―δΈηΊ§ηδΈηΊ§εη±»οΌζ₯ε°θΏδΈͺδΈηΊ§εη±»ηδΊηΊ§εη±»
                List<CategoryEntity> categoryEntities = getParent_cid(selectList,v.getCatId());

                // δΊηΊ§εη±»ιε
                List<Catelog2Vo> catelog2Vos = null;
                if (categoryEntities != null) {
                    catelog2Vos = categoryEntities.stream().map(l2 -> {
                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                        // ζε½εδΊηΊ§εη±»ηδΈηΊ§εη±»εθ£ζvo
                        List<CategoryEntity> level3Catlog = getParent_cid(selectList,l2.getCatId());
                        if(level3Catlog != null) {
                            List<Catelog2Vo.Category3Vo> collect = level3Catlog.stream().map(l3 -> {
                                // ε°θ£ζζε?ζ ΌεΌ
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

            // ε°ζ°ζ?εΊδΈ­ζ₯ε°ηζ°ζ?ζΎε₯ηΌε­(ε¨ζΉζ³η»ζδΉεε°ζ°ζ?ζΎε°ηΌε­δΈ­οΌ
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

    // 225οΌ25,2
    private List<Long> findParentPath(Long catelogId,List<Long> paths) {
        //ζΆιε½εθηΉid
        paths.add(catelogId);

        // ζ Ήζ?catelogIdζ₯εΊcategoryEntityηδΏ‘ζ―
        CategoryEntity categoryEntity = this.getById(catelogId);
        // ε¦ζζηΆεη±»οΌιε½ζ₯ζΎηΆθηΉθ·―εΎ
        if (categoryEntity == null) {
            return null;
        }
        if(categoryEntity.getParentCid() != null) {
            findParentPath(categoryEntity.getParentCid(),paths);
        }
        // θΏεθ·―εΎ
        return paths;
    }

    /**
     * ιε½ζ₯θ―’δΈηΊ§εη±»
     * @param root ε½εεη±»
     * @param all ζζεη±»ιε
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //ζΎε°ε­θε
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2) -> {
            //θεηζεΊ
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }



}