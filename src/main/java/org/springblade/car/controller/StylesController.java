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
import org.springblade.car.entity.JuHeALL;
import org.springblade.car.entity.Series;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.excel.util.ExcelUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Styles;
import org.springblade.car.vo.StylesVO;
import org.springblade.car.service.IStylesService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 车系表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/styles")
@Api(value = "车型表", tags = "后台设置管理-车型管理")
public class StylesController extends BladeController {

	private  IStylesService stylesService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入styles")
	public R<Styles> detail(Styles styles) {
		Styles detail = stylesService.getOne(Condition.getQueryWrapper(styles));
		return R.data(detail);
	}

	/**
	 * 分页 车系表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入styles")
	public R<List<Styles>> list(Styles styles) {
		List<Styles> pages = stylesService.list(Condition.getQueryWrapper(styles));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车系表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入styles")
	public R<IPage<StylesVO>> page(StylesVO styles, Query query) {
		IPage<StylesVO> pages = stylesService.selectStylesPage(Condition.getPage(query), styles);
		return R.data(pages);
	}

	/**
	 * 新增 车系表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入styles")
	public R save(@Valid @RequestBody Styles styles) {
		return R.status(stylesService.save(styles));
	}

	/**
	 * 修改 车系表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入styles")
	public R update(@Valid @RequestBody Styles styles) {
		return R.status(stylesService.updateById(styles));
	}

	/**
	 * 新增或修改 车系表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入styles")
	public R submit(@Valid @RequestBody Styles styles) {
		return R.status(stylesService.saveOrUpdate(styles));
	}


	/**
	 * 删除 车系表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stylesService.removeByIds(Func.toLongList(ids)));
	}

	@PostMapping("/uploadStyles")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "excel导入Styles信息", notes = "")
	@Synchronized
	@Transactional
	public R uploadStation(@RequestParam MultipartFile excel) {
//brand_first_letter	brand_id	brand_name	series_id	series_name	group_name	level_name	model_id	name	guide_price	year	configuration
//
		List<JuHeALL> list = new ArrayList<>();
		list = ExcelUtils.read(excel, JuHeALL.class);
		List<Styles> stylesList = new ArrayList<>();
		for (JuHeALL dto : list) {
			Styles h = stylesService.getById(Long.valueOf(dto.getModel_id()));
			if (Func.isEmpty(h)) {
				Styles entity = new Styles();
				entity.setId(Long.valueOf(dto.getModel_id()));
				entity.setStylesName(dto.getName());
				entity.setStylesPrice(BigDecimal.valueOf(checkNumber(dto.getGuide_price())));
				entity.setStylesYear(dto.getYear());
				entity.setBrandId(dto.getBrand_id());
				entity.setBrandName(dto.getBrand_name());
				entity.setSeriesId(dto.getSeries_id());
				entity.setSeriesName(dto.getSeries_name());
				stylesList.add(entity);
			}
		}
		stylesService.saveBatch(stylesList);
		return R.status(true);

	}

	public Double checkNumber(String str){

		Double price=0d;
		if(null == str || "" == str){
			return price;
		}
		str=str.replace("万", "");
		boolean int_flag = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean double_flag = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
		if(int_flag){
			return price=Double.parseDouble(str);
		} else if(double_flag){
			return price=Double.parseDouble(str);
		} else {
			return price;
		}

	}

}
