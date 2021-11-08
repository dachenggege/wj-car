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
package org.springblade.car.wx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.dto.CarsVinParseReq;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.CarSort;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WVinServeFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Region;
import org.springblade.modules.system.service.IRegionService;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 车源表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/car")
@Api(value = "微信-车源", tags = "v2微信-车源相关接口")
@ApiSort(1007)
public class WCarController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IShopService shopService;
	private final IRegionService regionService;
	private final IModelService modelService;
	private final ICarsService carsService;
	private final ISeriesService seriesService;
	private final ICarsCollectService casCollectService;
	private final IStylesService stylesService;
	private final ICarsBrowseService carsBrowseService;
	private IMemberService memberService;
	private WVinServeFactory wVinServeFactory;



	/**
	 * 车辆VIN识别
	 */
	@ApiLog("发布车源VIN查询")
	@GetMapping("/carVinQuery")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发布车源VIN查询", notes = "传入vin号")
	public R<CarsVinParseReq> carVinQuery(@ApiParam(value = "车架号") @RequestParam(value = "vin", required = true)String vin) {
		wVinServeFactory.isCheckVin(vin);
		CarsVinParseReq cars = wVinServeFactory.carVinQuery(vin);
		return R.data(cars);

	}

	@PostMapping("/saveCar")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发布(编辑)车辆", notes = "传入cars")
	public R saveCar(@Valid @RequestBody Cars cars) {

		wVinServeFactory.isCheckVin(cars.getPvin());
		if(Func.isEmpty(cars.getVest())){
			throw new ServiceException("车源所属不能为空");
		}
		if(Func.isEmpty(cars.getPafprice())){
			throw new ServiceException("零售价不能为空");
		}
		if(Func.isEmpty(cars.getPtradePrice())){
			throw new ServiceException("批发价不能为空");
		}
		if(Func.isEmpty(cars.getPafprice())){
			throw new ServiceException("内部价不能为空");
		}
		if(Func.isEmpty(cars.getPcostprice())){
			throw new ServiceException("成本价不能为空");
		}


		Member cl = wMemberFactory.getMember(request);

		//个人车源
		if(Func.equals(cars.getVest(),1)){
			cars.setProvince(cl.getProvince());
			cars.setProvinceName(cl.getProvinceName());
			cars.setCity(cl.getCity());
			cars.setCityName(cl.getCityName());
			cars.setCounty(cl.getCounty());
			cars.setCountyName(cl.getCountyName());
		}

		//门店车源
		if(Func.equals(cars.getVest(),2)){
			if(Func.isEmpty(cars.getShopId())){
				return R.fail("门店id不能为空");
			}
			Shop shop= shopService.getById(cars.getShopId());
			if(Func.isEmpty(shop)){
				return R.fail("门店ID不存在");
			}
			cars.setProvince(shop.getProvince());
			Region pr= regionService.getById(shop.getProvince());
			if(Func.isNotEmpty(pr)) {
				cars.setProvinceName(pr.getProvinceName());
			}
			cars.setCity(shop.getCity());
			Region city= regionService.getById(shop.getCity());
			if(Func.isNotEmpty(city)) {
				cars.setCityName(city.getCityName());
			}
			cars.setCounty(shop.getCounty());
			Region co= regionService.getById(shop.getCounty());
			if(Func.isNotEmpty(co)) {
				cars.setCountyName(co.getTownName());
			}
		}

		cars.setAuditStatus(AuditStatus.AUDITING.id);
		cars.setMemberId(cl.getId());
		cars.setListtime(DateUtil.format(new Date(),DateUtil.PATTERN_DATETIME));
		if(Func.isEmpty(cars.getId())) {
			cars.setId(NumberUtil.getRandomNumber(1, 10));
		}
		else{
			return R.status(carsService.updateById(cars));
		}
		return R.status(carsService.save(cars));
	}

	/**
	 * 车源详情
	 */
	@GetMapping("/carDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "车源详情", notes = "传入cars")
	public R<CarsDTO> carDetail(Long carId) {
		Member cl = wMemberFactory.getMember(request);
		Long memberId=cl.getId();
		CarsDTO carDetail=new CarsDTO();
		Cars cars = carsService.getById(carId);
		if(Func.isEmpty(cars)){
			throw new ServiceException("为获取到车源信息");
		}
		Member member= memberService.getById(cars.getMemberId());
		BeanUtils.copyProperties(cars,carDetail);

		if(Func.isNotEmpty(member)){
			//个人车源
			if(Func.equals(cars.getVest(),1)){
				carDetail.setMemberName(member.getName());
				carDetail.setPhone1(member.getPhone());
				carDetail.setShopName(member.getCarDealer());
				carDetail.setShopAddress(member.getDealerAddress());
				carDetail.setLat(member.getLat());
				carDetail.setLng(member.getLng());
			}

			//门店车源
			if(Func.equals(cars.getVest(),2)){
				Shop shop= shopService.getById(cars.getShopId());
				if(Func.isNotEmpty(shop)) {

					carDetail.setMemberName(member.getName());
					carDetail.setPhone1(shop.getPhone1());
					carDetail.setPhone2(shop.getPhone2());
					carDetail.setPhone3(shop.getPhone3());
					carDetail.setPhone4(shop.getPhone4());
					carDetail.setPhone5(shop.getPhone5());
					carDetail.setShopName(shop.getShopName());
					carDetail.setShopAddress(shop.getShopAddress());
					carDetail.setLat(shop.getLat());
					carDetail.setLng(shop.getLng());
				}
			}
		}
		CarsCollect carsCollect=new CarsCollect();
		carsCollect.setCarId(carId);
		carsCollect.setMemberId(memberId);
		CarsCollect entity=casCollectService.selectCarsCollect(carsCollect);
		if (Func.isNotEmpty(entity)){
			carDetail.setIsCollect(entity.getIsCollect());
		}
		//添加浏览记录
		CarsBrowse carsBrowse=new CarsBrowse();
		carsBrowse.setMemberId(memberId);
		carsBrowse.setCarId(carId);
		List<CarsBrowse> list =carsBrowseService.list(Condition.getQueryWrapper(carsBrowse));
		if(Func.isEmpty(list)){
			carsBrowseService.save(carsBrowse);
		}else{
			for(CarsBrowse cb:list){
				cb.setUpdateTime(LocalDateTime.now());
			}
			carsBrowseService.updateBatchById(list);
		}


		return R.data(carDetail);
	}
	/**
	 *
	 */
	@PostMapping("/carCollect")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "车源收藏", notes = "传入carsCollect")
	public R collect(@Valid @RequestBody CarsCollect carsCollect) {
		Member cl = wMemberFactory.getMember(request);
		carsCollect.setMemberId(cl.getId());
		CarsCollect entity=casCollectService.selectCarsCollect(carsCollect);
		Boolean res=false;
		if(Func.isEmpty(entity)){
			res=casCollectService.save(carsCollect);
		}else {
			entity.setIsCollect(carsCollect.getIsCollect());
			res=casCollectService.updateById(entity);
		}

		return R.status(res);
	}

	@PostMapping("/memberCarsPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "会员车源分页", notes = "传入cars")
	public R<IPage<CarsDTO>> memberCarsPage(
			@ApiParam(value = "会员id") @RequestParam(value = "memberId", required = true) Long memberId,
			@ApiParam(value = "车源所属：1个人 2门店") @RequestParam(value = "vest", required = true) Integer vest,
										Query query) {
		CarsVO cars=new CarsVO();
		cars.setMemberId(memberId);
		cars.setVest(vest);
		if (Func.isNotEmpty(cars.getSort())) {
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		} else {
			cars.setSort(CarSort.TIME.value);
		}

		IPage<CarsDTO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}


}
