package com.yueshop.product.service.impl;

import com.yueshop.product.entity.AttrgGroupRalationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.AttrAttrgroupRelationDao;
import com.yueshop.product.entity.AttrAttrgroupRelationEntity;
import com.yueshop.product.service.AttrAttrgroupRelationService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    //批量添加中间信息记录操作方法【属性分组-属性】
    @Override
    @Transactional
    public void saveBatch(List<AttrgGroupRalationVo> vos) {
        //1、把属性id-属性分组id都抽取到一个集合
        List<AttrAttrgroupRelationEntity> relationEntities = vos.stream().map((item)->{
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item,relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        //2、通过批量添加
        this.saveBatch(relationEntities);
    }

}