package com.yueshop.coupon.dao;

import com.yueshop.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:12:12
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
