package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.service.SkuInfoService;
import com.atguigu.gulimall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 查询商品sku的详细信息
     * @param skuId
     * @param model
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {
        System.out.println("准备查询" + skuId);

        SkuItemVo vos = skuInfoService.item(skuId);
//        if(vos.getSeckillSkuVo() == null) {
//            System.out.println("55555555555555555");
//        } else {
//            System.out.println("66666666666666666");
//        }

        model.addAttribute("item",vos);

        return "item";
    }
}
