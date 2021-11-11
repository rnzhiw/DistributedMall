package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.HomeSubjectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 12:58:30
 */
@Mapper
public interface HomeSubjectDao extends BaseMapper<HomeSubjectEntity> {
	
}
