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

import lombok.Synchronized;
import org.springblade.car.entity.Brand;
import org.springblade.car.entity.JuHeBrand;
import org.springblade.car.entity.JuHeSeries;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.excel.util.ExcelUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Series;
import org.springblade.car.vo.SeriesVO;
import org.springblade.car.service.ISeriesService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 车系表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/series")
@Api(value = "车系表", tags = "后台设置管理-车系管理")
public class SeriesController extends BladeController {

	private final ISeriesService seriesService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入series")
	public R<Series> detail(Series series) {
		Series detail = seriesService.getOne(Condition.getQueryWrapper(series));
		return R.data(detail);
	}

	/**
	 * 车系表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入series")
	public R<List<Series>> list(Series series) {
		List<Series> pages = seriesService.list(Condition.getQueryWrapper(series));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车系表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入series")
	public R<IPage<SeriesVO>> page(SeriesVO series, Query query) {
		IPage<SeriesVO> pages = seriesService.selectSeriesPage(Condition.getPage(query), series);
		return R.data(pages);
	}

	/**
	 * 新增 车系表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入series")
	public R save(@Valid @RequestBody Series series) {
		return R.status(seriesService.save(series));
	}

	/**
	 * 修改 车系表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入series")
	public R update(@Valid @RequestBody Series series) {
		return R.status(seriesService.updateById(series));
	}

//	/**
//	 * 新增或修改 车系表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入series")
//	public R submit(@Valid @RequestBody Series series) {
//		return R.status(seriesService.saveOrUpdate(series));
//	}

	
	/**
	 * 删除 车系表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(seriesService.removeByIds(Func.toLongList(ids)));
	}

	@PostMapping("/uploadSeries")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "excel导入Series信息", notes = "")
	@Synchronized
	@Transactional
	public R uploadStation(@RequestParam MultipartFile excel) {

		List<JuHeSeries> list = new ArrayList<>();
		list = ExcelUtils.read(excel, JuHeSeries.class);
		List<Series> seriesList=new ArrayList<>();
		for(JuHeSeries dto:list){
			Series h=seriesService.getById(Long.valueOf(dto.getId()));
			if(Func.isEmpty(h)) {
				Series entity = new Series();
				entity.setId(Long.valueOf(dto.getId()));
				entity.setSeriesName(dto.getName());
				entity.setSeriesGroupname(dto.getSgroup());
				entity.setBrandId(dto.getBrandid());
//				entity.setModelName(dto.getLevelname());
				entity.setOpen(true);
				seriesList.add(entity);
			}
		}
		seriesService.saveBatch(seriesList);
		return R.status(true);

	}
}
