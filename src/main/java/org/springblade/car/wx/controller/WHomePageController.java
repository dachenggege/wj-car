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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.CarSort;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WVinServeFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 品牌表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/HomePage")
@Api(value = "微信-车港湾", tags = "微信-车港湾接口")
public class WHomePageController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IBrandService brandService;
	private final IAdService adService;
	private final IModelService modelService;
	private final ICarsService carsService;
	private final ISeriesService seriesService;
	private final ICarsCollectService casCollectService;
	private final IStylesService stylesService;
	private final ICarsBrowseService carsBrowseService;
	private IMemberService memberService;
	private WVinServeFactory wVinServeFactory;
	/**
	 *  广告列表
	 */
	@GetMapping("/adlist")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "广告banner列表", notes = "传入ad")
	public R<List<Ad>> adlist(Ad ad) {
		List<Ad> list = adService.list(Condition.getQueryWrapper(ad));
		return R.data(list);
	}
	/**
	 * 车辆类型
	 */
	@GetMapping("/modellist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "车辆类型列表", notes = "传入model")
	public R<List<Model>> modellist(Model model) {
		List<Model> pages = modelService.list( Condition.getQueryWrapper(model));
		return R.data(pages);
	}

	/**
	 *  品牌表
	 */
	@GetMapping("/barndlist")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "品牌列表", notes = "传入brand")
	public R<List<Brand>> barndlist(Brand brand) {
		List<Brand> pages = brandService.list(Condition.getQueryWrapper(brand));
		return R.data(pages);
	}

	/**
	 * 车系表
	 */
	@GetMapping("/serieslist")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "车系列表", notes = "传入series")
	public R<List<Series>> serieslist(Series series) {
		List<Series> pages = seriesService.list(Condition.getQueryWrapper(series));
		return R.data(pages);
	}

	@GetMapping("/styleslist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入styles")
	public R<List<Styles>> styleslist(Styles styles) {
		List<Styles> pages = stylesService.list(Condition.getQueryWrapper(styles));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/carpage")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "车源分页", notes = "传入cars")
	public R<IPage<CarsVO>> page(CarsVO cars, Query query) {
		if(Func.isNotEmpty(cars.getSort())){
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		}else {
			cars.setSort(CarSort.TIME.value);
		}

		IPage<CarsVO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}


	/**
	 * 新增 车源表
	 */
	@PostMapping("/saveCar")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "发布车辆", notes = "传入cars")
	public R saveCar(@Valid @RequestBody Cars cars) {
		wVinServeFactory.isCheckVin(cars.getPvin());

		Member cl = wMemberFactory.getMember(request);
		cars.setAuditStatus(AuditStatus.AUDITING.id);
		if(Func.isEmpty(cars.getPafprice())){
			throw new ServiceException("零售价不能为空");
		}
		if(Func.isEmpty(cars.getPtradePrice())){
			throw new ServiceException("批发价不能为空");
		}
		if(Func.isEmpty(cars.getPafprice())){
			throw new ServiceException("内部价不能为空");
		}

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
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "车源详情", notes = "传入cars")
	public R<CarsDTO> carDetail(Long carId,Long memberId) {
		if(Func.isEmpty(memberId)){
			Member cl = wMemberFactory.getMember(request);
			memberId=cl.getId();
		}

		CarsDTO carDetail=new CarsDTO();
		Cars detail = carsService.getById(carId);
		if(Func.isEmpty(detail)){
			throw new ServiceException("为获取到车源信息");
		}
		Member member= memberService.getById(detail.getMemberId());
		BeanUtils.copyProperties(detail,carDetail);

		if(Func.isNotEmpty(member)){
			carDetail.setPhone(member.getPhone());
			carDetail.setMemberName(member.getName());
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
}
