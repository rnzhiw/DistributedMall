package com.atguigu.gulimall.cart.controller;

import com.atguigu.gulimall.cart.interceptor.CartInterceptor;
import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.to.UserInfoTo;
import com.atguigu.gulimall.cart.vo.CartItemVo;
import com.atguigu.gulimall.cart.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 获取当前用户的购物车商品项
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    @ResponseBody
    public List<CartItemVo> getCurrentCartItems() {

        List<CartItemVo> cartItemVoList = cartService.getUserCartItems();

        return cartItemVoList;
    }

    /**
     * 商品是否选中
     * @param skuId
     * @param checked
     * @return
     */
    @GetMapping(value = "/checkItem")
    public String checkItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "checked",required = false) Integer checked) {

        if(skuId == null) {
            return null;
        }

        cartService.checkItem(skuId,checked);

        return "redirect:http://cart.gulimall.com/cart.html";

    }

    /**
     * 改变商品数量
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping(value = "/countItem")
    public String countItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "num") Integer num) {

        cartService.changeItemCount(skuId,num);

        return "redirect:http://cart.gulimall.com/cart.html";
    }

    /**
     * 删除商品信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("skuId") Integer skuId) {

        cartService.deleteIdCartInfo(skuId);

        return "redirect:http://cart.gulimall.com/cart.html";

    }


    /**
     * 去购物车页面的请求
     * 浏览器有一个cookie:user-key 标识用户的身份，一个月过期
     * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份:
     * 浏览器以后保存，每次访问都会带上这个cookie；
     *
     * 登录：session有
     * 没登录：按照cookie里面带来user-key来做
     * 第一次，如果没有临时用户，自动创建一个临时用户
     *
     * @return
     */
    @GetMapping(value = "/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        //快速得到用户信息：id,user-key
         UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
         System.out.println(userInfoTo);

        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart",cartVo);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam(value = "skuId",defaultValue = "0") Long skuId,
                            @RequestParam(value = "num",defaultValue = "0") Integer num,
                            RedirectAttributes attributes) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId,num);

        attributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccessPage.html";
    }

    /**
     * 跳转到添加购物车成功页面
     * 防止通过链接重复增加购物车中的某个商品数量
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping(value = "/addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,
                                       Model model) {
        //重定向到成功页面。再次查询购物车数据即可
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem",cartItemVo);
        return "success";
    }



}
