package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCategoryId(Map<String, Object> params, Long categoryId);

    Long[] getCatelogPath(Long categoryId);
}


