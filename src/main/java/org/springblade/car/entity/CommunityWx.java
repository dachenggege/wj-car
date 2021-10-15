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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 小程序配置信息实体类
 *
 * @author BladeX
 * @since 2021-07-21
 */
@Data
@TableName("t_community_wx")
@ApiModel(value = "CommunityWx对象", description = "小程序配置信息")
public class CommunityWx extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 应用APPID
	*/
		@ApiModelProperty(value = "应用APPID")
		private String appid;
	/**
	* 微信支付商户号
	*/
		@ApiModelProperty(value = "微信支付商户号")
		private String mchId;
	/**
	* api密钥
	*/
		@ApiModelProperty(value = "api密钥")
		private String signKey;
	/**
	* 支付回调地址
	*/
		@ApiModelProperty(value = "支付回调地址")
		private String notifyUrl;
	/**
	* 服务器公网ip
	*/
		@ApiModelProperty(value = "服务器公网ip")
		private String spbillCreateIp;
	/**
	* 小程序appSecret
	*/
		@ApiModelProperty(value = "小程序appSecret")
		private String appSecret;


}
