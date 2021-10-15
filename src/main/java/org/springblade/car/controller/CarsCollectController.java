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
import org.springblade.car.entity.CarsCollect;
import org.springblade.car.vo.CarsCollectVO;
import org.springblade.car.service.ICarsCollectService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车源收藏 控制器
 *
 * @author BladeX
 * @since 2021-08-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/cascollect")
//@Api(value = "车源收藏", tags = "后台车源管理-车源收藏接口")
public class CarsCollectController extends BladeController {

	private final ICarsCollectService casCollectService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入casCollect")
	public R<CarsCollect> detail(CarsCollect carsCollect) {
		CarsCollect detail = casCollectService.getOne(Condition.getQueryWrapper(carsCollect));
		return R.data(detail);
	}

	/**
	 * 分页 车源收藏
	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入casCollect")
//	public R<IPage<CarsCollect>> list(CarsCollect carsCollect, Query query) {
//		IPage<CarsCollect> pages = casCollectService.page(Condition.getPage(query), Condition.getQueryWrapper(carsCollect));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 车源收藏
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入casCollect")
	public R<IPage<CarsCollectVO>> page(CarsCollectVO casCollect, Query query) {
		IPage<CarsCollectVO> pages = casCollectService.selectCarsCollectPage(Condition.getPage(query), casCollect);
		return R.data(pages);
	}

	/**
	 * 新增 车源收藏
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入casCollect")
	public R save(@Valid @RequestBody CarsCollect carsCollect) {
		return R.status(casCollectService.save(carsCollect));
	}

	/**
	 * 修改 车源收藏
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入casCollect")
	public R update(@Valid @RequestBody CarsCollect carsCollect) {
		return R.status(casCollectService.updateById(carsCollect));
	}

	/**
	 * 新增或修改 车源收藏
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入casCollect")
	public R submit(@Valid @RequestBody CarsCollect carsCollect) {
		return R.status(casCollectService.saveOrUpdate(carsCollect));
	}

	
	/**
	 * 删除 车源收藏
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(casCollectService.removeByIds(Func.toLongList(ids)));
	}

	
}
