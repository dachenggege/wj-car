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
 * 车源收藏实体类
 *
 * @author BladeX
 * @since 2021-08-12
 */
@Data
@TableName("t_cars_collect")
@ApiModel(value = "CarsCollect对象", description = "车源收藏")
public class CarsCollect extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 车源id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "车源id")
		private Long carId;
	/**
	* 收藏用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "收藏用户id")
		private Long memberId;
	/**
	* 是否点赞
	*/
		@ApiModelProperty(value = "是否收藏")
		private Boolean isCollect;

}
