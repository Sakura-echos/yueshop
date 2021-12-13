package com.yueshop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:09:56
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

