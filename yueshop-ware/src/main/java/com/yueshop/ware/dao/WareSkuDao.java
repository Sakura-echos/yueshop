package com.yueshop.ware.dao;

import com.yueshop.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:09:56
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
