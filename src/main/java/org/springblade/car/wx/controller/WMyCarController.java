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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.*;
import org.springblade.car.entity.CarsBrowse;
import org.springblade.car.entity.Member;
import org.springblade.car.enums.CarSort;
import org.springblade.car.enums.RoleType;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.vo.PayOrderVO;
import org.springblade.car.wx.WeixinPhone.AESForWeixinGetPhoneNumber;
import org.springblade.car.wx.WeixinPhone.WeixinPhoneDecryptInfo;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WxFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.vo.DictVO;
import org.springblade.modules.system.wrapper.DictWrapper;
import org.springblade.util.NumberUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/member")
@Api(value = "微信-用户", tags = "微信-v2我的车源接口")
@ApiSort(1008)
public class WMyCarController extends BladeController {
	private HttpServletRequest request;
	private final BladeRedis bladeRedis;
	private WMemberFactory wMemberFactory;
	private final IDictService dictService;

	private IMemberService memberService;
	private WxFactory wxFactory;
	private final ICarsService carsService;
	private final ICarsCollectService casCollectService;
	private IPayOrderService payOrderService;
	private final ICarsBrowseService carsBrowseService;



	@PostMapping("/myCarsPage")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "我的车源分页", notes = "传入cars")
	public R<IPage<CarsDTO>> myCarsPage(CarsVO cars,Query query) {
		Member cl = wMemberFactory.getMember(request);
		cars.setMemberId(cl.getId());
		if (Func.isNotEmpty(cars.getSort())) {
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		} else {
			cars.setSort(CarSort.TIME.value);
		}

		IPage<CarsDTO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}


	@GetMapping("/upDownMyCar")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "上架下架车源")
	public R upDownMyCar(@ApiParam(value = "车源id", required = true) @RequestParam Long id,
						   @ApiParam(value = "状态", required = true) @RequestParam Integer status) {
		return R.status(carsService.upDownShopCar(id,status));
	}

	@PostMapping("/removeMyCar")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "删除车源", notes = "传入ids")
	public R removeMyCar(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(carsService.removeByIds(Func.toLongList(ids)));
	}


	@GetMapping("/carsBeenBrowsePage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "我的车源被浏览记录", notes = "传入cars")
	public R<IPage<carsBeenBrowseDTO>> carsBeenBrowsePage(Query query) {
		Member cl = wMemberFactory.getMember(request);

		if (Func.isEmpty(cl)) {
			return R.fail("微信用户不存在");
		}
		CarsVO cars = new CarsVO();
		cars.setMemberId(cl.getId());

		IPage<carsBeenBrowseDTO> pages = carsService.carsBeenBrowsePage(Condition.getPage(query), cars);
		return R.data(pages);
	}
	@GetMapping("/carsBeenCallPage")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "我的车源被电话咨询记录", notes = "传入cars")
	public R<IPage<carsBeenCallDTO>> carsBeenCallPage(Query query) {
		Member cl = wMemberFactory.getMember(request);

		if (Func.isEmpty(cl)) {
			return R.fail("微信用户不存在");
		}
		CarsVO cars = new CarsVO();
		cars.setMemberId(cl.getId());

		IPage<carsBeenCallDTO> pages = carsService.carsBeenCallPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	@GetMapping("/updateListtime")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "上新车源时间", notes = "传入id")
	public R updateListtime(@ApiParam(value = "车源主键", required = true) @RequestParam Long id) {

		return R.status(carsService.updateCarListTime(id));
	}

}
