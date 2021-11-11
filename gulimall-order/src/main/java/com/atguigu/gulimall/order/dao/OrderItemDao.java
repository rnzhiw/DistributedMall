package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:22:08
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
