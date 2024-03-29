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

import org.springblade.car.entity.MemberRights;
import org.springblade.car.vo.MemberRightsVO;
import org.springblade.car.mapper.MemberRightsMapper;
import org.springblade.car.service.IMemberRightsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 会员体系权益表 服务实现类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Service
public class MemberRightsServiceImpl extends ServiceImpl<MemberRightsMapper, MemberRights> implements IMemberRightsService {

	@Override
	public IPage<MemberRightsVO> selectMemberRightsPage(IPage<MemberRightsVO> page, MemberRightsVO memberRights) {
		return page.setRecords(baseMapper.selectMemberRightsPage(page, memberRights));
	}
	public List<MemberRightsVO> selectMemberRightsList(MemberRightsVO memberRights) {
		return baseMapper.selectMemberRightsPage(memberRights);
	}
}
