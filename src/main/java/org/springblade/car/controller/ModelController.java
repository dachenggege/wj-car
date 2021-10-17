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
import org.springblade.car.entity.Model;
import org.springblade.car.vo.ModelVO;
import org.springblade.car.service.IModelService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 级别表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/model")
@Api(value = "级别表", tags = "后台-车级别管理")
public class ModelController extends BladeController {

	private final IModelService modelService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入model")
	public R<Model> detail(Model model) {
		Model detail = modelService.getOne(Condition.getQueryWrapper(model));
		return R.data(detail);
	}

	/**
	 * 分页 级别表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入model")
	public R<List<Model>> list(Model model) {
		List<Model> pages = modelService.list( Condition.getQueryWrapper(model));
		return R.data(pages);
	}

	/**
	 * 自定义分页 级别表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入model")
	public R<IPage<ModelVO>> page(ModelVO model, Query query) {
		IPage<ModelVO> pages = modelService.selectModelPage(Condition.getPage(query), model);
		return R.data(pages);
	}

	/**
	 * 新增 级别表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入model")
	public R save(@Valid @RequestBody Model model) {
		return R.status(modelService.save(model));
	}

	/**
	 * 修改 级别表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入model")
	public R update(@Valid @RequestBody Model model) {
		return R.status(modelService.updateById(model));
	}

//	/**
//	 * 新增或修改 级别表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入model")
//	public R submit(@Valid @RequestBody Model model) {
//		return R.status(modelService.saveOrUpdate(model));
//	}

	
	/**
	 * 删除 级别表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(modelService.removeByIds(Func.toLongList(ids)));
	}

	
}
