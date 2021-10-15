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
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author BladeX
 * @since 2021-09-12
 */
@Data
@TableName("t_phone_record")
@ApiModel(value = "PhoneRecord对象", description = "PhoneRecord对象")
public class PhoneRecord implements Serializable {

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
