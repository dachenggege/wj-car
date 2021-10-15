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
import org.springblade.car.entity.Ad;
import org.springblade.car.vo.AdVO;
import org.springblade.car.service.IAdService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 广告表 控制器
 *
 * @author BladeX
 * @since 2021-07-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/ad")
@Api(value = "广告", tags = "后台设置管理-广告设置")
public class AdController extends BladeController {

	private final IAdService adService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ad")
	public R<Ad> detail(Ad ad) {
		Ad detail = adService.getOne(Condition.getQueryWrapper(ad));
		return R.data(detail);
	}

	/**
	 *  广告表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入ad")
	public R<List<Ad>> list(Ad ad) {
		List<Ad> list = adService.list(Condition.getQueryWrapper(ad));
		return R.data(list);
	}

	/**
	 * 自定义分页 广告表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入ad")
	public R<IPage<AdVO>> page(AdVO ad, Query query) {
		IPage<AdVO> pages = adService.selectAdPage(Condition.getPage(query), ad);
		return R.data(pages);
	}

	/**
	 * 新增 广告表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ad")
	public R save(@Valid @RequestBody Ad ad) {
		return R.status(adService.save(ad));
	}

	/**
	 * 修改 广告表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ad")
	public R update(@Valid @RequestBody Ad ad) {
		return R.status(adService.updateById(ad));
	}

	/**
	 * 删除 广告表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(adService.removeByIds(Func.toLongList(ids)));
	}

	
}
