package com.atguigu.gulimall.seckill.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     * 信息
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R getSkuInfo(@PathVariable("skuId") Long skuId);
}
