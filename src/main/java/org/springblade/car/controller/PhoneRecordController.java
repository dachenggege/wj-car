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
import org.springblade.car.entity.PhoneRecord;
import org.springblade.car.vo.PhoneRecordVO;
import org.springblade.car.service.IPhoneRecordService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-09-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/phonerecord")
@Api(value = "电话咨询记录", tags = "后台商机管理-电话记录")
public class PhoneRecordController extends BladeController {
	private final UserAreaFactory userAreaFactory;

	private final IPhoneRecordService phoneRecordService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入phoneRecord")
//	public R<PhoneRecord> detail(PhoneRecord phoneRecord) {
//		PhoneRecord detail = phoneRecordService.getOne(Condition.getQueryWrapper(phoneRecord));
//		return R.data(detail);
//	}

//	/**
//	 * 分页
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入phoneRecord")
//	public R<IPage<PhoneRecord>> list(PhoneRecord phoneRecord, Query query) {
//		IPage<PhoneRecord> pages = phoneRecordService.page(Condition.getPage(query), Condition.getQueryWrapper(phoneRecord));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入phoneRecord")
	public R<IPage<PhoneRecordVO>> page(PhoneRecordVO phoneRecord, Query query) {
//		MemberVO member=userAreaFactory.getMemberVO();
////		phoneRecord.setAreas(member.getAreas());
////		phoneRecord.setNoareas(member.getNoareas());
		IPage<PhoneRecordVO> pages = phoneRecordService.selectPhoneRecordPage(Condition.getPage(query), phoneRecord);
		if(Func.isNotEmpty(pages.getRecords())) {
			for (PhoneRecordVO vo:pages.getRecords()) {
				if(Func.equals(2,vo.getType())){

				}
			}
		}

		return R.data(pages);
	}

//	/**
//	 * 新增
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入phoneRecord")
//	public R save(@Valid @RequestBody PhoneRecord phoneRecord) {
//		return R.status(phoneRecordService.save(phoneRecord));
//	}
//
//	/**
//	 * 修改
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入phoneRecord")
//	public R update(@Valid @RequestBody PhoneRecord phoneRecord) {
//		return R.status(phoneRecordService.updateById(phoneRecord));
//	}
//
//	/**
//	 * 新增或修改
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入phoneRecord")
//	public R submit(@Valid @RequestBody PhoneRecord phoneRecord) {
//		return R.status(phoneRecordService.saveOrUpdate(phoneRecord));
//	}
//
//
//	/**
//	 * 删除
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value = "删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(phoneRecordService.removeByIds(Func.toLongList(ids)));
//	}
//
//
}
