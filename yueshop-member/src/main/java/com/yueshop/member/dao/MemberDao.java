package com.yueshop.member.dao;

import com.yueshop.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:14:01
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
