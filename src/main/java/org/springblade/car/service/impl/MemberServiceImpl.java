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
import org.springblade.car.Req.MemberReq;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.mapper.MemberMapper;
import org.springblade.car.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 用户表 服务实现类
 *
 * @author BladeX
 * @since 2021-10-13
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

	@Override
	public IPage<MemberVO> selectMemberPage(IPage<MemberVO> page, MemberReq member) {
		return page.setRecords(baseMapper.selectMemberPage(page, member));
	}
	public List<MemberVO> selectMemberList(MemberReq member){
		return baseMapper.selectMemberPage(null, member);
	}
	public 	Integer selectMemberCount(MemberReq member){
		return baseMapper.selectMemberCount(member);
	}

	public Boolean delCars(){
		return baseMapper.delCars();
	}
	public Boolean delCarsBrowse()
	{
		return baseMapper.delCarsBrowse();
	}
	public Boolean delCarsCollect(){
		return baseMapper.delCarsCollect();
	}
	public Boolean delForun(){
		return baseMapper.delForun();
	}
	public Boolean delForunComment(){
		return baseMapper.delForunComment();
	}
	public Boolean delForunLike(){
		return baseMapper.delForunLike();
	}
	public Boolean delMemberCertification(){
		return baseMapper.delMemberCertification();
	}
	public Boolean delMemberfans(){
		return baseMapper.delMemberfans();
	}
	public Boolean delPhoneRecord(){
		return baseMapper.delPhoneRecord();
	}
	public Boolean delShop(){
		return baseMapper.delShop();
	}
	public Boolean delShopMember(){
		return baseMapper.delShopMember();
	}
	public Boolean delShopAllied(){
		return baseMapper.delShopAllied();
	}

}
