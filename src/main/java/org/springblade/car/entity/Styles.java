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

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 车系表实体类
 *
 * @author BladeX
 * @since 2021-07-24
 */
@Data
@TableName("t_styles")
@ApiModel(value = "Styles对象", description = "车系表")
public class Styles extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 车型名称
	*/
		@ApiModelProperty(value = "车型名称")
		private String stylesName;
	/**
	* 市场价
	*/
		@ApiModelProperty(value = "市场价")
		private BigDecimal stylesPrice;
	/**
	* 年款
	*/
		@ApiModelProperty(value = "年款")
		private String stylesYear;
	/**
	* 最小上牌年份
	*/
		@ApiModelProperty(value = "最小上牌年份")
		private String minRegYear;
	/**
	* 最大上牌年份
	*/
		@ApiModelProperty(value = "最大上牌年份")
		private String maxRegYear;
	/**
	* 排量
	*/
		@ApiModelProperty(value = "排量")
		private String stylesGas;
	private String literType;
	/**
	* 变速箱
	*/
		@ApiModelProperty(value = "变速箱")
		private String gearType;
	/**
	* 排放标准
	*/
		@ApiModelProperty(value = "排放标准")
		private String dischargeStandard;
	/**
	* 新能源
	*/
		@ApiModelProperty(value = "新能源")
		private Boolean isGreen;
	/**
	* 关联品牌
	*/
		@ApiModelProperty(value = "关联品牌")
		private Integer brandId;
		@ApiModelProperty(value = "关联品牌")
		private String brandName;
	/**
	* 关联车系
	*/
		@ApiModelProperty(value = "关联车系")
		private Integer seriesId;
		@ApiModelProperty(value = "关联车系")
		private String seriesName;
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
		@ApiModelProperty(value = "厂商")
		private String groupName;
		@ApiModelProperty(value = "配置")
		private String configuration;


}
