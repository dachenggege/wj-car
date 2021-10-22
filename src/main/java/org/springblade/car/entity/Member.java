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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表实体类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Data
@TableName("t_member")
@ApiModel(value = "Member对象", description = "用户表")
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	* 用户角色1游客,2个人,3商家
	*/
		@ApiModelProperty(value = "用户角色1游客,2个人,3商家")
		private Integer roletype;
	/**
	* 微信会话key
	*/
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
	* 省
	*/
		@ApiModelProperty(value = "省")
		private String provinceName;
	/**
	* 用户个人资料填写的城市
	*/
		@ApiModelProperty(value = "用户个人资料填写的城市")
		private String city;
	/**
	* 市
	*/
		@ApiModelProperty(value = "市")
		private String cityName;
	/**
	* 区县
	*/
		@ApiModelProperty(value = "区县")
		private String county;
	/**
	* 区县
	*/
		@ApiModelProperty(value = "区县")
		private String countyName;
	/**
	* 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
	*/
		@ApiModelProperty(value = "用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效")
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


	@ApiModelProperty(value = "车行经度")
	private Double lng;

	@ApiModelProperty(value = "车行维度")
	private Double lat;

	/**
	* 证件照多个用英文逗号分隔
	*/
		@ApiModelProperty(value = "证件照多个用英文逗号分隔")
		private String certificate;
	/**
	* 个人会员审核状态1审核中,2审核通过，3审核不通过
	*/
		@ApiModelProperty(value = "个人会员审核状态1审核中,2审核通过，3审核不通过")
		private Integer personAuditStatus;
	/**
	* 个人会员审核时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@ApiModelProperty(value = "个人会员审核时间")
		private Date personAuditTime;
	/**
	* 个人会员审核不通过原因
	*/
		@ApiModelProperty(value = "个人会员审核不通过原因")
		private String personNopassnotice;
	/**
	* 公司名称
	*/
		@ApiModelProperty(value = "公司名称")
		private String company;
	/**
	* 法人
	*/
		@ApiModelProperty(value = "法人")
		private String corporate;
	/**
	* 公司证件照多个用英文逗号分隔
	*/
		@ApiModelProperty(value = "公司证件照多个用英文逗号分隔")
		private String companyCertificate;
	/**
	* 商家会员审核状态1审核中,2审核通过，3审核不通过
	*/
		@ApiModelProperty(value = "商家会员审核状态1审核中,2审核通过，3审核不通过")
		private Integer companyAuditStatus;
	/**
	* 商家会员审核时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@ApiModelProperty(value = "商家会员审核时间")
		private Date companyAuditTime;
	/**
	* 商家会员审核不通过原因
	*/
		@ApiModelProperty(value = "商家会员审核不通过原因")
		private String companyNopassnotice;

	/**
	 * 会员认证等级
	 */
	@ApiModelProperty(value = "认证等级")
	private Integer certificationLv;
	/**
	* 会员等级
	*/
		@ApiModelProperty(value = "会员等级")
		private Integer memberLv;

		@ApiModelProperty(value = "会员是否到期")
		private Boolean isExpiry;

	/**
	* 会员有效期
	*/
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
		@ApiModelProperty(value = "会员有效期")
		private Date expiryDate;
	private String vi;
	private String encryptedData;
	/**
	* 最后登入时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "最后登入时间")
		private Date lastLogin;
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
