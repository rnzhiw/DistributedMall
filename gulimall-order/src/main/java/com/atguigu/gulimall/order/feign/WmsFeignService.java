package com.atguigu.gulimall.order.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WmsFeignService {

    /**
     * 查询sku是否有库存
     * @param skuIds
     * @return
     */
    @PostMapping("/ware/waresku/hasstock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);

    @GetMapping("/ware/wareinfo/fare")
    R getFare(@RequestParam("addrId") Long addrId);

    /**
     * 锁定库存
     * @param vo
     *
     * 库存解锁的场景
     *      1）、下订单成功，订单过期没有支付被系统自动取消或者被用户手动取消，都要解锁库存
     *      2）、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     *      3）、
     *
     * @return
     */
    @PostMapping(value = "/ware/waresku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo vo);
}
