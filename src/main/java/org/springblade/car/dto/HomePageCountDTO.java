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
 *
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
public class HomePageCountDTO{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "新增注册")
	private Integer newRegister;
	@ApiModelProperty(value = "新增会员")
	private Integer newMember;
	@ApiModelProperty(value = "新增游客")
	private Integer newVisitor;
	@ApiModelProperty(value = "新增车源")
	private Integer newCar;
	@ApiModelProperty(value = "新增门店")
	private Integer newShop;
	@ApiModelProperty(value = "新增帖子")
	private Integer newForum;



}
