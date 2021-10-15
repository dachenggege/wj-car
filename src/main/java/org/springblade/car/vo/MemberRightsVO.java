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

import org.springblade.car.entity.MemberRights;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 会员体系权益表视图实体类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MemberRightsVO对象", description = "会员体系权益表")
public class MemberRightsVO extends MemberRights {
	private static final long serialVersionUID = 1L;

}
