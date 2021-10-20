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
 * 支付订单表实体类
 *
 * @author BladeX
 * @since 2021-07-22
 */
@Data
@TableName("t_pay_order")
@ApiModel(value = "PayOrder对象", description = "支付订单表")
public class PayOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "用户id")
		private Long memberId;
	/**
	* 1会员充值，2保障金
	*/
		@ApiModelProperty(value = "1会员升级，2会员续费，3vin订单查询")
		private Integer type;

	/**
	* 微信支付订单号
	*/
		@ApiModelProperty(value = "微信支付订单号")
		private String outTradeNo;
	/**
	* 支付金额
	*/
		@ApiModelProperty(value = "支付金额")
		private Double payMoney;

	@ApiModelProperty(value = "描述")
	private String remark;
	@ApiModelProperty(value = "对应会员等级权限")
	private Integer memberLv;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员权益id")
	private Integer rightsId;
}
