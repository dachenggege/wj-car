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
import org.springblade.car.dto.ShopMemberDTO;
import org.springblade.car.dto.ShopMemberReq;
import org.springblade.car.entity.ShopMember;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.vo.ShopMemberVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 门店成员表 Mapper 接口
 *
 * @author BladeX
 * @since 2021-08-26
 */
public interface ShopMemberMapper extends BaseMapper<ShopMember> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param member
	 * @return
	 */
	List<ShopMemberDTO> selectShopMemberPage(IPage page, @Param("member") ShopMemberReq member);

}
