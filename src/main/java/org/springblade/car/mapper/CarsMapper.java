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
package org.springblade.car.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.car.dto.carsBeenBrowseDTO;
import org.springblade.car.dto.carsBeenCallDTO;
import org.springblade.car.entity.Cars;
import org.springblade.car.vo.CarsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 车源表 Mapper 接口
 *
 * @author BladeX
 * @since 2021-07-26
 */
public interface CarsMapper extends BaseMapper<Cars> {

	Integer selectCarsCount(@Param("cars") CarsVO cars);
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cars
	 * @return
	 */
	List<CarsVO> selectCarsPage(IPage page, CarsVO cars);
	List<CarsVO> carCollectPage(IPage page, CarsVO cars);
	List<CarsVO> carsBrowsePage(IPage page, CarsVO cars);
	List<carsBeenBrowseDTO> carsBeenBrowsePage(IPage page, CarsVO cars);
	List<carsBeenCallDTO> carsBeenCallPage(IPage page, CarsVO cars);

	//刷新上新时间
	Boolean updateCarListTime(@Param("id") Long id);
}
