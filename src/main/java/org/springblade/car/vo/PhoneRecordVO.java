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

import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.PhoneRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 视图实体类
 *
 * @author BladeX
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PhoneRecordVO对象", description = "PhoneRecordVO对象")
public class PhoneRecordVO extends PhoneRecord {
	private static final long serialVersionUID = 1L;

	private List<String> areas;
	private List<String> noareas;

	@ApiModelProperty(value = "省份id")
	private String province;
	@ApiModelProperty(value = "城市id")
	private String city;
	@ApiModelProperty(value = "区县id")
	private String county;

	@ApiModelProperty(value = "主叫手机")
	private String callPhone;
	@ApiModelProperty(value = "主叫人呢称")
	private String callNickName;

	@ApiModelProperty(value = "主叫人姓名")
	private String callName;

	@ApiModelProperty(value = "接听者手机")
	private String answerPhone;
	@ApiModelProperty(value = "接听者用户名称")
	private String answerName;

}
