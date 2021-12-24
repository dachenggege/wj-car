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
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springblade.car.Req.ShopPhoneReq;
import org.springblade.car.dto.*;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.service.*;
import org.springblade.car.vo.*;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.exception.ServiceException;
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
import java.util.*;

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
		if(Func.isEmpty(shop.getShopName())){
			return R.fail("门店名称不能为空");
		}
		if(Func.isEmpty(shop.getProvince()) || Func.isEmpty(shop.getCity()) || Func.isEmpty(shop.getCounty()) ||  Func.isEmpty(shop.getShopAddress())){
			return R.fail("门店地址不能为空");
		}
		if(Func.isEmpty(shop.getLat()) || Func.isEmpty(shop.getLng())){
			return R.fail("门店经纬度不能为空");
		}
		MemberDTO cl = wMemberFactory.getMemberDTO(request);
		//判断是否有创建门店的权限
		MemberRights  rights=  cl.getRights();
		if(!rights.getIsCreateShop()){
			return R.fail("对不起，金钻会员以上才能创建门店哦");
		}
		if(rights.getCreateShopNum()<=cl.getMyShopNum()){
			//个人、商家金钻
			if(Func.equals(cl.getMemberLv(),2) || Func.equals(cl.getMemberLv(),3) ){
				return R.fail("对不起，黑钻以上会员才能创建多个门店哦");
			}
			//商家黑钻
			if(Func.equals(cl.getMemberLv(),4)){
				return R.fail("对不起，黑钻PULS会员才能创建5个门店以上");
			}
			return R.fail("对不起您没有权限创建门店哦");
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
	@Transactional(rollbackFor = Exception.class)
	public R removeShop(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		Member cl = wMemberFactory.getMember(request);
		Shop shop= shopService.getById(id);
		if(Func.isNotEmpty(shop)){
			if(!Func.equals(cl.getId(),shop.getMemberId())){
				return R.fail("您没有权限删除门店");
			}
		}

		//删除门店 删除门店成员，删除门店结盟
		shopService.removeByIds(Func.toLongList(id));
		shopMemberService.delByShopId(Long.valueOf(id));
		shopAlliedService.delByShopId(Long.valueOf(id));

		// 门店车源 转移到个人车源
		Cars cars=new Cars();
		cars.setShopId(Long.valueOf(id));
		List<Cars> carsList= carsService.list(Condition.getQueryWrapper(cars));
		Member member=memberService.getById(shop.getMemberId());

		for(Cars car:carsList){
			if(Func.isEmpty(member)){
				car.setProvince(member.getProvince());
				car.setProvinceName(member.getProvinceName());
				car.setCity(member.getCity());
				car.setCityName(member.getCityName());
				car.setCounty(member.getCounty());
				car.setCountyName(member.getCountyName());
			}
			car.setShopId(null);
			car.setVest(1);
		}
		carsService.updateBatchById(carsList);

		return R.success("操作成功");
	}

	/**
	 * 自定义分页 用户门成员
	 */
	@GetMapping("/shopMemberPage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "门店成员分页", notes = "传入shopMember对象")
	public R<IPage<ShopMemberDTO>> shopMemberPage(ShopMemberReq shopMember, Query query) {
		if(Func.isEmpty(shopMember.getShopId())){
			R.fail("门店ID不能为空哦");
		}
		IPage<ShopMemberDTO> pages = shopMemberService.selectShopMemberPage(Condition.getPage(query), shopMember);
		return R.data(pages);
	}

	@GetMapping("/shopMemberRoleRight")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "我在门店成员里的权限")
	public R<ShopMemberRoleRightRep> shopMemberRight(
			@ApiParam(value = "门店id", required = true) @RequestParam Long shop_id) {
		Member cl = wMemberFactory.getMember(request);
//		ShopMember shopMember=new ShopMember();
//		shopMember.setShopId(shop_id);
//		shopMember.setStaffId(cl.getId());
//		ShopMember shopMemberRole = shopMemberService.getOne(Condition.getQueryWrapper(shopMember));
//		if(Func.isEmpty(shopMemberRole)){
//			return R.fail("您是不该门店的店员哦");
//		}
		Map<String,Object> map=new HashMap<>();
		map.put("shop_id",shop_id);
		map.put("staff_id",cl.getId());
		ShopMemberRoleRightDTO shopMemberRight = shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(shopMemberRight)){
			return R.fail("您是不该门店的店员哦");
		}
		ShopMemberRoleRightRep roleRightRep=new ShopMemberRoleRightRep();
		BeanUtils.copyProperties(shopMemberRight,roleRightRep);
		roleRightRep.setName(cl.getName());
		roleRightRep.setPhone(cl.getPhone());
		return R.data(roleRightRep);
	}

	@GetMapping("/shopMemberRightList")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "门店成员角色及权限列表")
	public R<List<ShopMemberRoleRight>> shopMemberRightList() {
		List<ShopMemberRoleRight> shopMemberRight = shopMemberService.selectShopMemberRoleRight();
		return R.data(shopMemberRight);
	}
	@GetMapping("/getMemberByPhone")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "根据电话获取会员信息")
	public R<List<ShopMemberRep>> getMember(@ApiParam(value = "门店Id") @RequestParam(value = "shopId", required = true) Long shopId,
									 @ApiParam(value = "用户电话") @RequestParam(value = "phone", required = false) String phone) {
		Map<String,Object> map=new HashMap<>();
		map.put("shopId",shopId);
		map.put("phone",phone);
		List<ShopMemberRep> list = shopMemberService.queryShopMemberPage(map);
		return R.data(list);
	}
	/**
	 * 新增 门店成员表
	 */
	@PostMapping("/saveShopMember")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增门店成员", notes = "传入shopMember")
	public R saveShopMember(@Valid @RequestBody ShopMember shopMember) {
		MemberDTO cl = wMemberFactory.getMemberDTO(request);
		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopMember.getShopId());
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			return R.fail("您没有权限添加店员哦");
		}
		if(!roleRightDTO.getIsEditStaff()){
			return R.fail("您没有权限添加店员哦");
		}
		//
		MemberRights  rights=  cl.getRights();
		ShopMember sm=new ShopMember();
		sm.setShopId(shopMember.getShopId());
		Integer mm= shopMemberService.count(Condition.getQueryWrapper(sm));
		if(rights.getShopMemberNum()<=(mm+1)){
			//return R.fail("对不起您的会员等级门店只能添加"+rights.getShopMemberNum()+"个店员哦");
			return R.fail("对不起，请升级至黑钻会员以上才能继续招人");
		}
		//店员权益
		MemberDTO Staff = wMemberFactory.getMemberByid(shopMember.getStaffId());
		MemberRights  Staffrights=  Staff.getRights();
		if(Func.isEmpty(Staff)){
			return	R.fail("该会员权益有问题，请检查");
		}
		Integer joinShopNUm=Staff.getMyJoinShopNum()==null?0:Staff.getMyJoinShopNum();
		if(joinShopNUm>=Staffrights.getJoinShopNum()){
			return R.fail("请对方升级会员才能加入多家门店");
		}

		return R.status(shopMemberService.save(shopMember));
	}

	@PostMapping("/updateShopMember")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "修改门店成员", notes = "传入shopMember")
	public R updateShopMember(@Valid @RequestBody ShopMember shopMember) {
		if(Func.isEmpty(shopMember.getId())){
			R.fail("门店店员主键id不能为空哦");
		}

		Member cl = wMemberFactory.getMember(request);
		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopMember.getShopId());
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限修改店员哦");
		}
		if(!roleRightDTO.getIsEditStaff()){
			R.fail("您没有权限修改店员哦");
		}
		return R.status(shopMemberService.updateById(shopMember));
	}
	/**
	 * 删除 门店成员表
	 */
	@PostMapping("/removeShopMember")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除门店成员", notes = "传入ids")
	public R removeShopMember(@ApiParam(value = "主键集合", required = true) @RequestParam String id,
							  @ApiParam(value = "门店id", required = true) @RequestParam Long shopId) {
		Member cl = wMemberFactory.getMember(request);

		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopId);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
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
	@ApiOperation(value = "门店车源分页", notes = "cars")
	public R<IPage<CarsDTO>> shopCarpage(CarsVO cars, Query query) {
		if(Func.isEmpty(cars.getShopId())){
			R.fail("门店id不能为空哦");
		}
		if(Func.isEmpty(cars.getStatus())){
			cars.setStatus(1);
		}
		cars.setVest(2);
		IPage<CarsDTO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	/**
	 * 删除 车源表
	 */
	@PostMapping("/removeShopCar")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "删除门店车源", notes = "传入ids")
	public R removeShopCar(@ApiParam(value = "主键集合", required = true) @RequestParam String ids,
						   @ApiParam(value = "门店id", required = true) @RequestParam Long shopId) {
		Member cl = wMemberFactory.getMember(request);

		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopId);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限删除门店车源哦");
		}
		if(!roleRightDTO.getIsEditCar()){
			R.fail("您没有权限删除门店车源哦");
		}
		return R.status(carsService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/upDownShopCar")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "上架下架门店车源")
	public R upDownShopCar(@ApiParam(value = "车源id", required = true) @RequestParam Long id,
						   @ApiParam(value = "状态", required = true) @RequestParam Integer status,
						   @ApiParam(value = "门店id", required = true) @RequestParam Long shopId) {
		Member cl = wMemberFactory.getMember(request);

		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopId);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限操作哦");
		}
		if(!roleRightDTO.getIsDownCar()){
			R.fail("您没有权限操作哦");
		}
		return R.status(carsService.upDownShopCar(id,status));
	}
	@GetMapping("/updateListtime")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "上新车源时间", notes = "传入id")
	public R updateListtime(@ApiParam(value = "车源主键", required = true) @RequestParam Long id,
							@ApiParam(value = "门店id", required = true) @RequestParam Long shopId) {
//		Member cl = wMemberFactory.getMember(request);
//
//		Map<String,Object> map=new HashMap<>();
//		map.put("staff_id",cl.getId());
//		map.put("shop_id",shopId);
//		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
//		if(Func.isEmpty(roleRightDTO)){
//			R.fail("您没有权限操作哦");
//		}
//		if(!roleRightDTO.getIsDownCar()){
//			R.fail("您没有权限操作哦");
//		}

		return R.status(carsService.updateCarListTime(id));
	}
	@GetMapping("/shopCarsBeenCallPage")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "门店车源被电话咨询记录", notes = "传入cars")
	public R<IPage<carsBeenCallDTO>> shopCarsBeenCallPage(@ApiParam(value = "门店id", required = true) @RequestParam Long shopId,
														  Query query) {
		Member cl = wMemberFactory.getMember(request);
		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopId);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限操作哦");
		}
		if(!roleRightDTO.getIsLookCarBrowse()){
			R.fail("您没有权限操作哦");
		}

		CarsVO cars = new CarsVO();
		cars.setShopId(shopId);
		IPage<carsBeenCallDTO> pages = carsService.carsBeenCallPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	@GetMapping("/hadAlliedShopPage")
	@ApiOperationSupport(order = 9001)
	@ApiOperation(value = "已结盟门店分页")
	public R<IPage<ShopAlliedDTO>> hadAlliedShopPage(@ApiParam(value = "门店id", required = true) @RequestParam Long shopId,
													 Query query) {
		ShopAlliedDTO shopAlliedDTO=new ShopAlliedDTO();
		shopAlliedDTO.setShopId(shopId);
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
		IPage<ShopAlliedDTO> pages = shopAlliedService.applyAlliedShopPage(Condition.getPage(query), shopAlliedDTO);
		return R.data(pages);
	}
	/**
	 * 自定义分页 用户门店表
	 */
	@GetMapping("/selectShopAlliedPage")
	@ApiOperationSupport(order = 9003)
	@ApiOperation(value = "门店结盟-查询门店分页", notes = "传入shop")
	public R<IPage<ShopAlliedDTO>> selectShopAlliedPage(@ApiParam(value = "我的门店id", required = true) @RequestParam Long shopId,
														@ApiParam(value = "门店名称", required = false) @RequestParam String shopName,
														Query query) {
		Member cl = wMemberFactory.getMember(request);
		ShopAlliedDTO shopAlliedDTO=new ShopAlliedDTO();
		shopAlliedDTO.setShopId(shopId);
		shopAlliedDTO.setShopName(shopName);
		shopAlliedDTO.setShopMemberId(cl.getId());
		IPage<ShopAlliedDTO> pages = shopAlliedService.selectShopAlliedPage(Condition.getPage(query), shopAlliedDTO);
		return R.data(pages);
	}

	@PostMapping("/applyAllied")
	@ApiOperationSupport(order = 9004)
	@ApiOperation(value = "申请门店结盟", notes = "shopAllied")
	public R applyAllied(@Valid @RequestBody ShopAllied shopAllied) {
		Member cl = wMemberFactory.getMember(request);


		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopAllied.getShopId());
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限申请门店结盟的权限哦");
		}
		if(!roleRightDTO.getIsEditShopAllied()){
			R.fail("您没有权限申请门店结盟的权限哦");
		}
		//查询申请者门店已结盟的门店数量
		ShopAllied apply=new ShopAllied();
		apply.setShopId(shopAllied.getShopId());
		Integer applyShopHadAlliedNum= shopAlliedService.count(Condition.getQueryWrapper(apply));



		shopAllied.setApplyMemberId(cl.getId());
		Boolean res=shopAlliedService.save(shopAllied);
		return R.status(res);
	}
	@GetMapping("/updateShopalliedStatus")
	@ApiOperationSupport(order = 9005)
	@ApiOperation(value = "修改门店结盟状态")
	public R updateShopalliedStatus(@ApiParam(value = "结盟id", required = true) @RequestParam Long id,
									@ApiParam(value = "门店id", required = true) @RequestParam Long shopId,
						   @ApiParam(value = "状态", required = true) @RequestParam Integer alliedStatus) {
		Member cl = wMemberFactory.getMember(request);

		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopId);
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限操作哦");
		}
		if(!roleRightDTO.getIsEditShopAllied()){
			R.fail("您没有权限操作哦");
		}
		ShopAllied shopAllied =shopAlliedService.getById(id);
		if(Func.isEmpty(shopAllied)){
			R.fail("结盟id不存在");
		}
		shopAllied.setAlliedStatus(alliedStatus);
		return R.status(shopAlliedService.updateById(shopAllied));
	}
	@GetMapping("/shopCollectCarpage")
	@ApiOperationSupport(order = 9006)
	@ApiOperation(value = "联盟车源分页", notes = "传入shopCarReq")
	public R<IPage<CarsDTO>> shopCollectCarpage(ShopCarReq shopCarReq, Query query) {
		Member cl = wMemberFactory.getMember(request);

		Map<String,Object> map=new HashMap<>();
		map.put("staff_id",cl.getId());
		map.put("shop_id",shopCarReq.getShopId());
		if(Func.isEmpty(shopCarReq.getShopId())){
			R.fail("shopId没传值");
		}
		ShopMemberRoleRightDTO roleRightDTO=shopMemberService.getShopMemberRight(map);
		if(Func.isEmpty(roleRightDTO)){
			R.fail("您没有权限查看联盟车源哦");
		}
		if(!roleRightDTO.getIsLookAlliedCar()){
			R.fail("您没有权限查看联盟车源哦");
		}

		ShopMember shopMember=new ShopMember();
//		List<ShopMember> list= shopMemberService.list(Condition.getQueryWrapper(shopMember));
//		List<Long> memberIds=new ArrayList<>();
		CarsVO cars=new CarsVO();
		BeanUtils.copyProperties(shopCarReq,cars);
//		cars.setMemberIds(memberIds);
		cars.setPallname(shopCarReq.getPallname());
		IPage<CarsDTO> pages = carsService.shopAlliedCarPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

}
