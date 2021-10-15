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
import org.springblade.car.entity.Forum;
import org.springblade.car.vo.ForumVO;
import org.springblade.car.service.IForumService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/forum")
@Api(value = "车论坛", tags = "后台车论坛管理-车论坛")
public class ForumController extends BladeController {

	private final IForumService forumService;
	private final UserAreaFactory userAreaFactory;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "帖子详情", notes = "传入forum")
	public R<Forum> detail(Forum forum) {
		Forum detail = forumService.getOne(Condition.getQueryWrapper(forum));
		return R.data(detail);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "帖子分页", notes = "传入forum")
	public R<IPage<ForumVO>> page(ForumVO forum, Query query) {
//		MemberVO member=userAreaFactory.getMemberVO();
//		forum.setAreas(member.getAreas());
//		forum.setNoareas(member.getNoareas());
		IPage<ForumVO> pages = forumService.selectForumPage(Condition.getPage(query), forum);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增帖子", notes = "传入forum")
	public R save(@Valid @RequestBody Forum forum) {
		return R.status(forumService.save(forum));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改帖子", notes = "传入forum")
	public R update(@Valid @RequestBody Forum forum) {
		return R.status(forumService.updateById(forum));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除帖子", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(forumService.removeByIds(Func.toLongList(ids)));
	}

	
}
