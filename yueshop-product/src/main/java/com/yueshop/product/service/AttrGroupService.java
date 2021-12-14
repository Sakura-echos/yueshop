package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.AttrGroupEntity;
import com.yueshop.product.entity.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCategory(Map<String, Object> params, Long categoryId);

    Long[] getCatelogPath(Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId);
}

