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
 * 实体类
 *
 * @author BladeX
 * @since 2021-07-28
 */
@Data
@TableName("t_forum_comment")
@ApiModel(value = "ForumComment对象", description = "ForumComment对象")
public class ForumComment extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 帖子id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "帖子id")
		private Long forumId;
	/**
	* 评论者
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "评论者")
		private Long mumberId;
	/**
	* 评论
	*/
		@ApiModelProperty(value = "评论")
		private String comment;

}
