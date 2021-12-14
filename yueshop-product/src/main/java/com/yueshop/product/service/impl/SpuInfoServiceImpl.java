package com.yueshop.product.service.impl;

import com.yueshop.product.vo.SpuSaveVo;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.SpuInfoDao;
import com.yueshop.product.entity.SpuInfoEntity;
import com.yueshop.product.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    //商品数据发布添加功能
    @Override
    @Transactional
    public void saveSpuInfo(SpuSaveVo vo) {
        //1、保存商品的spu基本信息 pms_spu_info （描述图片、图片集）
        //2、保存spu的描述图片 pms_spu_info_desc
        //3、保存spu的图片集 pms_spu_images
        //4、保存spu的基本属性 【包含了属性值 pms_product_attr_value】

        //5、保存spu的积分信息  【会员的数据库 yueshop_sms  sms_spu_bounds 】

        //6、保存sku信息
            //6.1sku的基本信息  pms_sku_info
            //6.2sku的图片信息  pms_sku_images
            //6.3sku的销售属性  pms_sku_sale_attr_value
            //6.4sku的优惠，满减、会员价 【跨库存储 【选做】】

    }

}