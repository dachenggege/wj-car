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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.ShopMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 门店成员表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShopMemberDTO extends ShopMember {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户角色1游客2会员
	 */
	@ApiModelProperty(value = "用户角色1游客；2会员")
	private Integer roletype;


	@ApiModelProperty(value = "微信会话key")
	private String sessionKey;
	/**
	 * 微信用户唯一标识
	 */
	@ApiModelProperty(value = "微信用户唯一标识")
	private String openid;
	/**
	 * 微信用户昵称
	 */
	@ApiModelProperty(value = "微信用户昵称")
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	@ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	private Integer sex;
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
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	@ApiModelProperty(value = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段")
	private String unionid;
	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = "真实姓名")
	private String name;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phone;
	/**
	 * 车行名称
	 */
	@ApiModelProperty(value = "车行名称")
	private String carDealer;
	/**
	 * 车行地址
	 */
	@ApiModelProperty(value = "车行地址")
	private String dealerAddress;
	/**
	 * 证件地址多个用英文逗号分隔
	 */
	@ApiModelProperty(value = "证件地址多个用英文逗号分隔")
	private String certificate;
	/**
	 * 会员审核状态1审核中,2审核通过，3审核不通过
	 */
	@ApiModelProperty(value = "会员审核状态1审核中,2审核通过，3审核不通过")
	private Integer auditStatus;
	/**
	 * 审核时间
	 */
	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditTime;
	/**
	 * 审核不通过原因
	 */
	@ApiModelProperty(value = "审核不通过原因")
	private String nopassnotice;
	/**
	 * 会员有效期
	 */
	@ApiModelProperty(value = "会员有效期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expiryDate;
	@ApiModelProperty(value = "1待支付，2支付完成未填写资料，3资料完成提交,4会员到期需要重缴费")
	private Integer payStatus;

	private String encryptedData;
	private String vi;

	@ApiModelProperty(value = "是否创建门店")
	private Boolean isshop;


	@ApiModelProperty(value = "最后登入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLogin;
}
