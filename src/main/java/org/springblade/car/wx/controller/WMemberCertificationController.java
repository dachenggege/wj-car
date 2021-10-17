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
import org.springblade.car.entity.MemberCertification;
import org.springblade.car.service.IMemberCertificationService;
import org.springblade.car.vo.MemberCertificationVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-10-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/authentication")
@Api(value = "", tags = "v2微信-会员认证接口")
@ApiSort(1003)
public class WMemberCertificationController extends BladeController {

	private final IMemberCertificationService memberCertificationService;

	/**
	 * 详情
	 */
	@GetMapping("/authenticationDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "认证详情", notes = "传入authentication")
	public R<MemberCertification> detail(MemberCertification memberCertification) {
		MemberCertification detail = memberCertificationService.getOne(Condition.getQueryWrapper(memberCertification));
		return R.data(detail);
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入authentication")
	public R submit(@Valid @RequestBody MemberCertification memberCertification) {
		//高级认证需要支付完成相对应的支付
		if(Func.equals(memberCertification.getLevel(),2)){
			
		}
		return R.status(memberCertificationService.saveOrUpdate(memberCertification));
	}

}
