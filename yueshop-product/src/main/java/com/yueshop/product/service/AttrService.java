package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.AttrEntity;
import com.yueshop.product.entity.AttrRespVo;
import com.yueshop.product.entity.AttrVo;
import com.yueshop.product.entity.AttrgGroupRalationVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addAttrVo(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, String type, Long categoryId);

    AttrRespVo getAttrinfoById(Long attrId);

    void updateAttrById(AttrRespVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrgGroupRalationVo[] vos);

    PageUtils getNoattrRelation(Map<String, Object> params, Long attrgroupId);
}

