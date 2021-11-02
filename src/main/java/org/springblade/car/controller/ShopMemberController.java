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

import org.springblade.car.dto.ShopMemberDTO;
import org.springblade.car.dto.ShopMemberReq;
import org.springblade.car.vo.MemberVO;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.ShopMember;
import org.springblade.car.vo.ShopMemberVO;
import org.springblade.car.service.IShopMemberService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 门店成员表 控制器
 *
 * @author BladeX
 * @since 2021-08-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/shopmember")
@Api(value = "门店成员表", tags = "v2后台门店管理-门店成员列表")
public class ShopMemberController extends BladeController {

	private final IShopMemberService shopMemberService;




	/**
	 * 自定义分页 门店成员表
	 */
	@GetMapping("/shopMemberPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "门店成员", notes = "传入shopMember对象")
	public R<IPage<ShopMemberDTO>> page(ShopMemberReq shopMember, Query query) {
		IPage<ShopMemberDTO> pages = shopMemberService.selectShopMemberPage(Condition.getPage(query), shopMember);
		return R.data(pages);
	}

	/**
	 * 删除 门店成员表
	 */
	@PostMapping("/removeShopMember")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "删除门店成员", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(shopMemberService.removeByIds(Func.toLongList(ids)));
	}

	
}
