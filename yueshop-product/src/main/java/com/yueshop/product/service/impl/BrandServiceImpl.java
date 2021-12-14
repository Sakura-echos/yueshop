package com.yueshop.product.service.impl;

import com.yueshop.product.service.AttrAttrgroupRelationService;
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

    //获取品牌分类的中间信息业务层对象
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //定义条件构造器的对象
        QueryWrapper wrapper = new QueryWrapper<BrandEntity>();
        //获取key
        String key = (String) params.get("key");
        //判断key是否为空null
        if(!StringUtils.isEmpty(key)){//根据品牌id查询，可以根据品牌名进行模糊查询
            wrapper.eq("brand_id",key);
            wrapper.or();
            wrapper.like("name",key);
        }
        //查询
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetailById(BrandEntity brand) {
        //1、修改品牌内容
        this.updateById(brand);
        //2、判断当前的品牌名是否为空【表示没有进行修改】--如果修改品牌名则需要更改中间信息表的数据
        if(!StringUtils.isEmpty(brand.getName())){//不为空则需要修改
            //调用修改中间信息表的品牌名
            categoryBrandRelationService.updateBrandName(brand.getBrandId(),brand.getName());
        }
    }

}