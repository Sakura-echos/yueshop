package com.yueshop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:09:56
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

