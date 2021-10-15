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

/**
 *
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
public class HomePageAllCountDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全部用户")
	private Integer allRegister;
	@ApiModelProperty(value = "全部会员")
	private Integer allMember;
	@ApiModelProperty(value = "全部游客")
	private Integer allVisitor;
	@ApiModelProperty(value = "全部车源")
	private Integer allCar;
	@ApiModelProperty(value = "全部门店")
	private Integer allShop;
	@ApiModelProperty(value = "全部帖子")
	private Integer allForum;

}
