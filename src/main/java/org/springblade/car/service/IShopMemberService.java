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
package org.springblade.car.service;

import org.springblade.car.dto.ShopMemberDTO;
import org.springblade.car.dto.ShopMemberRep;
import org.springblade.car.dto.ShopMemberReq;
import org.springblade.car.dto.ShopMemberRoleRightDTO;
import org.springblade.car.entity.ShopMember;
import org.springblade.car.entity.ShopMemberRoleRight;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.vo.ShopMemberVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.vo.ShopVO;

import java.util.List;
import java.util.Map;

/**
 * 门店成员表 服务类
 *
 * @author BladeX
 * @since 2021-08-26
 */
public interface IShopMemberService extends IService<ShopMember> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param shopMember
	 * @return
	 */
	IPage<ShopMemberDTO> selectShopMemberPage(IPage<ShopMemberDTO> page, ShopMemberReq shopMember);
	List<ShopMemberRep> queryShopMemberPage(Map<String,Object> map);
	List<ShopMemberRoleRight> selectShopMemberRoleRight();
	ShopMemberRoleRightDTO getShopMemberRight(Map<String,Object> map);

	Integer selectMyJoinShopCount(ShopMember shopMember);
	Boolean delByShopId(Long shopId);

}
