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
package org.springblade.car.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.Cars;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

/**
 * 车源表视图实体类
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CarsVO对象", description = "车源表")
public class CarsVO extends Cars {
	private static final long serialVersionUID = 1L;
	private List<String> areas;
	private List<String> noareas;

	private List<Long> memberIds;

	@ApiModelProperty(value = "电话号码")
	private String phone;
	@ApiModelProperty(value = "用户名称")
	private String name;

	@ApiModelProperty(value = "起始价格")
	private String sprice;
	@ApiModelProperty(value = "结束价格")
	private String eprice;
	@ApiModelProperty(value = "排序")
	private String sort;

	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date startCreateTime;
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date endCreateTime;

}
