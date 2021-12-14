package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.AttrAttrgroupRelationEntity;
import com.yueshop.product.entity.AttrgGroupRalationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatch(List<AttrgGroupRalationVo> vos);
}

