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

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 门店成员表实体类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Data
@ApiModel(value = "门店成员及权益限", description = "门店成员及权益限")
public class ShopMemberRoleRightDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "用户ID")
	private Long memberId;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键角色id")
	private Long id;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "是否可添加删除店员")
	private Boolean isEditStaff;
	@ApiModelProperty(value = "是否可编辑门店号码")
	private Boolean isEditShopPhone;
	@ApiModelProperty(value = "是否可发布车源")
	private Boolean isPublishCar;
	@ApiModelProperty(value = "是否可编辑车源")
	private Boolean isEditCar;
	@ApiModelProperty(value = "是否可下架车源")
	private Boolean isDownCar;
	@ApiModelProperty(value = "是否可查看成本价")
	private Boolean isLookPcostPrice;
	@ApiModelProperty(value = "是否可查看车源咨询电话记录")
	private Boolean isLookCarCall;
	@ApiModelProperty(value = "是否可查看车源浏览记录")
	private Boolean isLookCarBrowse;
	@ApiModelProperty(value = "是否可查找联盟车源")
	private Boolean isLookAlliedCar;
	@ApiModelProperty(value = "是否可申请取消门店联盟")
	private Boolean isEditShopAllied;


}
