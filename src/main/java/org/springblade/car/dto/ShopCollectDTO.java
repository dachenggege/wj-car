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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.car.entity.Shop;
import org.springblade.car.entity.ShopCollect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.vo.ShopVO;

import java.util.Date;
import java.util.List;

/**
 * 门店收藏表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Data
public class ShopCollectDTO{


	@ApiModelProperty(value = "申请id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@ApiModelProperty(value = "门店id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long shopId;
	@ApiModelProperty(value = "门店名称")
	private String shopName;

	@ApiModelProperty(value = "店主Id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long shopMemberId;
	@ApiModelProperty(value = "店主")
	private String shopMember;
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "结盟人id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long collecterId;
	@ApiModelProperty(value = "结盟人名称")
	private String collecterName;
	@ApiModelProperty(value = "申请0,1已接受,2取消")
	private int isCollect;
}
