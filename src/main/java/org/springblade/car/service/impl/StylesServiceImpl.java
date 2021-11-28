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

import org.springblade.car.entity.Styles;
import org.springblade.car.vo.StylesVO;
import org.springblade.car.mapper.StylesMapper;
import org.springblade.car.service.IStylesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 车系表 服务实现类
 *
 * @author BladeX
 * @since 2021-07-24
 */
@Service
public class StylesServiceImpl extends ServiceImpl<StylesMapper, Styles> implements IStylesService {

	@Override
	public IPage<StylesVO> selectStylesPage(IPage<StylesVO> page, StylesVO styles) {
		return page.setRecords(baseMapper.selectStylesPage(page, styles));
	}
	//vin查询匹配车品牌 车系 车型
	 public List<Styles> selectStylesVin(Map<String,Object> queryMap){
		return baseMapper.selectStylesVin(queryMap);
	 }
	 public List<String> queryStylesKeyWordsList(){
		return baseMapper.queryStylesKeyWordsList();
	 }
}
