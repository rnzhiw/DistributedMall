package com.atguigu.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:09:46
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 返回某个会员的收货地址列表
     * @param memberId
     * @return
     */
    List<MemberReceiveAddressEntity> getAddress(Long memberId);
}

