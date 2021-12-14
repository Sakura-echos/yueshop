package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:02
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

