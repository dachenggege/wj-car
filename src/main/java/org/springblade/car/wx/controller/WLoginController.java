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
package org.springblade.car.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.CarsBrowse;
import org.springblade.car.entity.Member;
import org.springblade.car.enums.CarSort;
import org.springblade.car.enums.RoleType;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.PayOrderVO;
import org.springblade.car.wx.WeixinPhone.AESForWeixinGetPhoneNumber;
import org.springblade.car.wx.WeixinPhone.WeixinPhoneDecryptInfo;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WxFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.vo.DictVO;
import org.springblade.modules.system.wrapper.DictWrapper;
import org.springblade.util.NumberUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/login")
@Api(value = "微信-授权登入", tags = "v2微信-授权登入接口")
@ApiSort(1001)
public class WLoginController extends BladeController {
	private HttpServletRequest request;
	private final BladeRedis bladeRedis;
	private WMemberFactory wMemberFactory;

	private IMemberService memberService;
	private WxFactory wxFactory;
	private IMemberRightsService memberRightsService;
	/*
	会员注册逻辑：1、当前身份是游客（购买会员）判断逻辑roletype=1并且payStatus=0并且auditStatus=0。直接进入支付页面，支付成功后跳转到注册填写资料页面。完成提交，提交时把payStatus=3，audit_status=1。
			2、当前身份是游客（购买会员已经支付未填写注册资料）判断逻辑roletype=1并且payStatus=2并且auditStatus=0。直接进入注册填写资料页面。完成提交，提交时把payStatus=3，audit_status=1。
			3、当前身份是游客（购买会员已经支付但是审核不通过）判断逻辑roletype=1并且payStatus=2并且auditStatus=3。直接进入注册填写资料页面。完成提交，提交时把payStatus=3，audit_status=1。

*/


	@GetMapping("/code2Session")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "用户登录")
	public R<MemberDTO> bindingWx(@ApiParam(value = "code") @RequestParam(value = "code", required = true) String code) {
		Member cl = new Member();
		MemberDTO memberDTO = new MemberDTO();

		String res = wxFactory.code2Session(code);
		JSONObject jsonObject = JSONObject.parseObject(res);
		if (jsonObject.containsKey("errcode")) {
			return R.fail(res);
		}
		if (!jsonObject.containsKey("openid")) {
			return R.fail(res);
		}
			Member client = new Member();
			client.setOpenid(jsonObject.getString("openid"));
			cl = memberService.getOne(Condition.getQueryWrapper(client));

			if (Func.isEmpty(cl)) {
				//没有则注册
				cl = new Member();
				if (jsonObject.containsKey("session_key")) {
					cl.setSessionKey(jsonObject.getString("session_key"));
				}
				if (jsonObject.containsKey("unionid")) {
					cl.setUnionid(jsonObject.getString("unionid"));
					cl.setSessionKey(jsonObject.getString("session_key"));
				}
				cl.setOpenid(jsonObject.getString("openid"));
				cl.setId(NumberUtil.getRandomNumber(8, 8));
				cl.setRoletype(RoleType.VISITOR.id);//游客
				cl.setLastLogin(new Date());
				cl.setMemberLv(0);
				memberService.save(cl);
				bladeRedis.set(cl.getOpenid(),cl);
				MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
				return R.data(dto);
			}

			//如果已经注册了，就返回
			cl.setSessionKey(jsonObject.getString("session_key"));
			cl.setLastLogin(new Date());
			memberService.updateById(cl);
			bladeRedis.set(cl.getOpenid(),cl);
			MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
			return R.data(dto);

	}
	/**
	 * 修改
	 */
	@GetMapping("/updatePhone")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改用户手机")
	public R<MemberDTO> update(@ApiParam(value = "加密数据") @RequestParam(value = "encryptedData", required = true) String encryptedData,
							   @ApiParam(value = "sessionKey") @RequestParam(value = "sessionKey", required = true) String sessionKey,
							   @ApiParam(value = "vi") @RequestParam(value = "vi", required = true) String vi,
							   @ApiParam(value = "昵称") @RequestParam(value = "nickname", required = true) String nickname,
							   @ApiParam(value = "头像") @RequestParam(value = "headimgurl", required = true) String headimgurl
							   ) {
		Member cl = wMemberFactory.getMember(request);
		//解密电话号码
		AESForWeixinGetPhoneNumber aes = new AESForWeixinGetPhoneNumber(encryptedData,sessionKey,vi);
		WeixinPhoneDecryptInfo info = aes.decrypt();
		if (Func.isNotEmpty(info)) {
			cl.setPhone(info.getPurePhoneNumber());
		}
		cl.setNickname(nickname);
		cl.setHeadimgurl(headimgurl);
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}

	@GetMapping("/updateArae")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改用户所属区域")
	public R<MemberDTO> updateArea(
			@ApiParam(value = "省级编码") @RequestParam(value = "province", required = true) String province,
			@ApiParam(value = "省") @RequestParam(value = "provinceName", required = true) String provinceName,
			@ApiParam(value = "市编码") @RequestParam(value = "city", required = true) String city,
			@ApiParam(value = "市") @RequestParam(value = "cityName", required = true) String cityName,
			@ApiParam(value = "区县编码") @RequestParam(value = "county", required = true) String county,
			@ApiParam(value = "区县") @RequestParam(value = "countyName", required = true) String countyName) {
		Member cl = wMemberFactory.getMember(request);
		cl.setProvince(province);
		cl.setProvinceName(provinceName);
		cl.setCity(city);
		cl.setCityName(cityName);
		cl.setCounty(county);
		cl.setCountyName(countyName);
		memberService.updateById(cl);
		bladeRedis.set(cl.getOpenid(),cl);
		MemberDTO dto = wMemberFactory.getMemberByid(cl.getId());
		return R.data(dto);
	}
}
