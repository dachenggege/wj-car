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
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实体类
 *
 * @author BladeX
 * @since 2021-09-23
 */
@Data
@TableName("t_vin_parse")
@ApiModel(value = "VinParse对象", description = "VinParse对象")
public class VinParse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty("VIN号")
	private String vin;
	@TableField("res")
	private String res;

	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createTime;

}
