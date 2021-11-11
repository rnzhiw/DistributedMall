package com.atguigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 09:47:49
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

