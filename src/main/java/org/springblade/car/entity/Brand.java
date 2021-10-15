/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.car.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 品牌表实体类
 *
 * @author BladeX
 * @since 2021-07-24
 */
@Data
@TableName("t_brand")
@ApiModel(value = "Brand对象", description = "品牌表")
public class Brand extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 品牌名称
	*/
		@ApiModelProperty(value = "品牌名称")
		private String brandName;
	/**
	* 品牌图片
	*/
		@ApiModelProperty(value = "品牌图片")
		private String brandPic;
	/**
	* 首字母
	*/
		@ApiModelProperty(value = "首字母")
		private String brandMark;
	/**
	* 英文标识
	*/
		@ApiModelProperty(value = "英文标识")
		private String brandEn;
	/**
	* 是否推荐
	*/
		@ApiModelProperty(value = "是否推荐")
		private Boolean isrecom;
	/**
	* 排序
	*/
		@ApiModelProperty(value = "排序")
		private Integer listorder;
	/**
	* 是否显示
	*/
		@ApiModelProperty(value = "是否显示")
		private Boolean open;


}
