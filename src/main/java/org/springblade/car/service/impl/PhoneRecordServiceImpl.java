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
import org.springblade.car.dto.CallPhoneCarsDTO;
import org.springblade.car.entity.PhoneRecord;
import org.springblade.car.vo.PhoneRecordVO;
import org.springblade.car.mapper.PhoneRecordMapper;
import org.springblade.car.service.IPhoneRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author BladeX
 * @since 2021-09-12
 */
@Service
public class PhoneRecordServiceImpl extends ServiceImpl<PhoneRecordMapper, PhoneRecord> implements IPhoneRecordService {

	@Override
	public IPage<PhoneRecordVO> selectPhoneRecordPage(IPage<PhoneRecordVO> page, PhoneRecordVO phoneRecord) {
		return page.setRecords(baseMapper.selectPhoneRecordPage(page, phoneRecord));
	}

	@Override
	public IPage<CallPhoneCarsDTO> selectCarsPhoneRecordPage(IPage<CallPhoneCarsDTO> page, CallPhoneCarsDTO cars) {
		return page.setRecords(baseMapper.selectCarsPhoneRecordPage(page, cars));
	}

}
