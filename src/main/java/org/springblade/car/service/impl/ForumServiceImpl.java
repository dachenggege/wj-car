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
package org.springblade.car.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springblade.car.entity.Forum;
import org.springblade.car.entity.ForumComment;
import org.springblade.car.service.IForumCommentService;
import org.springblade.car.vo.ForumCommentVO;
import org.springblade.car.vo.ForumVO;
import org.springblade.car.mapper.ForumMapper;
import org.springblade.car.service.IForumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 *  服务实现类
 *
 * @author BladeX
 * @since 2021-07-28
 */
@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements IForumService {

	@Autowired
	private IForumCommentService forumCommentService;

	@Override
	public IPage<ForumVO> selectForumPage(IPage<ForumVO> page, ForumVO forum) {

		List<ForumVO> list= baseMapper.selectForumPage(page, forum);
		Map<String,Object> map=new HashMap<>();
		for(ForumVO vo:list){
			map.put("forum_id",vo.getId());
			List<ForumCommentVO> list1=forumCommentService.selectForumCommentByMap(map);
			vo.setForumCommentList(list1);
		}
		return page.setRecords(list);
	}
	public Integer selectForumCount(ForumVO forum){
		return baseMapper.selectForumCount(forum);
	}


}
