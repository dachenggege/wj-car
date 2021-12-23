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
package org.springblade.car.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.car.Req.MemberReq;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.vo.MemberVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户表 Mapper 接口
 *
 * @author BladeX
 * @since 2021-10-13
 */
public interface MemberMapper extends BaseMapper<Member> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param member
	 * @return
	 */
	List<MemberVO> selectMemberPage(IPage page, @Param("member") MemberReq member);
	Integer selectMemberCount( @Param("member") MemberReq member);

	public Boolean delCars();
	public Boolean delCarsBrowse();
	public Boolean delCarsCollect();
	public Boolean delForun();
	public Boolean delForunComment();
	public Boolean delForunLike();
	public Boolean delMemberCertification();
	public Boolean delMemberfans();
	public Boolean delPhoneRecord();
	public Boolean delShop();
	public Boolean delShopMember();
	public Boolean delShopAllied();
}
