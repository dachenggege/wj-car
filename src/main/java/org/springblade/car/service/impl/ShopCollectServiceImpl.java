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
import org.springblade.car.dto.ShopCollectDTO;
import org.springblade.car.entity.ShopCollect;
import org.springblade.car.vo.ShopCollectVO;
import org.springblade.car.mapper.ShopCollectMapper;
import org.springblade.car.service.IShopCollectService;
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
public class ShopCollectServiceImpl extends ServiceImpl<ShopCollectMapper, ShopCollect> implements IShopCollectService {

	@Override
	public IPage<ShopVO> selectShopCollectPage(IPage<ShopVO> page, ShopCollectVO shopCollect) {
		return page.setRecords(baseMapper.selectShopCollectPage(page, shopCollect));
	}
	public IPage<ShopCollectDTO> selectShopCollectAcceptPage(IPage<ShopCollectDTO> page, ShopCollectDTO shopCollect) {
		return page.setRecords(baseMapper.selectShopCollectAcceptPage(page, shopCollect));
	}
	public ShopCollect selectShopCollect(ShopCollect shopCollect){
		return baseMapper.selectShopCollect(shopCollect);
	}

}
