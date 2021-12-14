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

import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.Cars;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车源表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
public class CarsDTO extends  Cars{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "是否收藏")
	private Boolean isCollect;

	@ApiModelProperty(value = "手机号码")
	private String phone1;
	@ApiModelProperty(value = "手机号码")
	private String phone2;
	@ApiModelProperty(value = "手机号码")
	private String phone3;
	@ApiModelProperty(value = "手机号码")
	private String phone4;
	@ApiModelProperty(value = "手机号码")
	private String phone5;

	@ApiModelProperty(value = "发布者名字")
	private String memberName;

	@ApiModelProperty(value = "发布者昵称")
	private String memberNickName;
	/**
	 * 用户角色1游客,2个人,3商家
	 */
	@ApiModelProperty(value = "发布者角色1游客,2个人,3商家")
	private Integer roletype;

	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "发布者等级")
	private Integer memberLv;


	@ApiModelProperty(value = "门店名字")
	private String shopName;

	@ApiModelProperty(value = "门店地址")
	private String shopAddress;

	@ApiModelProperty(value = "门店经度")
	private Double lng;

	@ApiModelProperty(value = "门店维度")
	private Double lat;

	//以下和数据库没有关联

	@ApiModelProperty(value = "浏览次数")
	private Integer viewCount;
	@ApiModelProperty(value = "电话咨询次数")
	private Integer callPhoneCount;

	@ApiModelProperty(value = "认证等级0未认证，1初级认证，2高级认证")
	private Integer certificationLv;

}
