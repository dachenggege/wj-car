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

import org.springblade.car.Req.CarsAuditReq;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.dto.carsBeenBrowseDTO;
import org.springblade.car.dto.carsBeenCallDTO;
import org.springblade.car.entity.Cars;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.mapper.CarsMapper;
import org.springblade.car.service.ICarsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 车源表 服务实现类
 *
 * @author BladeX
 * @since 2021-07-26
 */
@Service
public class CarsServiceImpl extends ServiceImpl<CarsMapper, Cars> implements ICarsService {

	public 	Integer selectCarsCount(CarsVO cars){
		return baseMapper.selectCarsCount(cars);
	}

	@Override
	public IPage<CarsDTO> selectCarsPage(IPage<CarsDTO> page, CarsVO cars) {
		return page.setRecords(baseMapper.selectCarsPage(page, cars));
	}
	@Override
	public IPage<CarsVO> carCollectPage(IPage<CarsVO> page, CarsVO cars) {
		return page.setRecords(baseMapper.carCollectPage(page, cars));
	}
	@Override
	public IPage<CarsDTO> shopAlliedCarPage(IPage<CarsDTO> page, CarsVO cars) {
		return page.setRecords(baseMapper.shopAlliedCarPage(page, cars));
	}

	@Override
	public IPage<CarsVO> carsBrowsePage(IPage<CarsVO> page, CarsVO cars) {
		return page.setRecords(baseMapper.carsBrowsePage(page, cars));
	}
	public IPage<carsBeenBrowseDTO> carsBeenBrowsePage(IPage<carsBeenBrowseDTO> page, CarsVO cars){
		return page.setRecords(baseMapper.carsBeenBrowsePage(page, cars));
	}
	public IPage<carsBeenCallDTO> carsBeenCallPage(IPage<carsBeenCallDTO> page, CarsVO cars){
		return page.setRecords(baseMapper.carsBeenCallPage(page, cars));
	}


	//刷新上新时间
	public Boolean updateCarListTime(Long id){
		return baseMapper.updateCarListTime(id);
	}
	public Boolean upDownShopCar(Long id,Integer status){
		if(Func.equals(status,0)){
			return baseMapper.downShopCar(id);
		}
		return baseMapper.upShopCar(id);
	}
}
