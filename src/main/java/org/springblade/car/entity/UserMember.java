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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 广告表实体类
 *
 * @author BladeX
 * @since 2021-07-20
 */
@Data
@TableName("t_user_member")
@ApiModel(value = "Ad对象", description = "广告表")
public class UserMember{

	private static final long serialVersionUID = 1L;


		@ApiModelProperty(value = "Id")
		private int id;

		@ApiModelProperty(value = "userId")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long userId;

		@ApiModelProperty(value = "memberId")
		@JsonSerialize(using = ToStringSerializer.class)
		private Long memberId;

}
