package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.constant.ProductConstant;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        // 保存基本数据
        this.save(attrEntity);

        // 如果是基本属性，则要保存属性与属性组的关系
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 保存属性数属性组的关联关系
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

//        System.out.println("base");
//        System.out.println("base".equalsIgnoreCase(type));

        if(catelogId != 0) {
            queryWrapper.eq("catelog_id",catelogId);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {

            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            if("base".equalsIgnoreCase(type)) {
                // 设置分类和分组的名字
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
                if (attrId != null) {
                    Long attrGroupId = attrId.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    if(attrGroupEntity == null) {
                        return null;
                    }
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());

        // 将respVos设置到pageUtils中
        pageUtils.setList(respVos);

        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo attrRespVo = new AttrRespVo();

        // 先根绝attrId查到attr的详细信息
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,attrRespVo);

        // 如果是基本类型的话
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 设置分组信息
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if(relationEntity != null) {
                attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
                // 根据分组id得到分组的详细信息
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if(attrGroupEntity != null) {
                    System.out.println("1111111111111111111111111111111111");
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        // 设置分类信息
        // 当前为子分类catelogId
        Long catelogId = attrEntity.getCatelogId();
        // 查找分类的完整路径
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrRespVo.setCatelogPath(catelogPath);

        // 设置分类名
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(categoryEntity != null) {
            attrRespVo.setCatelogName(categoryEntity.getName());
        }

        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        // 基本信息修改
        this.updateById(attrEntity);
//        System.out.println(attr.getAttrGroupId());

        // 如果是基本类型的话
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 进行修改操作
            // 修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());

            // 判断是新增属性名还是更新属性名
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if(count > 0) {
                // 更新关联的数据信息
                relationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            } else {
                // 新增关联的数据信息
                relationDao.insert(relationEntity);
            }
        }

    }

    /**
     * 根据attrgroup_id查找关联的所有基本属性（规格参数）
     * @param attrgroupId 属性组id
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        // 先去attrattrgroup的关联表中根据attrgroupid查找所有对应的attrid
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));

        // 查出所有的attrIds的集合
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if(attrIds == null || attrIds.size() == 0) {
            return null;
        }

        // 根据所有的attrIds查出所有的attrEntities
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);

        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        // TODO 删除属性组和属性的关联还有bug
        // 将vos转成AttrAttrRelationEntities的List形式
        //relationDao.delete(new QueryWrapper<>().eq("attr_id",1L).eq("attr_group_id",1L));
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        relationDao.deleteBatchRelation(entities);
    }

    /**
     * 获取当前分组没有关联的所有属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        //获取当前分类的id
        Long catelogId = attrGroupEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1）、当前分类下的其它分组
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));

        //获取到所有的attrGroupId
        List<Long> collect = groupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());


        //2.2）、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList
                (new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));

        List<Long> attrIds = groupId.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3）、从当前分类的所有属性移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        if (attrIds != null && attrIds.size() > 0) {
            queryWrapper.notIn("attr_id", attrIds);
        }

        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((w) -> {
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);


        return pageUtils;
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {

        return baseMapper.selectSearchAttrsIds(attrIds);

    }

}