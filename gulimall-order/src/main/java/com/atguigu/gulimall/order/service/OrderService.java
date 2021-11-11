package com.atguigu.gulimall.order.service;

import com.atguigu.common.to.mq.SeckillOrderTo;
import com.atguigu.gulimall.order.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.order.entity.OrderEntity;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:22:08
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页返回的数据
     * @return
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 下单
     * @param vo
     * @return
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    OrderEntity getOrderByOrderSn(String orderSn);

    /**
     * 定时关闭订单
     * @param orderEntity
     */
    void closeOrder(OrderEntity orderEntity);

    /**
     * 获取当前订单的支付信息
     * @param orderSn
     * @return
     */
    PayVo getOrderPay(String orderSn);

    /**
     * 查询当前用户的所有订单信息
     * @param params
     * @return
     */
    PageUtils queryPageWithItem(Map<String, Object> params);

    String handlePayResult(PayAsyncVo asyncVo);

    void createSeckillOrder(SeckillOrderTo orderTo);
}

