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
package org.springblade.car.wx.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据传输对象实体类
 *
 * @author bond
 * @since 2021-04-13
 */
@Data
public class WxUserResp {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "用户唯一标识")
	private String openid;

	@ApiModelProperty(value = "会话密钥")
	private String session_key;

	@ApiModelProperty(value = "用户在开放平台的唯一标识符")
	private String unionid;
	@ApiModelProperty(value = "是否注册")
	private Boolean isRegister=false;

}
