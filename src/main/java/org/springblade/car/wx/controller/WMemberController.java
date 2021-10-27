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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.dto.MemberRegist;
import org.springblade.car.dto.MerchantRegist;
import org.springblade.car.entity.CarsBrowse;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberCertification;
import org.springblade.car.entity.Shop;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.CarSort;
import org.springblade.car.enums.RoleType;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.vo.PayOrderVO;
import org.springblade.car.wx.WeixinPhone.AESForWeixinGetPhoneNumber;
import org.springblade.car.wx.WeixinPhone.WeixinPhoneDecryptInfo;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WxFactory;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.AesUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.vo.DictVO;
import org.springblade.modules.system.wrapper.DictWrapper;
import org.springblade.util.DateUtil;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/member")
@Api(value = "微信-个人中心", tags = "v2微信-个人中心接口")
@ApiSort(1002)
public class WMemberController extends BladeController {
	private HttpServletRequest request;
	private final BladeRedis bladeRedis;
	private WMemberFactory wMemberFactory;
	private final IDictService dictService;
	private final IMemberCertificationService memberCertificationService;

	private IMemberService memberService;
	private WxFactory wxFactory;
	private final ICarsService carsService;
	private final ICarsCollectService casCollectService;
	private IPayOrderService payOrderService;
	private final ICarsBrowseService carsBrowseService;


	@GetMapping("/getMember")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取自己个人信息")
	public R<MemberDTO> getMember(@ApiParam(value = "用户openid") @RequestParam(value = "openid", required = true) String openid) {
		Member client = new Member();
		client.setOpenid(openid);
		Member cl = memberService.getOne(Condition.getQueryWrapper(client));
		if(Func.isEmpty(cl)){
			throw new ServiceException("为获取到用户信息");
		}
		cl.setLastLogin(new Date());
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}
	@GetMapping("/getMemberById")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取用户信息")
	public R<MemberDTO> getMemberById(@ApiParam(value = "用户Id") @RequestParam(value = "memberId", required = true) String memberId) {
		Member cl = memberService.getById(memberId);
		if(Func.isEmpty(cl)){
			throw new ServiceException("为获取到用户信息");
		}
		cl.setLastLogin(new Date());
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}
	@PostMapping("/submitCertification")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "用户认证", notes = "传入authentication")
	public R submitCertification(@Valid @RequestBody MemberCertification memberCertification) {
		return R.status(memberCertificationService.saveOrUpdate(memberCertification));
	}

	@PostMapping("/memberRegist")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "注册会员")
	public R<MemberDTO> memberRegist(@Valid MemberRegist regist) {
		if(Func.isEmpty(regist.getName())){
			return R.fail("真实姓名不能为空");
		}
		Member cl = wMemberFactory.getMember(request);
		cl.setProvince(regist.getProvince());
		cl.setProvinceName(regist.getProvinceName());
		cl.setCity(regist.getCity());
		cl.setCityName(regist.getCityName());
		cl.setCounty(regist.getCounty());
		cl.setCountyName(regist.getCountyName());
		cl.setName(regist.getName());
		cl.setPhone(regist.getPhone());
		cl.setCarDealer(regist.getCarDealer());
		cl.setDealerAddress(regist.getDealerAddress());
		cl.setCertificate(regist.getCertificate());
		cl.setPersonAuditStatus(1);
		cl.setLng(regist.getLng());
		cl.setLat(regist.getLat());
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}
	@PostMapping("/merchantRegist")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "注册商家")
	public R<MemberDTO> merchantRegist(@Valid MerchantRegist regist) {
		Member cl = wMemberFactory.getMember(request);
		cl.setProvince(regist.getProvince());
		cl.setProvinceName(regist.getProvinceName());
		cl.setCity(regist.getCity());
		cl.setCityName(regist.getCityName());
		cl.setCounty(regist.getCounty());
		cl.setCountyName(regist.getCountyName());
		cl.setName(regist.getName());
		cl.setPhone(regist.getPhone());
		cl.setCarDealer(regist.getCarDealer());
		cl.setDealerAddress(regist.getDealerAddress());
		if(Func.isEmpty(cl.getCertificate())){
			cl.setCertificate(regist.getCompanyCertificate());
		}
		cl.setCompany(regist.getCompany());
		cl.setCorporate(regist.getCorporate());
		cl.setCompanyCertificate(regist.getCompanyCertificate());
		cl.setCompanyAuditStatus(1);
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}




	@GetMapping("/carCollectPage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "收藏车源", notes = "传入cars")
	public R<IPage<CarsVO>> carCollectPage(Query query) {
		Member cl = wMemberFactory.getMember(request);
		CarsVO cars = new CarsVO();
		cars.setMemberId(cl.getId());
		if (Func.isNotEmpty(cars.getSort())) {
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		} else {
			cars.setSort(CarSort.TIME.value);
		}
		cars.setMemberId(cl.getId());
		IPage<CarsVO> pages = carsService.carCollectPage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	@GetMapping("/carsBrowsePage")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "我浏览的车源记录", notes = "传入cars")
	public R<IPage<CarsVO>> carsBrowsePage(Query query) {
		Member cl = wMemberFactory.getMember(request);

		if (Func.isEmpty(cl)) {
			return R.fail("微信用户不存在");
		}
		CarsVO cars = new CarsVO();
		cars.setMemberId(cl.getId());




		IPage<CarsVO> pages = carsService.carsBrowsePage(Condition.getPage(query), cars);
		return R.data(pages);
	}

	@GetMapping("/delcarsBrowse")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "清空浏览记录")
	public R delcarsBrowse(){
		Member cl = wMemberFactory.getMember(request);
		CarsBrowse carsBrowse=new CarsBrowse();
		carsBrowse.setMemberId(cl.getId());
		Boolean res= carsBrowseService.remove(Condition.getQueryWrapper(carsBrowse));

		return R.status(res);
	}

	@GetMapping("/orderPage")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "支付记录", notes = "传入payOrder")
	public R<IPage<PayOrderVO>> page( Query query) {
		Member cl = wMemberFactory.getMember(request);
		PayOrderVO payOrder=new PayOrderVO();
		payOrder.setMemberId(cl.getId());
		IPage<PayOrderVO> pages = payOrderService.selectPayOrderPage(Condition.getPage(query), payOrder);
		return R.data(pages);
	}

	@GetMapping("/relationPhoen")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "联系我们电话号码", notes = "id=2")
	public R<DictVO> relationPhoen() {
		Dict detail = dictService.getById(2);
		return R.data(DictWrapper.build().entityVO(detail));
	}

}
