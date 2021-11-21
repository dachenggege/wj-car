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
package org.springblade.car.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.Forum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.springblade.car.entity.ForumComment;

import java.util.Date;
import java.util.List;

/**
 * 视图实体类
 *
 * @author BladeX
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ForumVO对象", description = "ForumVO对象")
public class ForumVO extends Forum {
	private static final long serialVersionUID = 1L;
	private List<String> areas;
	private List<String> noareas;

	@ApiModelProperty(value = "真实姓名")
	private String name;
	/**
	 * 微信用户昵称
	 */
	@ApiModelProperty(value = "微信用户昵称")
	private String nickname;

	@ApiModelProperty(value = "手机号码")
	private String phone;
	/**
	 * 用户个人资料填写的省份
	 */
	@ApiModelProperty(value = "用户个人资料填写的省份")
	private String province;
	/**
	 * 用户个人资料填写的城市
	 */
	@ApiModelProperty(value = "用户个人资料填写的城市")
	private String city;

	@ApiModelProperty(value = "区县")
	private String county;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "用户头像")
	private String headimgurl;

	@ApiModelProperty(value = "点赞者id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long likeMember;

	@ApiModelProperty(value = "是否点赞")
	private Boolean isLike;

	@ApiModelProperty(value = "评论列表")
	private List<ForumCommentVO> forumCommentList;
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date startCreateTime;
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date endCreateTime;

}
