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

import org.springblade.car.dto.MemberFansDTO;
import org.springblade.car.entity.Member;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.MemberFans;
import org.springblade.car.vo.MemberFansVO;
import org.springblade.car.service.IMemberFansService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-10-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/memberfans")
@Api(value = "", tags = "后台-关注粉丝接口")
@ApiSort(2003)
public class MemberFansController extends BladeController {

	private final IMemberFansService memberFansService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入memberFans")
	public R<MemberFans> detail(MemberFans memberFans) {
		MemberFans detail = memberFansService.getOne(Condition.getQueryWrapper(memberFans));
		return R.data(detail);
	}



	/**
	 * 自定义分页
	 */
	@GetMapping("/fansPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "粉丝分页", notes = "传入memberFans")
	public R<IPage<Member>> fansPage(MemberFansVO memberFans, Query query) {
		IPage<Member> pages = memberFansService.selectMemberFansPage(Condition.getPage(query), memberFans);
		return R.data(pages);
	}
	@GetMapping("/fcousPage")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "fcousPage", notes = "传入memberFans")
	public R<IPage<Member>> page(MemberFansVO memberFans, Query query) {
		IPage<Member> pages = memberFansService.selectMemberFansPage(Condition.getPage(query), memberFans);
		return R.data(pages);
	}
	
	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(memberFansService.removeByIds(Func.toLongList(ids)));
	}

	
}
