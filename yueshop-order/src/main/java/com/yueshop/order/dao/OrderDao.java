package com.yueshop.order.dao;

import com.yueshop.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:58:44
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
