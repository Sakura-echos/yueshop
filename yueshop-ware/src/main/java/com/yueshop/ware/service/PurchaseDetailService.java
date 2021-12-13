package com.yueshop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:09:56
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

