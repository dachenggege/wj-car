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

import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.Shop;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户门店表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShopDTO extends Shop {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "店主名称")
	private String shopMember;
	@ApiModelProperty(value = "门店成员数量")
	private Integer staffNum;
	@ApiModelProperty(value = "门店车源数量")
	private Integer shopCarNum;
	@ApiModelProperty(value = "门店联盟数量")
	private Integer alliedNum;
	@ApiModelProperty(value = "联盟车车源数量")
	private Integer alliedCarNum;
}
