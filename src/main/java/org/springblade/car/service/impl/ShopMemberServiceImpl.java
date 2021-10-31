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

import org.springblade.car.dto.ShopMemberDTO;
import org.springblade.car.dto.ShopMemberRep;
import org.springblade.car.dto.ShopMemberReq;
import org.springblade.car.dto.ShopMemberRoleRightDTO;
import org.springblade.car.entity.ShopMember;
import org.springblade.car.entity.ShopMemberRoleRight;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.vo.ShopMemberVO;
import org.springblade.car.mapper.ShopMemberMapper;
import org.springblade.car.service.IShopMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 门店成员表 服务实现类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Service
public class ShopMemberServiceImpl extends ServiceImpl<ShopMemberMapper, ShopMember> implements IShopMemberService {

	@Override
	public IPage<ShopMemberDTO> selectShopMemberPage(IPage<ShopMemberDTO> page, ShopMemberReq shopMember) {
		return page.setRecords(baseMapper.selectShopMemberPage(page, shopMember));
	}
	@Override
	public List<ShopMemberRep> queryShopMemberPage(Map<String,Object> map) {
		return  baseMapper.queryShopMemberPage(map);
	}
	public List<ShopMemberRoleRight> selectShopMemberRoleRight(){
		return baseMapper.selectShopMemberRoleRight();
	}
	public ShopMemberRoleRightDTO getShopMemberRight(Long memberId){
		return baseMapper.getShopMemberRight(memberId);
	}
}
