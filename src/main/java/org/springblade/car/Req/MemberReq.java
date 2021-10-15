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
package org.springblade.car.Req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.entity.Member;

import java.util.Date;

/**
 * 用户表视图实体类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MemberReq对象", description = "用户查询请求对象")
public class MemberReq extends Member {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建开始时间")
	private Date startCreateTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建结束时间")
	private Date endCreateTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "会员到期开始时间")
	private Date startExpiryDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "会员结束开始时间")
	private Date endExpiryDate;


}
