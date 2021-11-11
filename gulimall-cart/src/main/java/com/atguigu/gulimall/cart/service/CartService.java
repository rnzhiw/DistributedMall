package com.atguigu.gulimall.cart.service;

import com.atguigu.gulimall.cart.vo.CartItemVo;
import com.atguigu.gulimall.cart.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {

    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVo getCartItem(Long skuId);

    /**
     * 获取整个购物车
     * @return
     */
    CartVo getCart() throws ExecutionException, InterruptedException;

    /**
     * 清除购物车中的内容
     * @param cartKey
     */
    void clearCartInfo(String cartKey);

    /**
     * 勾选购物项
     * @param skuId
     * @param checked
     */
    void checkItem(Long skuId, Integer checked);

    /**
     * 修改购物项数量
     * @param skuId
     * @param num
     */
    void changeItemCount(Long skuId, Integer num);

    /**
     * 删除购物项
     * @param skuId
     */
    void deleteIdCartInfo(Integer skuId);

    /**
     * 获取当前用户的购物车中的所有购物项
     * @return
     */
    List<CartItemVo> getUserCartItems();
}
