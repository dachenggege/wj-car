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
package org.springblade.car.wx.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 实体类
 *
 * @author bond
 * @since 2021-04-15
 */
@Data
public class CarServiceOrderPayReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long memberId;

	@ApiModelProperty(value = "微信用户openid")
	private String openid;

	@ApiModelProperty(value = "1会员充值，2vin查询")
	@NotNull
	private Integer type;
	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	@NotNull
	private Double payMoney;

	@ApiModelProperty(value = "支付类型：1现金支付，2分享支付")
	@NotNull
	private Integer paytype;
}
