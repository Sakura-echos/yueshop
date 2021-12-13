package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

