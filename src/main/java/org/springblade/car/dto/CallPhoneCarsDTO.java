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
import lombok.Data;
import org.springblade.car.entity.Cars;

/**
 * 车源表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
public class CallPhoneCarsDTO{
	private static final long serialVersionUID = 1L;


	private Long id;
	/**
	 * 1主页咨询,2车源联系,3论坛咨询,4客服咨询
	 */
	@ApiModelProperty(value = "1主页咨询,2车源联系,3论坛咨询,4客服咨询")
	private String typename;
	/**
	 * 1主页咨询,2车源联系,3论坛咨询,4客服咨询
	 */
	@ApiModelProperty(value = "1主页咨询,2车源联系,3论坛咨询,4客服咨询")
	private Integer type;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long memberId;
	/**
	 * 车源id
	 */
	@ApiModelProperty(value = "车源id")
	private Long carsId;
	/**
	 * 帖子id
	 */
	@ApiModelProperty(value = "帖子id")
	private Long forumId;

	@ApiModelProperty(value = "所主手机号码")
	private String phone;

	@ApiModelProperty(value = "所主名字")
	private String memberName;


	@ApiModelProperty(value = "咨询手机号码")
	private String callphone;

	@ApiModelProperty(value = "咨询者名字")
	private String callmemberName;


}
