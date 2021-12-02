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
package org.springblade.car.service;

import org.springblade.car.Req.CarsAuditReq;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.dto.carsBeenBrowseDTO;
import org.springblade.car.dto.carsBeenCallDTO;
import org.springblade.car.entity.Cars;
import org.springblade.car.vo.CarsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.support.Condition;

import java.util.List;

/**
 * 车源表 服务类
 *
 * @author BladeX
 * @since 2021-07-26
 */
public interface ICarsService extends IService<Cars> {


	Integer selectCarsCount(CarsVO cars);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cars
	 * @return
	 */
	IPage<CarsDTO> selectCarsPage(IPage<CarsDTO> page, CarsVO cars);
	IPage<CarsVO> carCollectPage(IPage<CarsVO> page, CarsVO cars);
	IPage<CarsDTO> shopAlliedCarPage(IPage<CarsDTO> page, CarsVO cars);

	IPage<CarsVO> carsBrowsePage(IPage<CarsVO> page, CarsVO cars);
	IPage<carsBeenCallDTO> carsBeenCallPage(IPage<carsBeenCallDTO> page, CarsVO cars);
	IPage<carsBeenBrowseDTO> carsBeenBrowsePage(IPage<carsBeenBrowseDTO> page,CarsVO cars);

	//刷新上新时间
	Boolean updateCarListTime(Long id);
	//上架下架车源
	Boolean upDownShopCar(Long id,Integer status);


}
