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
package org.springblade.car.wx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.MemberFansDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberFans;
import org.springblade.car.service.IMemberFansService;
import org.springblade.car.vo.MemberFansVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-10-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/memberfans")
@Api(value = "", tags = "v2微信-我的粉丝和关注")
@ApiSort(1004)
public class WMemberFansController extends BladeController {

	private final IMemberFansService memberFansService;
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;


	/**
	 * 新增
	 */
	@PostMapping("/memberfansSave")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增关注", notes = "传入memberFans")
	public R memberfansSave(@Valid @RequestBody MemberFans memberFans) {
		if(Func.isEmpty(memberFans.getFansId())){
			return R.fail("粉丝id不能为空");
		}
		if(Func.isEmpty(memberFans.getMemberId())){
			return R.fail("用户id不能为空");
		}
		//Member cl = wMemberFactory.getMember(request);

		MemberFans fans=memberFansService.getOne(Condition.getQueryWrapper(memberFans));
		if(Func.isEmpty(fans)){
			memberFansService.save(memberFans);
		}else {
			fans.setIsDeleted(0);
			memberFansService.updateById(fans);
		}
		return R.success("成功");
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/myfansPage")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "我的粉丝分页")
	public R<IPage<Member>> myfansPage(Query query) {
		Member cl = wMemberFactory.getMember(request);
		MemberFans memberFans =new MemberFans();
		memberFans.setMemberId(cl.getId());
		IPage<Member> pages = memberFansService.selectMemberFansPage(Condition.getPage(query), memberFans);
		return R.data(pages);
	}
	@GetMapping("/myfcousPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "我的关注分页")
	public R<IPage<Member>> myfcousPage(Query query) {
		Member cl = wMemberFactory.getMember(request);
		MemberFansVO memberFans =new MemberFansVO();
		memberFans.setFansId(cl.getId());
		IPage<Member> pages = memberFansService.selectMemberFcousPage(Condition.getPage(query), memberFans);
		return R.data(pages);
	}

	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除我的关注", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		Member cl = wMemberFactory.getMember(request);
		MemberFans memberFans =new MemberFans();
		memberFans.setMemberId(Long.valueOf(id));
		memberFans.setFansId(cl.getId());
		MemberFans fans=memberFansService.getOne(Condition.getQueryWrapper(memberFans));
		if(Func.isNotEmpty(fans)){
			memberFansService.removeById(fans.getId());
		}
		return R.success("成功");
	}

	
}
