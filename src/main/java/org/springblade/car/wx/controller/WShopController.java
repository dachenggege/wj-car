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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.controller.ShopMemberController;
import org.springblade.car.dto.ShopCarReq;
import org.springblade.car.dto.ShopCollectDTO;
import org.springblade.car.dto.ShopMemberDTO;
import org.springblade.car.dto.ShopMemberReq;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.service.*;
import org.springblade.car.vo.*;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@RequestMapping("second-hand-car/wshop")
@Api(value = "用户门店表", tags = "微信-门店接口")
public class WShopController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IShopService shopService;
	private final IShopMemberService shopMemberService;
	private final IShopCollectService shopCollectService;
	private  final ICarsService carsService;
	private IMemberService memberService;


	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "门店详情", notes = "传入shop")
	public R<ShopVO> detail(ShopVO shop) {
		ShopVO detail = shopService.getShopDetail(shop);
		return R.data(detail);
	}


	/**
	 * 自定义分页 用户门店表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "门店分页", notes = "传入shop")
	public R<IPage<ShopVO>> page(ShopVO shop, Query query) {
		IPage<ShopVO> pages = shopService.selectShopPage(Condition.getPage(query), shop);
		return R.data(pages);
	}

	@GetMapping("/myShopPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "我的门店分页", notes = "传入shop")
	public R<IPage<ShopVO>> myShopPage(ShopVO shop, Query query) {
		Member cl = wMemberFactory.getMember(request);
		shop.setMemberId(cl.getId());
		IPage<ShopVO> pages = shopService.selectMyShopPage(Condition.getPage(query), shop);
		return R.data(pages);
	}

	/**
	 * 新增 用户门店表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增门店", notes = "传入shop")
	@Transactional
	public R save(@Valid @RequestBody Shop shop) {
		shop.setId(NumberUtil.getRandomNumber(6,8));
		shop.setAuditStatus(AuditStatus.AUDITING.id);
		ShopMember member=new ShopMember();
		member.setMemberId(shop.getMemberId());
		member.setShopId(shop.getId());
		shopMemberService.save(member);

		return R.status(shopService.save(shop));
	}

	/**
	 * 修改 用户门店表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改门店", notes = "传入shop")
	public R update(@Valid @RequestBody Shop shop) {
		shop.setAuditStatus(AuditStatus.AUDITING.id);
		return R.status(shopService.updateById(shop));
	}

	/**
	 * 删除 用户门店表
	 */
	@PostMapping("/removeShop")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除门店", notes = "传入ids")
	public R removeShop(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(shopService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 自定义分页 用户门成员
	 */
	@GetMapping("/shopMemberPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "门店成员", notes = "传入shopMember对象")
	public R<IPage<ShopMemberDTO>> page(ShopMemberReq shopMember, Query query) {
		IPage<ShopMemberDTO> pages = shopMemberService.selectShopMemberPage(Condition.getPage(query), shopMember);
		return R.data(pages);
	}

	/**
	 * 新增 门店成员表
	 */
	@PostMapping("/saveShopMember")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增门店成员", notes = "传入shopMember")
	public R saveShopMember(@Valid @RequestBody ShopMember shopMember) {
		return R.status(shopMemberService.save(shopMember));
	}
	/**
	 * 删除 门店成员表
	 */
	@PostMapping("/removeShopMember")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除门店成员", notes = "传入ids")
	public R removeShopMember(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(shopMemberService.removeByIds(Func.toLongList(ids)));
	}


	@GetMapping("/shopCollectPage")
	@ApiOperationSupport(order = 9001)
	@ApiOperation(value = "已结盟门店（isCollect=1 ）的门店分页", notes = "传入shopCollect")
	public R<IPage<ShopCollectDTO>> shopCollectPage(ShopCollectDTO shopCollect,Query query) {
		Member cl = wMemberFactory.getMember(request);
		shopCollect.setCollecterId(cl.getId());
		shopCollect.setIsCollect(1);
		IPage<ShopCollectDTO> pages = shopCollectService.selectShopCollectAcceptPage(Condition.getPage(query), shopCollect);
		return R.data(pages);
	}

	@GetMapping("/selectShopCollectAcceptPage")
	@ApiOperationSupport(order = 9002)
	@ApiOperation(value = "结盟门店申请列表isCollect=0）的分页", notes = "传入shopCollect")
	public R<IPage<ShopCollectDTO>> selectShopCollectAcceptPage(ShopCollectDTO shopCollect,Query query) {
		Member cl = wMemberFactory.getMember(request);
		shopCollect.setShopMemberId(cl.getId());
		shopCollect.setIsCollect(0);
		IPage<ShopCollectDTO> pages = shopCollectService.selectShopCollectAcceptPage(Condition.getPage(query), shopCollect);
		return R.data(pages);
	}

	@PostMapping("/shopCollect")
	@ApiOperationSupport(order = 9003)
	@ApiOperation(value = "门店结盟（申请结盟isCollect=0,同意结盟isCollect=1，取消结盟isCollect=2）", notes = "shopCollect")
	public R shopCollect(@Valid @RequestBody ShopCollectDTO shopCollectDTO) {
		Member cl = wMemberFactory.getMember(request);
		boolean res=false;
		if(shopCollectDTO.getIsCollect()==0){
			ShopCollect shopCollect =new ShopCollect();
			shopCollect.setShopId(shopCollectDTO.getShopId());
			shopCollect.setMemberId(cl.getId());
			res=shopCollectService.save(shopCollect);
		}
		else{
			ShopCollect shopCollect =shopCollectService.getById(shopCollectDTO.getId());
			shopCollect.setIsCollect(shopCollectDTO.getIsCollect());
			res=shopCollectService.updateById(shopCollect);
		}


		return R.status(res);
	}

	@GetMapping("/shopCollectCarpage")
	@ApiOperationSupport(order = 9004)
	@ApiOperation(value = "联盟车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsVO>> shopCollectCarpage(ShopCarReq shopCarReq, Query query) {
		Member cl = wMemberFactory.getMember(request);
		ShopMember shopMember=new ShopMember();
		shopMember.setMemberId(cl.getId());
		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
		List<Long> memberIds=new ArrayList<>();
		for(ShopMember m:list){
			memberIds.add(m.getMemberId());
		}
		CarsVO cars=new CarsVO();
		BeanUtils.copyProperties(shopCarReq,cars);
		cars.setMemberIds(memberIds);
		cars.setPallname(shopCarReq.getPallname());
		IPage<CarsVO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/shopCarpage")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "门店车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsVO>> shopCarpage(ShopCarReq shopCarReq, Query query) {
		ShopMember shopMember=new ShopMember();
		shopMember.setShopId(shopCarReq.getShopId());
		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
		List<Long> memberIds=new ArrayList<>();
		for(ShopMember m:list){
			memberIds.add(m.getMemberId());
		}
		CarsVO cars=new CarsVO();
		BeanUtils.copyProperties(shopCarReq,cars);
		cars.setMemberIds(memberIds);
		cars.setPallname(shopCarReq.getPallname());
		IPage<CarsVO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	/**
	 * 删除 车源表
	 */
	@PostMapping("/removeShopCar")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "删除门店车源", notes = "传入ids")
	public R removeShopCar(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(carsService.removeByIds(Func.toLongList(ids)));
	}


}
