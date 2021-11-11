package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 12:58:30
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
