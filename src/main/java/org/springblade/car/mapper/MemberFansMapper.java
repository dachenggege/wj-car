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

import org.springblade.car.dto.MemberFansDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberFans;
import org.springblade.car.vo.MemberFansVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author BladeX
 * @since 2021-10-16
 */
public interface MemberFansMapper extends BaseMapper<MemberFans> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param memferFans
	 * @return
	 */
	List<Member> selectMemberFansPage(IPage page, MemberFans memferFans);
	List<Member> selectMemberFcousPage(IPage page, MemberFans memferFans);

}
