package com.yueshop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:58:44
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

