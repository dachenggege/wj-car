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
import org.springblade.car.dto.CallPhoneCarsDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.PhoneRecord;
import org.springblade.car.service.IPhoneRecordService;
import org.springblade.car.vo.PhoneRecordVO;
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
 * @since 2021-09-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/phonerecord")
@Api(value = "电话咨询记录", tags = "微信-电话咨询记录")
public class WPhoneRecordController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IPhoneRecordService phoneRecordService;

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增拨打电话记录", notes = "传入phoneRecord")
	public R save(@Valid @RequestBody PhoneRecord phoneRecord) {
		return R.status(phoneRecordService.save(phoneRecord));
	}


	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "电话咨询记录分页", notes = "传入phoneRecord")
	public R<IPage<CallPhoneCarsDTO>> page(CallPhoneCarsDTO cars, Query query) {
		Member cl = wMemberFactory.getMember(request);
		cars.setMemberId(cl.getId());
		IPage<CallPhoneCarsDTO> pages = phoneRecordService.selectCarsPhoneRecordPage(Condition.getPage(query), cars);
		return R.data(pages);
	}
}
