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
import org.springblade.core.mp.base.BaseEntity;

/**
 * 广告表实体类
 *
 * @author BladeX
 * @since 2021-07-20
 */
@Data
@TableName("t_ad")
@ApiModel(value = "Ad对象", description = "广告表")
public class Ad extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	* 广告名称
	*/
		@ApiModelProperty(value = "广告名称")
		private String name;
	/**
	* 广告类型
	*/
		@ApiModelProperty(value = "广告类型")
		private Integer adtype;
	/**
	* 位置1.首页banner
	*/
		@ApiModelProperty(value = "位置1.首页banner")
		private Integer site;

	@ApiModelProperty(value = "图片地址")
	private String picurl;

	/**
	* 图片宽度
	*/
		@ApiModelProperty(value = "图片宽度")
		private String picwidth;
	/**
	* 图片高度
	*/
		@ApiModelProperty(value = "图片高度")
		private String picheight;
	/**
	* url地址
	*/
		@ApiModelProperty(value = "url地址")
		private String url;
	/**
	* 描述
	*/
		@ApiModelProperty(value = "描述")
		private String urlNote;
	/**
	* 开始时间
	*/
		@ApiModelProperty(value = "开始时间")
		private Integer starttime;
	/**
	* 结束时间
	*/
		@ApiModelProperty(value = "结束时间")
		private Integer endtime;
	/**
	* 是否显示
	*/
		@ApiModelProperty(value = "是否显示")
		private Boolean open;


}
