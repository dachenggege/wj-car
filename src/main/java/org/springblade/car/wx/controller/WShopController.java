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
import org.springblade.car.Req.ShopPhoneReq;
import org.springblade.car.dto.*;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.service.*;
import org.springblade.car.vo.*;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
@Api(value = "用户门店表", tags = "v2微信-门店接口")
@ApiSort(1006)
public class WShopController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IShopService shopService;
	private final IShopMemberService shopMemberService;
	private final IShopAlliedService shopAlliedService;
	private  final ICarsService carsService;
	private IMemberService memberService;



	/**
	 * 新增 用户门店表
	 */
	@PostMapping("/saveShop")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增门店", notes = "传入shop")
	@Transactional
	public R saveShop(@Valid @RequestBody Shop shop) {
		MemberDTO cl = wMemberFactory.getMember(request);
		//判断是否有创建门店的权限
		MemberRights  rights=  cl.getRights();
		if(!rights.getIsCreateShop()){
			return R.fail("对不起您没有创建门店的权限");
		}
		if(rights.getCreateShopNum()<=cl.getMyShopNum()){
			return R.fail("对不起您不能创建更多的门店了");
		}
		shop.setId(NumberUtil.getRandomNumber(6,8));
		shop.setAuditStatus(AuditStatus.AUDITING.id);
		shop.setPhone1(cl.getPhone());
		shop.setMemberId(cl.getId());
		//店主权限
		ShopMember member=new ShopMember();
		member.setStaffId(cl.getId());
		member.setStaffRole(1);
		member.setShopId(shop.getId());
		shopMemberService.save(member);

		return R.status(shopService.save(shop));
	}



	/**
	 * 详情
	 */
	@GetMapping("/shopDetail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "门店详情")
	public R<ShopDTO> shopDetail(@ApiParam(value = "门店id", required = true) @RequestParam Long shopId) {
		ShopDTO detail = shopService.getShopDetail(shopId);
		return R.data(detail);
	}

	@GetMapping("/myShopPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "我的门店分页", notes = "传入shop")
	public R<IPage<ShopDTO>> myShopPage(Query query) {
		Member cl = wMemberFactory.getMember(request);
		IPage<ShopDTO> pages = shopService.selectMyShopPage(Condition.getPage(query), cl.getId());
		return R.data(pages);
	}


	@PostMapping("/updateShopPhone")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改门店手机")
	public R update(@Valid @RequestBody ShopPhoneReq shopPhoneReq) {
		Member cl = wMemberFactory.getMember(request);
		Shop shop= shopService.getById(shopPhoneReq.getId());
		if(Func.isNotEmpty(shop)){
			if(!Func.equals(cl.getId(),shop.getMemberId())){
				return R.fail("您没有权限删除门店");
			}
		}
		if(Func.isEmpty(shop)){
			return R.fail("门店不存在");
		}
		shop.setPhone1(shopPhoneReq.getPhone1());
		shop.setPhone2(shopPhoneReq.getPhone2());
		shop.setPhone3(shopPhoneReq.getPhone3());
		shop.setPhone4(shopPhoneReq.getPhone4());
		shop.setPhone5(shopPhoneReq.getPhone5());
		return R.status(shopService.updateById(shop));
	}

	/**
	 * 删除 用户门店表
	 */
	@PostMapping("/removeShop")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除门店", notes = "传入ids")
	public R removeShop(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		Member cl = wMemberFactory.getMember(request);
		Shop shop= shopService.getById(id);
		if(Func.isNotEmpty(shop)){
			if(!Func.equals(cl.getId(),shop.getMemberId())){
				return R.fail("您没有权限删除门店");
			}
		}
		return R.status(shopService.removeByIds(Func.toLongList(id)));
	}

	/**
	 * 自定义分页 用户门成员
	 */
	@GetMapping("/shopMemberPage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "门店成员分页", notes = "传入shopMember对象")
	public R<IPage<ShopMemberDTO>> shopMemberPage(ShopMemberReq shopMember, Query query) {
		IPage<ShopMemberDTO> pages = shopMemberService.selectShopMemberPage(Condition.getPage(query), shopMember);
		return R.data(pages);
	}


	@GetMapping("/shopMemberRoleRight")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "门店成员角色及权限")
	public R<List<ShopMemberRoleRight>> shopMemberRight() {
		List<ShopMemberRoleRight> shopMemberRight = shopMemberService.selectShopMemberRoleRight();
		return R.data(shopMemberRight);
	}


	/**
	 * 新增 门店成员表
	 */
	@PostMapping("/saveShopMember")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增门店成员", notes = "传入shopMember")
	public R saveShopMember(@Valid @RequestBody ShopMember shopMember) {
		Member cl = wMemberFactory.getMember(request);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(cl.getId());
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限添加店员哦");
		}
		if(!roleRightDTO.getIsEditStaff()){
			R.fail("您没有权限添加店员哦");
		}
		return R.status(shopMemberService.save(shopMember));
	}
	/**
	 * 删除 门店成员表
	 */
	@PostMapping("/removeShopMember")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除门店成员", notes = "传入ids")
	public R removeShopMember(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		Member cl = wMemberFactory.getMember(request);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(cl.getId());
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限删除店员哦");
		}
		if(!roleRightDTO.getIsEditStaff()){
			R.fail("您没有权限删除店员哦");
		}
		return R.status(shopMemberService.removeByIds(Func.toLongList(id)));
	}


	/**
	 * 自定义分页 车源表
	 */
	@GetMapping("/shopCarpage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "门店车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsVO>> shopCarpage(ShopCarReq shopCarReq, Query query) {
		ShopMember shopMember=new ShopMember();
		shopMember.setShopId(shopCarReq.getShopId());
		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
		List<Long> memberIds=new ArrayList<>();
		for(ShopMember m:list){
			//memberIds.add(m.getMemberId());
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
	public R removeShopCar(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		Member cl = wMemberFactory.getMember(request);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(cl.getId());
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限删除门店车源哦");
		}
		if(!roleRightDTO.getIsEditCar()){
			R.fail("您没有权限删除门店车源哦");
		}
		return R.status(carsService.removeByIds(Func.toLongList(id)));
	}

	@GetMapping("/upDownShopCar")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "上架下架门店车源")
	public R upDownShopCar(@ApiParam(value = "车源id", required = true) @RequestParam Long carId,
						   @ApiParam(value = "车源id", required = true) @RequestParam Integer status) {
		Member cl = wMemberFactory.getMember(request);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(cl.getId());
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限添下架门店车源哦");
		}
		if(!roleRightDTO.getIsDownCar()){
			R.fail("您没有权限添下架门店车源哦");
		}
		return R.status(carsService.upDownShopCar(carId,status));
	}

	@GetMapping("/hadAlliedShopPage")
	@ApiOperationSupport(order = 9001)
	@ApiOperation(value = "已结盟门店分页")
	public R<IPage<ShopAlliedDTO>> hadAlliedShopPage(@ApiParam(value = "门店id", required = true) @RequestParam Long shopId,
													 Query query) {
		ShopAlliedDTO shopAlliedDTO=new ShopAlliedDTO();
		shopAlliedDTO.setShopId(shopId);
		shopAlliedDTO.setAlliedStatus(1);
		IPage<ShopAlliedDTO> pages = shopAlliedService.hadAlliedShopPage(Condition.getPage(query), shopAlliedDTO);
		return R.data(pages);
	}
	@GetMapping("/applyAlliedShopPage")
	@ApiOperationSupport(order = 9002)
	@ApiOperation(value = "申请结盟门店分页")
	public R<IPage<ShopAlliedDTO>> applyAlliedShopPage(@ApiParam(value = "门店id", required = true) @RequestParam Long shopId,
													 Query query) {
		//别人申请我的门店来结盟
		ShopAlliedDTO shopAlliedDTO=new ShopAlliedDTO();
		shopAlliedDTO.setAlliedShopId(shopId);
		shopAlliedDTO.setAlliedStatus(0);
		IPage<ShopAlliedDTO> pages = shopAlliedService.applyAlliedShopPage(Condition.getPage(query), shopAlliedDTO);
		return R.data(pages);
	}
	/**
	 * 自定义分页 用户门店表
	 */
	@GetMapping("/selectShopAlliedPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "门店结盟-查询门店分页", notes = "传入shop")
	public R<IPage<ShopAlliedDTO>> selectShopAlliedPage(@ApiParam(value = "门店名称", required = true) @RequestParam String shopName, Query query) {
		ShopAlliedDTO shopAlliedDTO=new ShopAlliedDTO();
		shopAlliedDTO.setShopName(shopName);
		IPage<ShopAlliedDTO> pages = shopAlliedService.selectShopAlliedPage(Condition.getPage(query), shopAlliedDTO);
		return R.data(pages);
	}

	@PostMapping("/applyAllied")
	@ApiOperationSupport(order = 9003)
	@ApiOperation(value = "申请门店结盟", notes = "shopCollect")
	public R applyAllied(@Valid @RequestBody ShopAllied shopAllied) {
		Member cl = wMemberFactory.getMember(request);
		shopAllied.setApplyMemberId(cl.getId());
		Boolean res=shopAlliedService.save(shopAllied);
		return R.status(res);
	}

	@GetMapping("/shopCollectCarpage")
	@ApiOperationSupport(order = 9004)
	@ApiOperation(value = "联盟车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsVO>> shopCollectCarpage(ShopCarReq shopCarReq, Query query) {
		Member cl = wMemberFactory.getMember(request);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(cl.getId());
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限查看联盟车源哦");
		}
		if(!roleRightDTO.getIsLookAlliedCar()){
			R.fail("您没有权限查看联盟车源哦");
		}

		ShopMember shopMember=new ShopMember();
		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
		List<Long> memberIds=new ArrayList<>();
		CarsVO cars=new CarsVO();
		BeanUtils.copyProperties(shopCarReq,cars);
		cars.setMemberIds(memberIds);
		cars.setPallname(shopCarReq.getPallname());
		IPage<CarsVO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

}
