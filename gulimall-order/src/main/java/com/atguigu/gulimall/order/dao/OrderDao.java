package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:22:08
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(@Param("orderSn") String orderSn,@Param("code") Integer code,@Param("payType") Integer payType);
}
