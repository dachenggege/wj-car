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
import org.springblade.car.Req.ShopReq;
import org.springblade.car.dto.ShopCarReq;
import org.springblade.car.dto.ShopDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.ShopMember;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.RoleType;
import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.service.ICarsService;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IShopMemberService;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.MemberVO;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.DateUtil;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.Shop;
import org.springblade.car.vo.ShopVO;
import org.springblade.car.service.IShopService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户门店表 控制器
 *
 * @author BladeX
 * @since 2021-07-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/shop")
@Api(value = "用户门店表", tags = "v2后台门店管理-门店列表")
public class ShopController extends BladeController {

	private final IShopService shopService;
	private final IShopMemberService shopMemberService;
	private  final ICarsService carsService;
	private final IMemberService memberService;
	private final UserAreaFactory userAreaFactory;


	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "门店详情", notes = "传入shop")
	public R<ShopDTO> detail(Long shopId) {
		ShopDTO detail = shopService.getShopDetail(shopId);
		return R.data(detail);

	}

//	/**
//	 * 用户门店表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "门店列表", notes = "传入shop")
//	public R<List<Shop>> list(Shop shop) {
//		List<Shop> list = shopService.list(Condition.getQueryWrapper(shop));
//		return R.data(list);
//	}

	/**
	 * 自定义分页 用户门店表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "门店分页", notes = "传入shop")
	public R<IPage<ShopDTO>> page(ShopReq shop, Query query) {

		MemberReq memberReq=userAreaFactory.getUserAreas();
		shop.setAreas(memberReq.getAreas());
		shop.setNoareas(memberReq.getNoareas());
		shop.setUserId(memberReq.getUserId());
		IPage<ShopDTO> pages = shopService.selectShopPage(Condition.getPage(query), shop);
		return R.data(pages);
	}

	/**
	 * 新增 用户门店表
	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增门店", notes = "传入shop")
//	public R save(@Valid @RequestBody Shop shop) {
//		shop.setId(NumberUtil.getRandomNumber(6,8));
//		shop.setAuditStatus(AuditStatus.AUDITING.id);
//		ShopMember member=new ShopMember();
//		member.setMemberId(shop.getMemberId());
//		member.setShopId(shop.getId());
//		shopMemberService.save(member);
//		return R.status(shopService.save(shop));
//	}

	/**
	 * 修改 用户门店表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改门店", notes = "传入shop")
	public R update(@Valid @RequestBody Shop shop) {
		//如果是创建门店审核通过
		if(Func.equals(shop.getAuditStatus(), AuditStatus.PASS.id)){
			shop.setAuditTime(new Date());
		}
		//如果是创建门店审核不通过
		if(Func.equals(shop.getAuditStatus(), AuditStatus.NOPASS.id)){
			shop.setAuditTime(new Date());
		}
		return R.status(shopService.updateById(shop));
	}

	/**
	 * 删除 用户门店表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除门店", notes = "传入ids")
	@Transactional(rollbackFor = Exception.class)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(shopService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/shopCarPage")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "门店车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsVO>> shopCarpage(ShopCarReq shopCarReq, Query query) {
		ShopMember shopMember=new ShopMember();
		shopMember.setShopId(shopCarReq.getShopId());
		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
		List<Long> memberIds=new ArrayList<>();
		for(ShopMember m:list){
			memberIds.add(m.getStaffId());
		}
		CarsVO cars=new CarsVO();
		BeanUtils.copyProperties(shopCarReq,cars);
		cars.setMemberIds(memberIds);
		IPage<CarsVO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

}
