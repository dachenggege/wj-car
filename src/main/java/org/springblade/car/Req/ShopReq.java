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
package org.springblade.car.Req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.entity.Shop;

import java.util.Date;
import java.util.List;

/**
 * 用户门店表视图实体类
 *
 * @author BladeX
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ShopReq对象", description = "用户门店表")
public class ShopReq extends Shop {
	private static final long serialVersionUID = 1L;

	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date startCreateTime;
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date endCreateTime;


	private List<String> areas;
	private List<String> noareas;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
}
