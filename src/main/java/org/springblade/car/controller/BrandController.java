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
import org.springblade.car.entity.JuHeBrand;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.excel.util.ExcelUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Brand;
import org.springblade.car.vo.BrandVO;
import org.springblade.car.service.IBrandService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/brand")
@Api(value = "品牌表", tags = "后台设置管理-品牌管理")
public class BrandController extends BladeController {

	private final IBrandService brandService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入brand")
	public R<Brand> detail(Brand brand) {
		Brand detail = brandService.getOne(Condition.getQueryWrapper(brand));
		return R.data(detail);
	}

	/**
	 *  品牌表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入brand")
	public R<List<Brand>> list(Brand brand) {
		List<Brand> pages = brandService.list(Condition.getQueryWrapper(brand));
		return R.data(pages);
	}

	/**
	 * 自定义分页 品牌表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入brand")
	public R<IPage<BrandVO>> page(BrandVO brand, Query query) {
		IPage<BrandVO> pages = brandService.selectBrandPage(Condition.getPage(query), brand);
		return R.data(pages);
	}

	/**
	 * 新增 品牌表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入brand")
	public R save(@Valid @RequestBody Brand brand) {
		return R.status(brandService.save(brand));
	}

	/**
	 * 修改 品牌表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入brand")
	public R update(@Valid @RequestBody Brand brand) {
		return R.status(brandService.updateById(brand));
	}

//	/**
//	 * 新增或修改 品牌表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入brand")
//	public R submit(@Valid @RequestBody Brand brand) {
//		return R.status(brandService.saveOrUpdate(brand));
//	}

	
	/**
	 * 删除 品牌表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(brandService.removeByIds(Func.toLongList(ids)));
	}


	@PostMapping("/uploadBrand")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "excel导入Brand信息", notes = "")
	@Synchronized
	@Transactional
	public R uploadStation(@RequestParam MultipartFile excel) {

		List<JuHeBrand> list = new ArrayList<>();
		list = ExcelUtils.read(excel, JuHeBrand.class);
		List<Brand> brandList=new ArrayList<>();
		for(JuHeBrand dto:list){
			Brand h=brandService.getById(Long.valueOf(dto.getId()));
			if(Func.isEmpty(h)) {
				Brand brand = new Brand();
				brand.setId(Long.valueOf(dto.getId()));
				brand.setBrandName(dto.getBrand_name());
				brand.setBrandPic(dto.getBrand_logo());
				brand.setBrandMark(dto.getFirst_letter());
				brand.setBrandEn(dto.getFirst_letter());
				brand.setOpen(true);
				brandList.add(brand);
			}
		}
		brandService.saveBatch(brandList);
		return R.status(true);

	}

	
}
