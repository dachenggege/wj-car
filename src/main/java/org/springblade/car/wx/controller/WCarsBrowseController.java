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
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.entity.CarsBrowse;
import org.springblade.car.entity.Member;
import org.springblade.car.service.ICarsBrowseService;
import org.springblade.car.vo.CarsBrowseVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 车源浏览表 控制器
 *
 * @author BladeX
 * @since 2021-08-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/carsbrowse")
@Api(value = "车源浏览表", tags = "微信-车源浏览接口")
public class WCarsBrowseController extends BladeController {

	private final ICarsBrowseService carsBrowseService;
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;

//	/**
//	 * 自定义分页 车源浏览表
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "浏览记录分页", notes = "传入carsBrowse")
//	public R<IPage<CarsBrowseVO>> page(CarsBrowseVO carsBrowse, Query query) {
//		IPage<CarsBrowseVO> pages = carsBrowseService.selectCarsBrowsePage(Condition.getPage(query), carsBrowse);
//		return R.data(pages);
//	}
//
//
//	/**
//	 * 新增或修改 车源浏览表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增浏览", notes = "传入carsBrowse")
//	public R submit(@Valid @RequestBody CarsBrowse carsBrowse) {
//		return R.status(carsBrowseService.saveOrUpdate(carsBrowse));
//	}

	
	/**
	 * 删除 车源浏览表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除浏览", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		Member cl = wMemberFactory.getMember(request);
		CarsBrowse carsBrowse=new CarsBrowse();
		carsBrowse.setMemberId(cl.getId());
		carsBrowse.setCarId(Long.valueOf(ids));
		Boolean res= carsBrowseService.remove(Condition.getQueryWrapper(carsBrowse));
		//carsBrowseService.removeByIds(Func.toLongList(ids)
		return R.status(res);
	}

	
}
