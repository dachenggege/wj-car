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

import org.springblade.car.Req.MemberReq;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Member;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.service.IMemberService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 用户表 控制器
 *
 * @author BladeX
 * @since 2021-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/member")
@Api(value = "用户表", tags = "用户表接口")
public class MemberController extends BladeController {

	private final IMemberService memberService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入member")
	public R<Member> detail(Member member) {
		Member detail = memberService.getOne(Condition.getQueryWrapper(member));
		return R.data(detail);
	}

	/**
	 * 自定义分页 用户表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入member")
	public R<IPage<MemberVO>> page(MemberReq member, Query query) {
		IPage<MemberVO> pages = memberService.selectMemberPage(Condition.getPage(query), member);
		return R.data(pages);
	}

	/**
	 * 新增 用户表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入member")
	public R save(@Valid @RequestBody Member member) {
		return R.status(memberService.save(member));
	}

	/**
	 * 修改 用户表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入member")
	public R update(@Valid @RequestBody Member member) {
		return R.status(memberService.updateById(member));
	}

	/**
	 * 新增或修改 用户表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入member")
	public R submit(@Valid @RequestBody Member member) {
		return R.status(memberService.saveOrUpdate(member));
	}

	
	/**
	 * 删除 用户表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(memberService.removeByIds(Func.toLongList(ids)));
	}

	
}
