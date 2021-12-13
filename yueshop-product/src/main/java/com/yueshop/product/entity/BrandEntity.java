package com.yueshop.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.yueshop.common.valid.AddGroup;
import com.yueshop.common.valid.ListValid;
import com.yueshop.common.valid.UpdateGroup;
import com.yueshop.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改品牌时，品牌id是必须提供的",groups = {UpdateGroup.class})
	@Null(message = "添加品牌时，品牌id是必须为null",groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotEmpty(message = "品牌不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(message = "logo不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@URL(message = "logo必须是一个合法的url请求地址",groups = {AddGroup.class,UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValid(vals = {0,1},groups = {AddGroup.class,UpdateGroup.class,UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(message = "检索首字母不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索字段必须是大小写字母",groups = {AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@Min(value=0,message = "排序不能为空且不小于0",groups = {AddGroup.class,UpdateGroup.class})
	private Integer sort;

}
