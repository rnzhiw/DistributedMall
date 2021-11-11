package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.vo.SkuItemSaleAttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SkuSaleAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 09:47:49
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId);

    List<String> getSkuSaleAttrValuesAsStringList(Long skuId);
}

