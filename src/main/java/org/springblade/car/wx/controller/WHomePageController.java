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
import lombok.AllArgsConstructor;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.CarSort;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WVinServeFactory;
import org.springblade.common.cache.CacheNames;
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
@Api(value = "微信-车港湾", tags = "v2微信-首页车源接口")
@ApiSort(1009)
public class WHomePageController extends BladeController {
	private HttpServletRequest request;
	private final IBrandService brandService;
	private final IAdService adService;
	private final IModelService modelService;
	private final ICarsService carsService;
	private final ISeriesService seriesService;
	private final IStylesService stylesService;
	private IMemberService memberService;
	private BladeRedis bladeRedis;
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
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "车型列表", notes = "传入styles")
	public R<List<Styles>> styleslist(Styles styles) {
		List<Styles> pages = stylesService.list(Condition.getQueryWrapper(styles));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/carpage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "车源分页", notes = "传入cars")
	public R<IPage<CarsDTO>> page(CarsVO cars, Query query) {
		String code = cars.getCity();
		cars.setCity(null);
		if (Func.isNotEmpty(code)) {
			String county = code.substring(4, 6);
			String city = code.substring(2, 4);
			if (Func.equals(city, "00") && Func.equals(county, "00")) {
				cars.setProvince(code);
			}
			if (Func.equals(county, "00") && !Func.equals(city, "00")) {
				cars.setCity(code);
			}
			if (!Func.equals(county, "00") && !Func.equals(city, "00")) {
				cars.setCounty(code);
			}
		}

		if (Func.isNotEmpty(cars.getSort())) {
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		} else {
			cars.setSort(CarSort.TIME.value);
		}
		if (Func.isEmpty(cars.getStatus())) {
			cars.setStatus(1);
		}
		Member cl = new Member();
		Long memberId=null;
		String openid = request.getHeader("openid");
		if (Func.isNotEmpty(openid)) {
			cl = bladeRedis.get(CacheNames.MEMBER_OPENID_KEY+openid);
			if (Func.isEmpty(cl)) {
				Member client = new Member();
				client.setOpenid(openid);
				cl = memberService.getOne(Condition.getQueryWrapper(client));
				bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+cl.getOpenid(), cl);
			}
			memberId=cl.getId();
		}

		IPage<CarsDTO> pages = carsService.selectHomePageCarsPage(Condition.getPage(query), cars,memberId);
		return R.data(pages);
	}

}
