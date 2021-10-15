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

import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.vo.MemberVO;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.entity.PayOrder;
import org.springblade.car.vo.PayOrderVO;
import org.springblade.car.service.IPayOrderService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 支付订单表 控制器
 *
 * @author BladeX
 * @since 2021-07-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/payorder")
@Api(value = "支付订单表", tags = "后台商机管理-订单管理")
public class PayOrderController extends BladeController {

	private final IPayOrderService payOrderService;
	private final UserAreaFactory userAreaFactory;

	/**
	 * 详情
	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "订单详情", notes = "传入payOrder")
//	public R<PayOrder> detail(PayOrder payOrder) {
//		PayOrder detail = payOrderService.getOne(Condition.getQueryWrapper(payOrder));
//		return R.data(detail);
//	}

//	/**
//	 * 分页 支付订单表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "列表", notes = "传入payOrder")
//	public R<IPage<PayOrder>> list(PayOrder payOrder, Query query) {
//		IPage<PayOrder> pages = payOrderService.page(Condition.getPage(query), Condition.getQueryWrapper(payOrder));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 支付订单表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "订单分页", notes = "传入payOrder")
	public R<IPage<PayOrderVO>> page(PayOrderVO payOrder, Query query) {
//		MemberVO member=userAreaFactory.getMemberVO();
//		payOrder.setAreas(member.getAreas());
//		payOrder.setNoareas(member.getNoareas());
		IPage<PayOrderVO> pages = payOrderService.selectPayOrderPage(Condition.getPage(query), payOrder);
		return R.data(pages);
	}
//
//	/**
//	 * 新增 支付订单表
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入payOrder")
//	public R save(@Valid @RequestBody PayOrder payOrder) {
//		return R.status(payOrderService.save(payOrder));
//	}
//
//	/**
//	 * 修改 支付订单表
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入payOrder")
//	public R update(@Valid @RequestBody PayOrder payOrder) {
//		return R.status(payOrderService.updateById(payOrder));
//	}
//
//	/**
//	 * 新增或修改 支付订单表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入payOrder")
//	public R submit(@Valid @RequestBody PayOrder payOrder) {
//		return R.status(payOrderService.saveOrUpdate(payOrder));
//	}

	
	/**
	 * 删除 支付订单表
	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value = "删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(payOrderService.removeByIds(Func.toLongList(ids)));
//	}

	
}
