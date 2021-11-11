package com.atguigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 09:47:49
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuImagesEntity> getImagesBySkuId(Long skuId);
}

