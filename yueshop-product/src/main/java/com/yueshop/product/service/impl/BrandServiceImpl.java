package com.yueshop.product.service.impl;

import com.yueshop.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.BrandDao;
import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.service.BrandService;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //条件构造器对象
        QueryWrapper queryWrapper = new QueryWrapper<BrandEntity>();
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.eq("brand_id",key);
            queryWrapper.or();
            queryWrapper.like("name",key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetialById(BrandEntity brand) {
        this.updateById(brand);
        //判断当前品牌是否为空【表示没有进行修改】--如果修改品牌名这需要修改中间表
        if(!StringUtils.isEmpty(brand.getName())){
            categoryBrandRelationService.updateBrandName(brand.getBrandId(),brand.getName());
        }
    }


}