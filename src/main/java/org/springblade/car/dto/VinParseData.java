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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.car.entity.VinParse;

/**
 * 数据传输对象实体类
 *
 * @author BladeX
 * @since 2021-09-23
 */
@Data
public class VinParseData{
	private static final long serialVersionUID = 1L;

	//{"reason":"车架号格式错误","result":null,"error_code":228302}
	private String vin;
	private String reason;
	private String error_code;
	private VinVehicle vinVehicle;
}
