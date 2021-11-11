package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:22:08
 */
@Mapper
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReasonEntity> {
	
}
