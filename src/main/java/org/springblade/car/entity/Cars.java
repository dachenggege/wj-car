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

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 车源表实体类
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
@TableName("t_cars")
@ApiModel(value = "Cars对象", description = "车源表")
public class Cars extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 编号
	*/
		@ApiModelProperty(value = "编号")
		private String pno;

	@ApiModelProperty(value = "车源所属1个人，2门店")
	private Integer vest;
	/**
	 * 会员id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	private Long memberId;

	/**
	 * 会员id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "门店id")
	private Long shopId;
	/**
	* 省份id
	*/
		@ApiModelProperty(value = "省份id")
		private String province;
	/**
	* 城市id
	*/
		@ApiModelProperty(value = "城市id")
		private String city;
	/**
	* 区县id
	*/
		@ApiModelProperty(value = "区县id")
		private String county;

	/**
	* 品牌id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "品牌id")
		private Long brandId;
	/**
	* 车系id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "车系id（对应t_series表id）")
		private Long seriesId;
	/**
	* 车型id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "车型id(对应t_styles表id)")
		private Long stylesId;

	/**
	* 车辆全称
	*/
		@ApiModelProperty(value = "车辆全称")
		private String pallname;
	/**
	* 价格
	*/
		@ApiModelProperty(value = "价格")
		private BigDecimal pprice;
	/**
	* 批发价格
	*/
		@ApiModelProperty(value = "批发价格")
		private BigDecimal ptradePrice;
	/**
	* 亲情价
	*/
		@ApiModelProperty(value = "内部价")
		private BigDecimal pafprice;

		@ApiModelProperty(value = "成本价")
		private BigDecimal pcostprice;

		@ApiModelProperty(value = "收车时间")
		private Date purchaseTime;


	/**
	* 级别id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
		@ApiModelProperty(value = "级别id")
		private Long modelId;
	/**
	* 排量
	*/
		@ApiModelProperty(value = "排量")
		private String pgas;
	/**
	* 行驶里程
	*/
		@ApiModelProperty(value = "行驶里程")
		private BigDecimal pkilometre;
	/**
	* 颜色
	*/
		@ApiModelProperty(value = "颜色")
		private String pcolor;
	/**
	* 国别
	*/
		@ApiModelProperty(value = "国别")
		private String pcountry;
	/**
	* 变速箱
	*/
		@ApiModelProperty(value = "变速箱")
		private String ptransmission;
	/**
	* 排放标准
	*/
		@ApiModelProperty(value = "排放标准")
		private String pemission;
	/**
	* 燃油类型
	*/
		@ApiModelProperty(value = "燃油类型")
		private String pfuel;
	/**
	* 过户次数
	*/
		@ApiModelProperty(value = "过户次数")
		private String pguohu;
	/**
	* 上牌时间
	*/
		@ApiModelProperty(value = "上牌时间")
		private String pontime;
	/**
	* 车主描述
	*/
		@ApiModelProperty(value = "车主描述")
		private String pdetails;
	/**
	* 主图地址
	*/
		@ApiModelProperty(value = "主图地址")
		private String pmainpic;
	/**
	* 图片地址
	*/
		@ApiModelProperty(value = "图片地址")
		private String ppics;
	/**
	* 点击数
	*/
		@ApiModelProperty(value = "点击浏览数")
		private Integer phits;
	/**
	* 是否出售
	*/
		@ApiModelProperty(value = "是否出售")
		private Boolean issell;
	/**
	* 刷新时间
	*/
		@ApiModelProperty(value = "刷新时间")
		private String listtime;
	/**
	* 是否推荐
	*/
		@ApiModelProperty(value = "是否推荐")
		private Boolean isrecom;
	/**
	* 是否显示
	*/
		@ApiModelProperty(value = "是否显示")
		private Boolean isshow;
	/**
	* 是否包含过户费
	*/
		@ApiModelProperty(value = "是否包含过户费")
		private Boolean ptransfer;
	/**
	* 年检到期
	*/
		@ApiModelProperty(value = "年检到期")
		private String pinsurance;
	/**
	* 保险到期
	*/
		@ApiModelProperty(value = "保险到期")
		private String pinspection;
	/**
	* 车辆用途
	*/
		@ApiModelProperty(value = "车辆用途")
		private String pcaruse;
	/**
	* 定期保养
	*/
		@ApiModelProperty(value = "定期保养")
		private String pmaintenance;
	/**
	* 保修服务
	*/
		@ApiModelProperty(value = "保修服务")
		private Boolean isrepair;
	/**
	* 已售时间
	*/
		@ApiModelProperty(value = "已售时间")
		private Integer selltime;
	/**
	* 审核
	*/
		@ApiModelProperty(value = "审核状态1审核中,2审核通过，3审核不通过")
		private Integer auditStatus;
	/**
	* 审核时间
	*/
		@ApiModelProperty(value = "审核时间")
		private Date auditTime;
	/**
	* 不通过原因
	*/
		@ApiModelProperty(value = "不通过原因")
		private String nopassnotice;
	/**
	* 是否降价
	*/
		@ApiModelProperty(value = "是否降价")
		private Boolean isjiangjia;
	/**
	* 亮点配置
	*/
		@ApiModelProperty(value = "亮点配置")
		private String hlconfig;
	/**
	* 亮点配置图片
	*/
		@ApiModelProperty(value = "亮点配置图片")
		private String hlPic;

	@ApiModelProperty(value = "车架号")
	private String pvin;


	@ApiModelProperty(value = "省名称")
	private String	provinceName;
	@ApiModelProperty(value = "市名称")
	private String	cityName;
	@ApiModelProperty(value = "区县名称")
	private String	countyName;
	@ApiModelProperty(value = "品牌名字")
	private String	brandName;
	@ApiModelProperty(value = "车系名字")
	private String	seriesName;
	@ApiModelProperty(value = "车型名字")
	private String	stylesName;
	@ApiModelProperty(value = "车级别名字")
	private String	modelName;
}
