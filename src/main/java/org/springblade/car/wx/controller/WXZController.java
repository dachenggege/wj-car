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
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.springblade.car.entity.Member;
import org.springblade.car.enums.RoleType;
import org.springblade.car.service.IMemberService;
import org.springblade.car.wx.dto.Wxz;
import org.springblade.car.wx.factory.WxFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Region;
import org.springblade.modules.system.service.IRegionService;
import org.springblade.modules.system.vo.RegionVO;
import org.springblade.util.NumberUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/xz")
//@Api(value = "微信-用户", tags = "微信-XZ")
public class WXZController extends BladeController {

	private final IRegionService regionService;


	/**
	 * 修改 
	 */
	@PostMapping("/add")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "添加", notes = "传入xzs")
	public R update(@Valid @RequestBody List<Wxz> xzs) {
		List<Region> regionList =new ArrayList<>();
		for(Wxz xz:xzs){
			Region region=new Region();
			region.setCode(xz.getId());
			region.setName(xz.getFullname());
			regionList.add(region);
		}
		return R.status(regionService.saveOrUpdateBatch(regionList));
	}

}
