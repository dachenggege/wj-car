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
package org.springblade.car.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.car.Req.CarsAuditReq;
import org.springblade.car.Req.MemberReq;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.entity.*;
import org.springblade.car.enums.CarSort;
import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IShopService;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.DateUtil;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.service.ICarsService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 车源表 控制器
 *
 * @author BladeX
 * @since 2021-07-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/cars")
@Api(value = "车源表", tags = "v2后台车源管理-车源列表")
public class CarsController extends BladeController {
	private final IShopService shopService;
	private final ICarsService carsService;
	private final IMemberService memberService;
	private UserAreaFactory userAreaFactory;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "车源详情", notes = "传入cars")
	public R<CarsDTO> detail(Long carId) {
		CarsDTO carDetail=new CarsDTO();
		Cars cars = carsService.getById(carId);
		if(Func.isEmpty(cars)){
			throw new ServiceException("为获取到车源信息");
		}
		Member member= memberService.getById(cars.getMemberId());
		BeanUtils.copyProperties(cars,carDetail);
		carDetail.setMemberName(member.getName());
		carDetail.setMemberNickName(member.getNickname());
		carDetail.setRoletype(member.getRoletype());
		carDetail.setCertificationLv(member.getCertificationLv());
		carDetail.setMemberLv(member.getMemberLv());
		if(Func.isNotEmpty(member)){
			//个人车源
			if(Func.equals(cars.getVest(),1)){
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
		return R.data(carDetail);
	}

//	/**
//	 * 分页 车源表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入cars")
//	public R<IPage<Cars>> list(Cars cars, Query query) {
//		IPage<Cars> pages = carsService.page(Condition.getPage(query), Condition.getQueryWrapper(cars));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "车源分页", notes = "传入cars")
	public R<IPage<CarsDTO>> page(CarsVO cars, Query query) {
		MemberReq memberReq=userAreaFactory.getUserAreas();
		cars.setAreas(memberReq.getAreas());
		cars.setNoareas(memberReq.getNoareas());
		cars.setUserId(memberReq.getUserId());
		cars.setSort("t.listtime");
		IPage<CarsDTO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);

		return R.data(pages);
	}

//	/**
//	 * 新增 车源表
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增车源", notes = "传入cars")
//	public R save(@Valid @RequestBody Cars cars) {
//		cars.setId(NumberUtil.getRandomNumber(1,10));
//		return R.status(carsService.save(cars));
//	}

	/**
	 * 修改 车源表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改车源", notes = "传入cars")
	public R update(@Valid @RequestBody Cars cars) {

		if(Func.isNotEmpty(cars.getAuditStatus()) && Func.isEmpty(cars.getAuditTime())){
			cars.setAuditTime(new Date());
		}
		return R.status(carsService.updateById(cars));
	}

	@GetMapping("/carAudit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "审核车源")
	public R carAudit(@ApiParam(value = "车源id") @RequestParam(value = "id", required = true) Long id,
								 @ApiParam(value = "状态 2审核通过，3审核不通过") @RequestParam(value = "auditStatus", required = true) int auditStatus,
								 @ApiParam(value = "原因") @RequestParam(value = "nopassnotice", required = false) String nopassnotice) {

		Cars car= carsService.getById(id);
		if(Func.isEmpty(car)){
			return R.fail("车源信息不存在");
		}
		car.setAuditStatus(auditStatus);
		car.setAuditTime(new Date());
		car.setNopassnotice(nopassnotice);
		return R.status(carsService.updateById(car));
	}
	@GetMapping("/upDownCar")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "上架下架车源")
	public R upDownMyCar(@ApiParam(value = "车源id", required = true) @RequestParam Long id,
						 @ApiParam(value = "状态", required = true) @RequestParam Integer status) {
		return R.status(carsService.upDownShopCar(id,status));
	}
	/**
	 * 删除 车源表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除车源", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(carsService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/updateListtime")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "上新车源时间", notes = "传入id")
	public R updateListtime(@ApiParam(value = "车源主键", required = true) @RequestParam Long id) {

		return R.status(carsService.updateCarListTime(id));
	}


}
