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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.HomePageAllCountDTO;
import org.springblade.car.dto.HomePageCountDTO;
import org.springblade.car.entity.Brand;
import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.service.*;
import org.springblade.car.vo.*;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 品牌表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/homePage")
@Api(value = "后台首页", tags = "v2后台首页-后台首页接口")
@ApiSort(2001)
public class HomePageController extends BladeController {

	private final UserAreaFactory userAreaFactory;
	private final IMemberService memberService;
	private final IShopService shopService;
	private final IForumService forumService;
	private final ICarsService carsService;


	@GetMapping("/queryCount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询统计")
	public R<HomePageCountDTO> queryCount(Date startDate, Date endDate ) {
		HomePageCountDTO homePageCountDTO=new HomePageCountDTO();
	/*	MemberVO member=userAreaFactory.getMemberVO();
		 List<String> areas=member.getAreas();
		 List<String> noareas=member.getNoareas();
		member.setStartCreateTime(startDate);
		member.setEndCreateTime(endDate);
		 Integer newRegister=memberService.selectMemberCount(member);
		homePageCountDTO.setNewRegister(newRegister);
		member.setRoletype(2);
		Integer newMember=memberService.selectMemberCount(member);

		homePageCountDTO.setNewMember(newMember);

		member.setRoletype(1);
		Integer newVisitor=memberService.selectMemberCount(member);

		homePageCountDTO.setNewVisitor(newVisitor);

		CarsVO carsVO=new CarsVO();
		carsVO.setAreas(areas);
		carsVO.setNoareas(noareas);
		carsVO.setStartCreateTime(startDate);
		carsVO.setEndCreateTime(endDate);
		Integer newCars=carsService.selectCarsCount(carsVO);

		homePageCountDTO.setNewCar(newCars);

		ShopVO shopVO=new ShopVO();
		shopVO.setAreas(areas);
		shopVO.setNoareas(noareas);
		shopVO.setStartCreateTime(startDate);
		shopVO.setEndCreateTime(endDate);
		Integer newShop=shopService.selectShopCount(shopVO);

		homePageCountDTO.setNewShop(newShop);

		ForumVO forumVO=new ForumVO();
		forumVO.setAreas(areas);
		forumVO.setNoareas(noareas);
		forumVO.setStartCreateTime(startDate);
		forumVO.setEndCreateTime(endDate);
		Integer newForum=forumService.selectForumCount(forumVO);

		homePageCountDTO.setNewForum(newForum);*/

		return R.data(homePageCountDTO);
	}

	@GetMapping("/queryAllCount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "全部统计")
	public R<HomePageAllCountDTO> queryAllCount() {
		HomePageAllCountDTO homePageCountDTO=new HomePageAllCountDTO();
		/*MemberVO member=userAreaFactory.getMemberVO();
		List<String> areas=member.getAreas();
		List<String> noareas=member.getNoareas();
		Integer allRegister=memberService.selectMemberCount(member);
		homePageCountDTO.setAllRegister(allRegister);
		member.setRoletype(2);
		Integer allMember=memberService.selectMemberCount(member);

		homePageCountDTO.setAllMember(allMember);

		member.setRoletype(1);
		Integer allVisitor=memberService.selectMemberCount(member);

		homePageCountDTO.setAllVisitor(allVisitor);

		CarsVO carsVO=new CarsVO();
		carsVO.setAreas(areas);
		carsVO.setNoareas(noareas);
		Integer allCars=carsService.selectCarsCount(carsVO);

		homePageCountDTO.setAllCar(allCars);

		ShopVO shopVO=new ShopVO();
		shopVO.setAreas(areas);
		shopVO.setNoareas(noareas);
		Integer allShop=shopService.selectShopCount(shopVO);

		homePageCountDTO.setAllShop(allShop);

		ForumVO forumVO=new ForumVO();
		forumVO.setAreas(areas);
		forumVO.setNoareas(noareas);
		Integer allForum=forumService.selectForumCount(forumVO);

		homePageCountDTO.setAllForum(allForum);*/

		return R.data(homePageCountDTO);
	}



	
}
