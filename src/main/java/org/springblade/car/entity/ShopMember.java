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

/**
 * 门店成员表实体类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Data
@TableName("t_shop_member")
@ApiModel(value = "ShopMember对象", description = "门店成员表")
public class ShopMember implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
		@ApiModelProperty(value = "主键id")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long id;
	/**
	* 门店id
	*/
		@ApiModelProperty(value = "门店id")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long shopId;
	/**
	* 用户id
	*/
		@ApiModelProperty(value = "店员id")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long staffId;
	@ApiModelProperty(value = "店员角色ID")
	private Integer staffRole;

	/**
	* 创建人
	*/
		@ApiModelProperty(value = "创建人")
		private Long createUser;
	/**
	* 创建部门
	*/
		@ApiModelProperty(value = "创建部门")
		private Long createDept;
	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		private LocalDateTime createTime;
	/**
	* 修改人
	*/
		@ApiModelProperty(value = "修改人")
		private Long updateUser;
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
