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
package org.springblade.car.service.impl;

import org.springblade.car.entity.CarsCollect;
import org.springblade.car.vo.CarsCollectVO;
import org.springblade.car.mapper.CarsCollectMapper;
import org.springblade.car.service.ICarsCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 车源收藏 服务实现类
 *
 * @author BladeX
 * @since 2021-08-12
 */
@Service
public class CarsCollectServiceImpl extends ServiceImpl<CarsCollectMapper, CarsCollect> implements ICarsCollectService {

	@Override
	public IPage<CarsCollectVO> selectCarsCollectPage(IPage<CarsCollectVO> page, CarsCollectVO casCollect) {
		return page.setRecords(baseMapper.selectCarsCollectPage(page, casCollect));
	}

	public CarsCollect selectCarsCollect(CarsCollect casCollect){
		return baseMapper.selectCarsCollect(casCollect);
	}

}
