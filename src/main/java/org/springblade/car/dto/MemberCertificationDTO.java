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
package org.springblade.car.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.MemberCertification;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberCertificationDTO extends MemberCertification {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户个人资料填写的省份
	 */
	@ApiModelProperty(value = "用户个人资料填写的省份")
	private String province;
	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String provinceName;
	/**
	 * 用户个人资料填写的城市
	 */
	@ApiModelProperty(value = "用户个人资料填写的城市")
	private String city;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String cityName;
	/**
	 * 区县
	 */
	@ApiModelProperty(value = "区县")
	private String county;
	/**
	 * 区县
	 */
	@ApiModelProperty(value = "区县")
	private String countyName;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phone;
	/**
	 * 车行名称
	 */
	@ApiModelProperty(value = "车行名称")
	private String carDealer;
	/**
	 * 车行地址
	 */
	@ApiModelProperty(value = "车行地址")
	private String dealerAddress;



}
