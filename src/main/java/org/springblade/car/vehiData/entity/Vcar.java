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
package org.springblade.car.vehiData.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author bond
 * @since 2021-11-30
 */
@Data
@TableName("v_vcar")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Vcar对象", description = "Vcar对象")
public class Vcar extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String initial;
	private Integer parentid;
	private String logo;
	private String price;
	private String yeartype;
	private String productionstate;
	private String salestate;
	private String sizetype;
	private Integer depth;


}
