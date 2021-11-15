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
import io.swagger.models.auth.In;
import org.springblade.car.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.entity.MemberRights;

/**
 * 用户表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberDTO extends Member {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "会员等级及权益")
	private MemberRights rights;

	@ApiModelProperty(value = "关注数量")
	private Integer focusNum;

	@ApiModelProperty(value = "粉丝数量")
	private Integer fansNum;

	@ApiModelProperty(value = "车源数量")
	private Integer myCarNum;

	@ApiModelProperty(value = "门店车源数量")
	private Integer myShopCarNum;

	@ApiModelProperty(value = "创建的门店数")
	private Integer myShopNum;

	@ApiModelProperty(value = "加入的门店数")
	private Integer myJoinShopNum;

	@ApiModelProperty(value = "是否关注")
	private Boolean isFocus;
}
