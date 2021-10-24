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

import org.springblade.car.dto.ShopAlliedDTO;
import org.springblade.car.entity.ShopAllied;
import org.springblade.car.vo.ShopAlliedVO;
import org.springblade.car.mapper.ShopAlliedMapper;
import org.springblade.car.service.IShopAlliedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.car.vo.ShopVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 门店收藏表 服务实现类
 *
 * @author BladeX
 * @since 2021-08-26
 */
@Service
public class ShopAlliedServiceImpl extends ServiceImpl<ShopAlliedMapper, ShopAllied> implements IShopAlliedService {

	public IPage<ShopAlliedDTO> hadAlliedShopPage(IPage<ShopAlliedDTO> page, ShopAlliedDTO shopCollect) {
		return page.setRecords(baseMapper.hadAlliedShopPage(page, shopCollect));
	}
	public IPage<ShopAlliedDTO> applyAlliedShopPage(IPage<ShopAlliedDTO> page, ShopAlliedDTO shopCollect) {
		return page.setRecords(baseMapper.applyAlliedShopPage(page, shopCollect));
	}
	public IPage<ShopAlliedDTO> selectShopAlliedPage(IPage<ShopAlliedDTO> page, ShopAlliedDTO shopCollect) {
		return page.setRecords(baseMapper.selectShopAlliedPage(page, shopCollect));
	}

}
