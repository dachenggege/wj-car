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
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author BladeX
 * @since 2021-10-12
 */
@Data
@TableName("t_member_certification")
@ApiModel(value = "MemberCertification", description = "MemberCertification对象")
public class MemberCertification implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键ID
	*/
		@ApiModelProperty(value = "主键ID")
		private Long id;
	/**
	* 用户ID
	*/
		@ApiModelProperty(value = "用户ID")
		private Long memberId;
	/**
	* 用户角色1游客,2个人,3商家
	*/
		@ApiModelProperty(value = "用户角色1游客,2个人,3商家")
		private Integer roletype;
	/**
	* 1初级认证，2高级认证
	*/
		@ApiModelProperty(value = "1初级认证，2高级认证")
		private Integer level;
	/**
	* 真实姓名
	*/
		@ApiModelProperty(value = "真实姓名")
		private String realName;
	/**
	* 身份证正反照
	*/
		@ApiModelProperty(value = "身份证正反照")
		private String idcard;
	/**
	* 营业照
	*/
		@ApiModelProperty(value = "营业照")
		private String certificate;
	/**
	* 支付证件
	*/
		@ApiModelProperty(value = "支付证件")
		private String receipt;


	@ApiModelProperty(value = "审核状态1审核中,2审核通过，3审核不通过")
	private Integer auditStatus;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "审核时间")
	private Date auditTime;

	@ApiModelProperty(value = "审核不通过原因")
	private String nopassnotice;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**
	 * 是否删除
	 */
	@ApiModelProperty(value = "是否删除")
	private Integer isDeleted;

}
