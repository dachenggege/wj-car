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
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.ShopMember;

import java.util.Date;

/**
 * 门店成员表数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShopMemberRep extends Member {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户角色1已是员工；0不是")
	private Integer isStaff;


}
