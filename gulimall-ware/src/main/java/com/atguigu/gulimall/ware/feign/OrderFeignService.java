package com.atguigu.gulimall.ware.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-order")
public interface OrderFeignService {
    /**
     * 根据订单编号查询订单状态
     * @param orderSn
     * @return
     */
    @GetMapping(value = "/order/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);
}
