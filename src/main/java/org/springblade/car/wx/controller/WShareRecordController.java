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
import org.springblade.car.entity.Member;
import org.springblade.car.entity.ShareRecord;
import org.springblade.car.service.IShareRecordService;
import org.springblade.car.vo.ShareRecordVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.service.IDictService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-09-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/sharerecord")
@Api(value = "", tags = "微信-车服务分享")
public class WShareRecordController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IShareRecordService shareRecordService;
	private final IDictService dictService;



	/**
	 * 新增 
	 */
	@PostMapping("/saveSharerecord")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "小程序分享-保存", notes = "传入shareRecord")
	public R saveSharerecord(@Valid @RequestBody ShareRecord shareRecord) {
		Member cl= wMemberFactory.getMember(request);
		shareRecord.setMemberId(cl.getId());
		return R.status(shareRecordService.save(shareRecord));
	}

	@PostMapping("/sharerecordCount")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询当天小程序分享次数", notes = "传入shareRecord")
	public R<Integer> sharerecordCount() {
		Member cl= wMemberFactory.getMember(request);
		int count=shareRecordService.sharerecordCount(cl.getId());
		return R.data(count);
	}

	
}
