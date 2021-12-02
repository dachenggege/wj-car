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
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 车系表实体类
 *
 * @author BladeX
 * @since 2021-07-24
 */
@Data
@TableName("t_series")
@ApiModel(value = "Series对象", description = "车系表")
public class Series extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 车系名称
	*/
		@ApiModelProperty(value = "车系名称")
		private String seriesName;
	/**
	* 车系分组
	*/
		@ApiModelProperty(value = "车系分组")
		private String seriesGroupname;
	/**
	* 新能源
	*/
		@ApiModelProperty(value = "新能源")
		private Boolean isGreen;
	/**
	* 关联品牌
	*/
		@ApiModelProperty(value = "关联品牌")
		private Integer brandId;

	@ApiModelProperty(value = "品牌名称")
	private String brandName;
	/**
	* 排序
	*/
		@ApiModelProperty(value = "排序")
		private Integer listorder;
	/**
	* 是否显示
	*/
		@ApiModelProperty(value = "是否显示")
		private Boolean open;

}
