package com.yueshop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:58:44
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

