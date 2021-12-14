package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

