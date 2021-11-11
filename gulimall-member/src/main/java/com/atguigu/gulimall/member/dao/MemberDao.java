package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author rnzhiw
 * @email rnzhiw@qq.com
 * @date 2021-09-12 13:09:46
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
