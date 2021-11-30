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
package org.springblade.car.vehiData.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.car.vehiData.entity.Vcar;
import org.springblade.car.vehiData.vo.VcarVO;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author bond
 * @since 2021-11-30
 */
public class VcarWrapper extends BaseEntityWrapper<Vcar, VcarVO>  {

	public static VcarWrapper build() {
		return new VcarWrapper();
 	}

	@Override
	public VcarVO entityVO(Vcar vcar) {
		VcarVO vcarVO = Objects.requireNonNull(BeanUtil.copy(vcar, VcarVO.class));

		return vcarVO;
	}

}
