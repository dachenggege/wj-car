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
package org.springblade.car.vehiData.service.impl;

import org.springblade.car.vehiData.entity.Vcardetail;
import org.springblade.car.vehiData.vo.VcardetailVO;
import org.springblade.car.vehiData.mapper.VcardetailMapper;
import org.springblade.car.vehiData.service.IVcardetailService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author bond
 * @since 2021-11-30
 */
@Service
public class VcardetailServiceImpl extends BaseServiceImpl<VcardetailMapper, Vcardetail> implements IVcardetailService {

	@Override
	public IPage<VcardetailVO> selectVcardetailPage(IPage<VcardetailVO> page, VcardetailVO vcardetail) {
		return page.setRecords(baseMapper.selectVcardetailPage(page, vcardetail));
	}

}
