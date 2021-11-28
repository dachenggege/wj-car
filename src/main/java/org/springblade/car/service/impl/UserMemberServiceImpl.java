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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.car.entity.Ad;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.UserMember;
import org.springblade.car.mapper.AdMapper;
import org.springblade.car.mapper.UserMemberMapper;
import org.springblade.car.service.IAdService;
import org.springblade.car.service.IUserMemberService;
import org.springblade.car.vo.AdVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author BladeX
 * @since 2021-07-20
 */
@Service
public class UserMemberServiceImpl extends ServiceImpl<UserMemberMapper, UserMember> implements IUserMemberService {

	@Override
	public IPage<UserMember> selectUserMemberPage(IPage<UserMember> page, UserMember userMember) {
		return page.setRecords(baseMapper.selectUserMemberPage(page, userMember));
	}
	public List<Member> selectMemberList(Long userId){
		return  baseMapper.selectMemberList(userId);
	}
	public Boolean delUserMembrs(Long userId){
		return baseMapper.delUserMembrs(userId);
	}

}
