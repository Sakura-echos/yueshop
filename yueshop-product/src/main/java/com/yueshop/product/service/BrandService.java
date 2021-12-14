package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetailById(BrandEntity brand);
}

