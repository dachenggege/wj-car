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
import org.springblade.car.dto.ShopCollectDTO;
import org.springblade.car.entity.ShopCollect;
import org.springblade.car.vo.ShopCollectVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.vo.ShopVO;

import java.util.List;

/**
 * 门店收藏表 Mapper 接口
 *
 * @author BladeX
 * @since 2021-08-26
 */
public interface ShopCollectMapper extends BaseMapper<ShopCollect> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param shopCollect
	 * @return
	 */
	List<ShopVO> selectShopCollectPage(IPage page, @Param("shopCollect") ShopCollectVO shopCollect);
	List<ShopCollectDTO> selectShopCollectAcceptPage(IPage page, @Param("shopCollect") ShopCollectDTO shopCollect);
	ShopCollect selectShopCollect(@Param("shopCollect") ShopCollect shopCollect);

}
