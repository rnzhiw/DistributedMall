package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:22:08
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
