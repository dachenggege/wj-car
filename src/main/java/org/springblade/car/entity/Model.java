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
 * 级别表实体类
 *
 * @author BladeX
 * @since 2021-07-24
 */
@Data
@TableName("t_model")
@ApiModel(value = "Model对象", description = "级别表")
public class Model extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 名称
	*/
		@ApiModelProperty(value = "名称")
		private String sName;
	/**
	* 图片地址
	*/
		@ApiModelProperty(value = "图片地址")
		private String sPic;
	/**
	* 排序id
	*/
		@ApiModelProperty(value = "排序id")
		private Integer listorder;
		private Boolean open;

		private Boolean isrecom;

}
