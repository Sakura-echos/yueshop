package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();
    //逻辑删除分类的方法
    void removeMenuByIds(List<Long> asList);

    void updateDetailById(CategoryEntity category);
}

