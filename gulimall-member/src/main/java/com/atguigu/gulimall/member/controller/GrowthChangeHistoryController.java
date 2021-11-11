package com.atguigu.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.member.entity.GrowthChangeHistoryEntity;
import com.atguigu.gulimall.member.service.GrowthChangeHistoryService;



/**
 * 成长值变化历史记录
 *
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:09:46
 */
@RestController
@RequestMapping("member/growthchangehistory")
public class GrowthChangeHistoryController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = growthChangeHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		GrowthChangeHistoryEntity growthChangeHistory = growthChangeHistoryService.getById(id);

        return R.ok().put("growthChangeHistory", growthChangeHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GrowthChangeHistoryEntity growthChangeHistory){
		growthChangeHistoryService.save(growthChangeHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody GrowthChangeHistoryEntity growthChangeHistory){
		growthChangeHistoryService.updateById(growthChangeHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		growthChangeHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
