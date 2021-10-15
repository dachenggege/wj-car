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

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.CarsBrowse;
import org.springblade.car.vo.CarsBrowseVO;
import org.springblade.car.service.ICarsBrowseService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车源浏览表 控制器
 *
 * @author BladeX
 * @since 2021-08-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/carsbrowse")
//@Api(value = "车源浏览表", tags = "车源浏览表接口")
public class CarsBrowseController extends BladeController {

	private final ICarsBrowseService carsBrowseService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入carsBrowse")
	public R<CarsBrowse> detail(CarsBrowse carsBrowse) {
		CarsBrowse detail = carsBrowseService.getOne(Condition.getQueryWrapper(carsBrowse));
		return R.data(detail);
	}

	/**
	 * 分页 车源浏览表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入carsBrowse")
	public R<IPage<CarsBrowse>> list(CarsBrowse carsBrowse, Query query) {
		IPage<CarsBrowse> pages = carsBrowseService.page(Condition.getPage(query), Condition.getQueryWrapper(carsBrowse));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车源浏览表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入carsBrowse")
	public R<IPage<CarsBrowseVO>> page(CarsBrowseVO carsBrowse, Query query) {
		IPage<CarsBrowseVO> pages = carsBrowseService.selectCarsBrowsePage(Condition.getPage(query), carsBrowse);
		return R.data(pages);
	}

	/**
	 * 新增 车源浏览表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入carsBrowse")
	public R save(@Valid @RequestBody CarsBrowse carsBrowse) {
		return R.status(carsBrowseService.save(carsBrowse));
	}

	/**
	 * 修改 车源浏览表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入carsBrowse")
	public R update(@Valid @RequestBody CarsBrowse carsBrowse) {
		return R.status(carsBrowseService.updateById(carsBrowse));
	}

	/**
	 * 新增或修改 车源浏览表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入carsBrowse")
	public R submit(@Valid @RequestBody CarsBrowse carsBrowse) {
		return R.status(carsBrowseService.saveOrUpdate(carsBrowse));
	}

	
	/**
	 * 删除 车源浏览表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(carsBrowseService.removeByIds(Func.toLongList(ids)));
	}

	
}
