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
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 用户门店表实体类
 *
 * @author BladeX
 * @since 2021-07-22
 */
@Data
@TableName("t_shop")
@ApiModel(value = "Shop对象", description = "用户门店表")
public class Shop extends BaseEntity {

	/**
	* 会员id
	*/
		@ApiModelProperty(value = "会员id")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long memberId;
	/**
	* 手机号码
	*/
		@ApiModelProperty(value = "手机号码")
		private String phone1;
		@ApiModelProperty(value = "手机号码")
		private String phone2;
		@ApiModelProperty(value = "手机号码")
		private String phone3;
		@ApiModelProperty(value = "手机号码")
		private String phone4;
		@ApiModelProperty(value = "手机号码")
		private String phone5;
	/**
	* 门店名称
	*/
		@ApiModelProperty(value = "门店名称")
		@NotEmpty
		private String shopName;
	/**
	* 门店地址
	*/
		@ApiModelProperty(value = "门店地址")
		@NotEmpty
		private String shopAddress;

	@ApiModelProperty(value = "门店经度")
	private Double lng;

	@ApiModelProperty(value = "门店维度")
	private Double lat;
	/**
	* 用户个人资料填写的省份
	*/
		@ApiModelProperty(value = "用户个人资料填写的省份")
		private String province;
	/**
	* 用户个人资料填写的城市
	*/
		@ApiModelProperty(value = "用户个人资料填写的城市")
		private String city;
	/**
	* 区县
	*/
		@ApiModelProperty(value = "区县")
		private String county;
	/**
	* 门店图片地址多个用英文逗号分隔
	*/
		@ApiModelProperty(value = "门店图片地址多个用英文逗号分隔")
		@NotEmpty
		private String shopImg;
	/**
	* 证件地址多个用英文逗号分隔
	*/
		@ApiModelProperty(value = "证件地址多个用英文逗号分隔")
		@NotEmpty
		private String certificate;
	/**
	* 审核状态1审核中,2审核通过，3审核不通过
	*/
		@ApiModelProperty(value = "审核状态1审核中,2审核通过，3审核不通过")
		private Integer auditStatus;
	/**
	* 审核时间
	*/
		@ApiModelProperty(value = "审核时间")
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date auditTime;
	/**
	* 审核不通过原因
	*/
		@ApiModelProperty(value = "审核不通过原因")
		private String nopassnotice;

	@ApiModelProperty(value = "省市区名称")
	private String areaName;

}
