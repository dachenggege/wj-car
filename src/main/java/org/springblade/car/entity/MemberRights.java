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
 * 会员体系权益表实体类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Data
@TableName("t_member_rights")
@ApiModel(value = "MemberRights对象", description = "会员体系权益表")
public class MemberRights implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
		@ApiModelProperty(value = "主键id")
		private Integer id;
	/**
	* 会员等级
	*/
		@ApiModelProperty(value = "会员等级")
		private Integer level;
	/**
	* 等级名称
	*/
		@ApiModelProperty(value = "等级名称")
		private String levelName;
	/**
	* 费用
	*/
		@ApiModelProperty(value = "费用")
		private Double price;
	/**
	* 用户角色1游客,2个人,3商家
	*/
		@ApiModelProperty(value = "用户角色1游客,2个人,3商家")
		private Integer roletype;
	/**
	* 是否可发布车源
	*/
		@ApiModelProperty(value = "是否可发布车源")
		private Boolean isPublishCar;
	/**
	* 是否可查看车源
	*/
		@ApiModelProperty(value = "是否可查看车源")
		private Boolean isLookCar;
	/**
	* 是否可查看批发价
	*/
		@ApiModelProperty(value = "是否可查看批发价")
		private Boolean isLookPtradePrice;
	/**
	* 发布车源权益-卖价
	*/
		@ApiModelProperty(value = "发布车源权益-卖价")
		private Boolean isPprice;
	/**
	* 发布车源权益-批发价格
	*/
		@ApiModelProperty(value = "发布车源权益-批发价格")
		private Integer isPtradePrice;
	/**
	* 发布车源权益-内部价格
	*/
		@ApiModelProperty(value = "发布车源权益-内部价格")
		private Integer isPinteriorPrice;
	/**
	* 发布车源权益-成本价
	*/
		@ApiModelProperty(value = "发布车源权益-成本价")
		private Integer isPcostPrice;
	/**
	* 发布车源权益-收车时间
	*/
		@ApiModelProperty(value = "发布车源权益-收车时间")
		private Boolean isBuyData;
	/**
	* 发布车源权益-号码个数
	*/
		@ApiModelProperty(value = "发布车源权益-号码个数")
		private Integer showPhoneNum;
	/**
	* 是否可以创建门店
	*/
		@ApiModelProperty(value = "是否可以创建门店")
		private Boolean isCreateShop;
	/**
	* 是否可看车源被浏览记录
	*/
		@ApiModelProperty(value = "是否可看车源被浏览记录")
		private Boolean isCarBrowseList;
	/**
	* 是否可查看车源被电话咨询记录
	*/
		@ApiModelProperty(value = "是否可查看车源被电话咨询记录")
		private Boolean isCarCallList;
	/**
	* 可以创建门店的数量
	*/
		@ApiModelProperty(value = "可以创建门店的数量")
		private Integer createShopNum;
	/**
	* 门店店员数量
	*/
		@ApiModelProperty(value = "门店店员数量")
		private Integer shopMemberNum;
	/**
	* 加入门店数量
	*/
		@ApiModelProperty(value = "加入门店数量")
		private Integer joinShopNum;
	/**
	* 门店结盟数量
	*/
		@ApiModelProperty(value = "门店结盟数量")
		private Integer shopAlliedNum;
	/**
	* 免费识别车辆vin次数
	*/
		@ApiModelProperty(value = "免费识别车辆vin次数")
		private Integer freeVinParseNum;
	/**
	* 每天前N次发朋友圈
	*/
		@ApiModelProperty(value = "每天前N次发朋友圈")
		private Integer freeVinParseNum1;
	/**
	* 每天后N次看视频
	*/
		@ApiModelProperty(value = "每天后N次看视频")
		private Integer freeVinParseNum2;
	private Double vinParsePrice;
	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		private LocalDateTime createTime;
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
