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
import org.springblade.car.entity.Forum;
import org.springblade.car.entity.ForumComment;
import org.springblade.car.entity.ForumLike;
import org.springblade.car.entity.Member;
import org.springblade.car.service.IForumCommentService;
import org.springblade.car.service.IForumLikeService;
import org.springblade.car.service.IForumService;
import org.springblade.car.vo.ForumVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.NumberUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/forum")
@Api(value = "微信-车论坛", tags = "v2微信-车论坛接口")
public class WForumController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IForumService forumService;
	private final IForumLikeService forumLikeService;
	private final IForumCommentService forumCommentService;
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
		Member cl = wMemberFactory.getMember(request);
			forum.setLikeMember(cl.getId());
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

		Member cl = wMemberFactory.getMember(request);
		forum.setMemberId(cl.getId());
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
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除帖子", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(forumService.removeByIds(Func.toLongList(ids)));
	}



	/**
	 * 点赞
	 */
	@PostMapping("/like")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "点赞", notes = "传入forumLike")
	public R like(@Valid @RequestBody ForumLike forumLike) {
		Member cl = wMemberFactory.getMember(request);
		forumLike.setMemberId(cl.getId());
		ForumLike entity=forumLikeService.selectForumLike(forumLike);
		Boolean res=false;
		if(Func.isEmpty(entity)){
			res=forumLikeService.save(forumLike);
		}else {
			entity.setIsLike(forumLike.getIsLike());
			res=forumLikeService.updateById(entity);
		}

		return R.status(res);
	}


	/**
	 * 评论
	 */
	@PostMapping("/comment")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "评论", notes = "传入forumComment")
	public R<ForumComment> comment(@Valid @RequestBody ForumComment forumComment) {
		Member cl = wMemberFactory.getMember(request);
		Long id= NumberUtil.getRandomNumber(1,12);
		forumComment.setId(id);
		forumComment.setMumberId(cl.getId());
		forumCommentService.save(forumComment);
		return R.data(forumComment);
	}

	/**
	 * 删除 评论
	 */
	@PostMapping("/delComment")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除评论", notes = "传入ids")
	public R delComment(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(forumCommentService.removeByIds(Func.toLongList(ids)));
	}

}
