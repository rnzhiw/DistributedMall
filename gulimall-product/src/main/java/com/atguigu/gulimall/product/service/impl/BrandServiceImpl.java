package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 获取key
        String key = (String) params.get("key");
        // 检查brand_id和key是否相等或者name和key是否相等
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
//        System.out.println("11111111111111111111111111111111111111111");
        if(!StringUtils.isEmpty(key)) {
            queryWrapper.eq("brand_id",key).or().like("name",key);
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        // 保持冗余字段的数据一致性
        // 先更新当前表
        this.updateById(brand);
        // 再更新关联表
        if(!StringUtils.isEmpty(brand.getName())) {
            // 同步关联到其他表
            categoryBrandRelationService.upBrand(brand.getBrandId(),brand.getName());

            // TODO 更新其他关联
        }
    }

}