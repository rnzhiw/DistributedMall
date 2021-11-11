package com.atguigu.gulimall.member.controller;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.member.entity.MemberReceiveAddressEntity;
import com.atguigu.gulimall.member.service.MemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 会员收货地址
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:09:46
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController {
    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    /**
     * 根据会员id查询会员的所有地址
     * @param memberId
     * @return
     */
    @GetMapping(value = "/{memberId}/address")
    public List<MemberReceiveAddressEntity> getAddress(@PathVariable("memberId") Long memberId) {

        List<MemberReceiveAddressEntity> addressList = memberReceiveAddressService.getAddress(memberId);

        return addressList;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberReceiveAddressService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getById(id);

        return R.ok().put("memberReceiveAddress", memberReceiveAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberReceiveAddressEntity memberReceiveAddress){
		memberReceiveAddressService.save(memberReceiveAddress);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberReceiveAddressEntity memberReceiveAddress){
		memberReceiveAddressService.updateById(memberReceiveAddress);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberReceiveAddressService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
