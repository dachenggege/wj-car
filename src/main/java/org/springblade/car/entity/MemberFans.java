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

import javax.validation.constraints.NotNull;

/**
 * 实体类
 *
 * @author BladeX
 * @since 2021-10-16
 */
@Data
@TableName("t_member_fans")
@ApiModel(value = "MemberFans对象", description = "MemberFans对象")
public class MemberFans implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
		@ApiModelProperty(value = "主键id")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long id;
	/**
	* 用户id
	*/
		@ApiModelProperty(value = "用户id")
		@JsonSerialize(using = ToStringSerializer.class)
		@NotNull
		private Long memberId;
	/**
	* 粉丝id
	*/
		@ApiModelProperty(value = "粉丝id")
		@JsonSerialize(using = ToStringSerializer.class)
		@NotNull
		private Long fansId;
	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		private LocalDateTime createTime;
	/**
	* 修改时间
	*/
		@ApiModelProperty(value = "修改时间")
		private LocalDateTime updateTime;
	/**
	* 状态
	*/
		@ApiModelProperty(value = "状态")
		private Integer status;
	/**
	* 是否删除
	*/
		@ApiModelProperty(value = "是否删除")
		private Integer isDeleted;


}
