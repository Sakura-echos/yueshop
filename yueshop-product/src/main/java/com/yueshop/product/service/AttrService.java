package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.AttrEntity;
import com.yueshop.product.entity.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, String type, Long categoryId);
}

