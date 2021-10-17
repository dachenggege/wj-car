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
import org.springblade.car.entity.VinParse;
import org.springblade.car.vo.VinParseVO;
import org.springblade.car.service.IVinParseService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-09-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/vinparse")
@Api(value = "", tags = "后台VIN查询记录-接口")
public class VinParseController extends BladeController {

	private final IVinParseService vinParseService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入vinParse")
	public R<VinParse> detail(VinParse vinParse) {
		VinParse detail = vinParseService.getOne(Condition.getQueryWrapper(vinParse));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入vinParse")
	public R<IPage<VinParse>> list(VinParse vinParse, Query query) {
		IPage<VinParse> pages = vinParseService.page(Condition.getPage(query), Condition.getQueryWrapper(vinParse));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入vinParse")
	public R<IPage<VinParseVO>> page(VinParseVO vinParse, Query query) {
		IPage<VinParseVO> pages = vinParseService.selectVinParsePage(Condition.getPage(query), vinParse);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入vinParse")
	public R save(@Valid @RequestBody VinParse vinParse) {
		return R.status(vinParseService.save(vinParse));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入vinParse")
	public R update(@Valid @RequestBody VinParse vinParse) {
		return R.status(vinParseService.updateById(vinParse));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入vinParse")
	public R submit(@Valid @RequestBody VinParse vinParse) {
		return R.status(vinParseService.saveOrUpdate(vinParse));
	}

	
	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vinParseService.removeByIds(Func.toLongList(ids)));
	}

	
}
