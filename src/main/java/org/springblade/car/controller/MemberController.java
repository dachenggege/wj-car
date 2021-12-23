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

import org.springblade.car.Req.MemberReq;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Member;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.service.IMemberService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 用户表 控制器
 *
 * @author BladeX
 * @since 2021-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/member")
@Api(value = "用户表", tags = "v2后台-人员关联接口")
@ApiSort(2004)
public class MemberController extends BladeController {
	private UserAreaFactory userAreaFactory;
	private final IMemberService memberService;
	private WMemberFactory WMemberFactory;
	private BladeRedis bladeRedis;
	/**
	 * 详情
	 */
	@GetMapping("/memberDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "用户详情", notes = "传入id")
	public R<MemberDTO> detail(Long id) {
		MemberDTO detail = WMemberFactory.getMemberByid(id);
		return R.data(detail);
	}

	@GetMapping("/memberDetailByPhone")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "电话查询用户", notes = "传入phone")
	public R<List<MemberVO>> memberDetailByPhone(String phone) {
		MemberReq member=new MemberReq();
		member.setPhone(phone);
		List<MemberVO> detail = memberService.selectMemberList(member);
		return R.data(detail);
	}

	/**
	 * 自定义分页 用户表
	 */
	@GetMapping("/memberPage")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "用户分页", notes = "传入member")
	public R<IPage<MemberVO>> page(MemberReq member, Query query) {
		MemberReq memberReq=userAreaFactory.getUserAreas();
		member.setAreas(memberReq.getAreas());
		member.setNoareas(memberReq.getNoareas());
		member.setUserId(memberReq.getUserId());
		IPage<MemberVO> pages = memberService.selectMemberPage(Condition.getPage(query), member);
		return R.data(pages);
	}

	/**
	 * 修改 用户表
	 */
	@PostMapping("/memberUpdate")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改用户", notes = "传入member")
	public R update(@Valid @RequestBody Member member) {
		bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+member.getOpenid(),member);
		return R.status(memberService.updateById(member));
	}

	/**
	 * 删除 用户表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除用户", notes = "传入ids")
	@Transactional(rollbackFor = Exception.class)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(memberService.removeByIds(Func.toLongList(ids)));
	}

	
}
