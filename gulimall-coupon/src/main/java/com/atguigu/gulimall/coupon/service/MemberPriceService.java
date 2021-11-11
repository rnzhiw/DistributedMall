package com.atguigu.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 12:58:30
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

