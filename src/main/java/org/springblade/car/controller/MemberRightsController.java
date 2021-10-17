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

import com.github.xiaoymin.knife4j.annotations.ApiSort;
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
import org.springblade.car.entity.MemberRights;
import org.springblade.car.vo.MemberRightsVO;
import org.springblade.car.service.IMemberRightsService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 会员体系权益表 控制器
 *
 * @author BladeX
 * @since 2021-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/memberrights")
@Api(value = "会员体系权益表", tags = "后台-会员体系权益表接口")
@ApiSort(2004)
public class MemberRightsController extends BladeController {

	private final IMemberRightsService memberRightsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入memberRights")
	public R<MemberRights> detail(MemberRights memberRights) {
		MemberRights detail = memberRightsService.getOne(Condition.getQueryWrapper(memberRights));
		return R.data(detail);
	}

	/**
	 * 自定义分页 会员体系权益表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入memberRights")
	public R<IPage<MemberRightsVO>> page(MemberRightsVO memberRights, Query query) {
		IPage<MemberRightsVO> pages = memberRightsService.selectMemberRightsPage(Condition.getPage(query), memberRights);
		return R.data(pages);
	}

	/**
	 * 新增 会员体系权益表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入memberRights")
	public R save(@Valid @RequestBody MemberRights memberRights) {
		return R.status(memberRightsService.save(memberRights));
	}

	/**
	 * 修改 会员体系权益表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入memberRights")
	public R update(@Valid @RequestBody MemberRights memberRights) {
		return R.status(memberRightsService.updateById(memberRights));
	}

	/**
	 * 删除 会员体系权益表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(memberRightsService.removeByIds(Func.toLongList(ids)));
	}

	
}
