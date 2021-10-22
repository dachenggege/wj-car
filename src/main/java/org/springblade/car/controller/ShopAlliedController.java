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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.car.entity.ShopAllied;
import org.springblade.car.vo.ShopVO;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.vo.ShopAlliedVO;
import org.springblade.car.service.IShopCollectService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 门店收藏表 控制器
 *
 * @author BladeX
 * @since 2021-08-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/shopcollect")
//@Api(value = "门店收藏表", tags = "门店收藏表接口")
public class ShopAlliedController extends BladeController {

	private final IShopCollectService shopCollectService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入shopCollect")
	public R<ShopAllied> detail(ShopAllied shopAllied) {
		ShopAllied detail = shopCollectService.getOne(Condition.getQueryWrapper(shopAllied));
		return R.data(detail);
	}

	/**
	 * 分页 门店收藏表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入shopCollect")
	public R<IPage<ShopAllied>> list(ShopAllied shopAllied, Query query) {
		IPage<ShopAllied> pages = shopCollectService.page(Condition.getPage(query), Condition.getQueryWrapper(shopAllied));
		return R.data(pages);
	}

	/**
	 * 自定义分页 门店收藏表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入shopCollect")
	public R<IPage<ShopVO>> page(ShopAlliedVO shopCollect, Query query) {
		IPage<ShopVO> pages = shopCollectService.selectShopCollectPage(Condition.getPage(query), shopCollect);
		return R.data(pages);
	}

	/**
	 * 新增 门店收藏表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入shopCollect")
	public R save(@Valid @RequestBody ShopAllied shopAllied) {
		return R.status(shopCollectService.save(shopAllied));
	}

	/**
	 * 修改 门店收藏表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入shopCollect")
	public R update(@Valid @RequestBody ShopAllied shopAllied) {
		return R.status(shopCollectService.updateById(shopAllied));
	}

	/**
	 * 新增或修改 门店收藏表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入shopCollect")
	public R submit(@Valid @RequestBody ShopAllied shopAllied) {
		return R.status(shopCollectService.saveOrUpdate(shopAllied));
	}

	
	/**
	 * 删除 门店收藏表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(shopCollectService.removeByIds(Func.toLongList(ids)));
	}

	
}
