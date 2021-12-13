package com.yueshop.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:14:01
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

