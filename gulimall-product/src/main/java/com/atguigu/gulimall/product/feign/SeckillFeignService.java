package com.atguigu.gulimall.product.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.fallback.SeckillFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "gulimall-seckill",fallback = SeckillFeignServiceFallBack.class)
public interface SeckillFeignService {
    @ResponseBody
    @GetMapping("/sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
