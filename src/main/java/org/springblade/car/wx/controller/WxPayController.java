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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.CommunityWx;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberRights;
import org.springblade.car.entity.PayOrder;
import org.springblade.car.service.ICommunityWxService;
import org.springblade.car.service.IMemberRightsService;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IPayOrderService;
import org.springblade.car.vo.MemberRightsVO;
import org.springblade.car.wx.dto.MemberRightsPayRep;
import org.springblade.car.wx.dto.OrderPayReq;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.pay.WXHttpConntionUtil;
import org.springblade.car.wx.pay.WXNonceStrUtil;
import org.springblade.car.wx.pay.WXOrderPayVo;
import org.springblade.car.wx.pay.WXXmlUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.service.IDictService;
import org.springblade.util.DateUtil;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  控制器
 *
 * @author bond
 * @since 2021-04-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/second-hand-car/wx/pay")
@Api(value = "微信-支付接口", tags = "v2微信-支付接口")
@ApiSort(1005)
public class WxPayController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private IPayOrderService PayOrderService;
	private ICommunityWxService iCommunityWxService;
	private IMemberService memberService;
	private final IDictService dictService;
	private final IMemberRightsService memberRightsService;

	@GetMapping("/memberMoney")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "会员年费费用")
	public R<List<MemberRightsPayRep>> memberMoney() {
		MemberDTO cl = wMemberFactory.getMember(request);

		Integer memberLv=cl.getMemberLv();
		Integer roteType=cl.getRoletype();
		if(Func.equals(roteType,1)){
			return R.fail("请先注册会员，才能充值哦");
		}

		MemberRightsVO memberRights=new MemberRightsVO();
		memberRights.setRoletype(roteType);
		memberRights.setStatus(1);
		List<MemberRightsVO> list=memberRightsService.selectMemberRightsList(memberRights);
		List<MemberRightsPayRep> payReqs=new ArrayList<>();
		for(MemberRights rights:list){
			MemberRightsPayRep payReq=new MemberRightsPayRep();
			payReq.setRightsId(rights.getId());
			payReq.setRoleType(roteType);
			payReq.setMemberLv(rights.getLevel());
			payReq.setLevelName(rights.getLevelName());
			payReq.setPayMoney(rights.getPrice());
			payReq.setType(1);//("升级");

			//个人会员，银钻，金钻
			if(Func.equals(rights.getRoletype(),2) && Func.equals(roteType,2) ){
				if(Func.equals(memberLv,rights.getLevel())){
					payReq.setType(2);//("续费");
				}
				payReqs.add(payReq);
			}
			//商家
			if(Func.equals(rights.getRoletype(),3) && Func.equals(roteType,3) ){
				if(Func.equals(memberLv,rights.getLevel())){
					payReq.setType(2);//("续费");
				}
				payReqs.add(payReq);
			}
		}

		return R.data(payReqs);
	}


	@PostMapping("/order")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "会员充值下单支付", notes = "传入OrderPayReq")
	@Transactional
	@Synchronized
	public R<Map<String, String>> payment(@Valid @RequestBody OrderPayReq orderPayReq) {

		String openid =request.getHeader("openid");
		if(Func.isEmpty(openid)){
			return  R.fail("openid不能为空");
		}
		MemberDTO cl = wMemberFactory.getMember(request);

		MemberRights rights=memberRightsService.getById(orderPayReq.getRightsId());

		if(Func.isEmpty(cl)){
			return  R.fail("用户不存在");
		}
		if(Func.isEmpty(rights)){
			return  R.fail("用户权益id不存在");
		}
		if(Func.equals(cl.getRoletype(),1)){
			return  R.fail("您现在是游客身份，请先注册会员或商家哦");
		}


		orderPayReq.setOpenid(openid);
		orderPayReq.setMemberId(cl.getId());
		if(Func.isEmpty(openid)){
			return  R.fail("微信用户openid不能为空");
		}
		if(orderPayReq.getPayMoney()<=0){
			return  R.fail("支付金额不能小于等于0");
		}

		if(Func.isEmpty(orderPayReq.getRightsId())){
			return  R.fail("会员权益id不能为空");
		}

		CommunityWx Wx=iCommunityWxService.getById(1);
		if(Func.isEmpty(Wx)){
			return R.fail("微信商户，小程序信息没有配置，请联系技术人员");
		}
		if(Func.isEmpty(Wx.getAppid()) || Func.isEmpty(Wx.getAppSecret()) || Func.isEmpty(Wx.getMchId())
			|| Func.isEmpty(Wx.getNotifyUrl()) || Func.isEmpty(Wx.getSignKey()) || Func.isEmpty(Wx.getSpbillCreateIp())){
			return R.fail("微信商户，小程序信息没有配置，请联系技术人员");
		}

		PayOrder pay=new PayOrder();
		BeanUtils.copyProperties(orderPayReq,pay);

		Long id= NumberUtil.getRandomNum(16);
		pay.setId(id);
		pay.setOutTradeNo(id.toString());
		pay.setStatus(1);
		pay.setRemark(rights.getLevelName());
		Map<String, String> resMap=WXPay(id.toString(),pay.getPayMoney(),openid,Wx);

		String return_code = resMap.get("return_code");
		String result_code = resMap.get("result_code");

		if (Func.equals("FAIL", return_code)){
			return  R.data(resMap);
		}

		if (Func.equals("FAIL", result_code)){
			return  R.data(resMap);
		}

		//统一下单成功，插入订单表
		PayOrderService.save(pay);
		return R.data(resMap);
	}

	@PostMapping("/carServiceMomeyOrder")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "车服务查询下单现金支付", notes = "传入OrderPayReq")
	@Transactional
	@Synchronized
	public R<Map<String, String>> carServiceMomeyOrder(@Valid @RequestBody OrderPayReq orderPayReq) {
		String openid =request.getHeader("openid");
		if(Func.isEmpty(openid)){
			return  R.fail("openid不能为空");
		}
		Member cl = wMemberFactory.getMember(request);
		orderPayReq.setOpenid(openid);
		orderPayReq.setMemberId(cl.getId());
		if(Func.isEmpty(openid)){
			return  R.fail("微信用户openid不能为空");
		}
		if(orderPayReq.getPayMoney()<=0){
			return  R.fail("支付金额不能小于等于0");
		}

		CommunityWx Wx=iCommunityWxService.getById(1);
		if(Func.isEmpty(Wx)){
			return R.fail("微信商户，小程序信息没有配置，请联系技术人员");
		}
		if(Func.isEmpty(Wx.getAppid()) || Func.isEmpty(Wx.getAppSecret()) || Func.isEmpty(Wx.getMchId())
				|| Func.isEmpty(Wx.getNotifyUrl()) || Func.isEmpty(Wx.getSignKey()) || Func.isEmpty(Wx.getSpbillCreateIp())){
			return R.fail("微信商户，小程序信息没有配置，请联系技术人员");
		}

		PayOrder pay=new PayOrder();
		BeanUtils.copyProperties(orderPayReq,pay);
		Long id= NumberUtil.getRandomNum(16);
		pay.setId(id);
		pay.setOutTradeNo(id.toString());
		pay.setStatus(1);

		Map<String, String> resMap=WXPay(id.toString(),pay.getPayMoney(),openid,Wx);

		String return_code = resMap.get("return_code");
		String result_code = resMap.get("result_code");

		if (Func.equals("FAIL", return_code)){
			return  R.data(resMap);
		}

		if (Func.equals("FAIL", result_code)){
			return  R.data(resMap);
		}

		//统一下单成功，插入订单表
		PayOrderService.save(pay);
		return R.data(resMap);
	}


	@PostMapping("/carServiceShareOrder")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "车服务查询下单分享支付", notes = "传入OrderPayReq")
	@Transactional
	@Synchronized
	public R carServiceOrder(@Valid @RequestBody OrderPayReq orderPayReq) {
		String openid =request.getHeader("openid");
		if(Func.isEmpty(openid)){
			return  R.fail("openid不能为空");
		}
		Member cl = wMemberFactory.getMember(request);
		orderPayReq.setOpenid(openid);
		orderPayReq.setMemberId(cl.getId());
		if(Func.isEmpty(openid)){
			return  R.fail("微信用户openid不能为空");
		}


		PayOrder pay=new PayOrder();
		BeanUtils.copyProperties(orderPayReq,pay);
		Long id= NumberUtil.getRandomNum(16);
		pay.setId(id);
		pay.setOutTradeNo(id.toString());
		pay.setStatus(2);
		pay.setPayMoney(0d);

		 Boolean r =PayOrderService.save(pay);

		return R.data(r);
	}


	public Map<String, String> WXPay(String out_trade_no, Double price, String openid, CommunityWx Wx){

		int total_fee = (int) (price * 100);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time_start = dateFormat.format(new Date());
		String time_expire = "";

		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt;
		try {
			dt = sd.parse(time_start);

			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.HOUR, 2);
			Date dt1 = rightNow.getTime();
			time_expire = sd.format(dt1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String trade_type = "JSAPI";// 网页扫码支付(小程序支付)

		// 保存订单信息
		WXOrderPayVo wx = new WXOrderPayVo();
		wx.setOut_trade_no(out_trade_no);
		wx.setProduct_id("zhcb0000001");
		wx.setBody("万家车源");
		wx.setTime_start(time_start);
		wx.setTime_expire(time_expire);
		wx.setPay_status("0");
		wx.setTotal_fee(total_fee);
		wx.setTrade_type(trade_type);
		wx.setOpenid(openid);

		Map<String, String> map = new HashMap<String, String>();
		map.put("return_code","FAIL");

		Map<String, String> responseMap=new HashMap<String, String>();
		responseMap = WXHttpConntionUtil.sendGZH(wx,Wx);
		if (responseMap != null) {
			String return_code = responseMap.get("return_code");
			String result_code = responseMap.get("result_code");

			map.put("return_code",return_code);
			map.put("result_code",result_code);

			if (Func.equals("SUCCESS", return_code) && Func.equals("SUCCESS", result_code)) {// 说明调微信成功
				String appid = responseMap.get("appid");
				String nonceStr = WXNonceStrUtil.getRandomStringByLength();
				String timeStamp = DateUtil.timeStamp();
				StringBuffer MD5str = new StringBuffer();
				MD5str.append("appId=").append(appid);
				MD5str.append("&nonceStr=").append(nonceStr);
				MD5str.append("&package=").append("prepay_id=" + responseMap.get("prepay_id"));
				MD5str.append("&signType=").append("MD5");
				MD5str.append("&timeStamp=").append(timeStamp);

				String sign_key = Wx.getSignKey();
				MD5str.append("&key=").append(sign_key);
				String paySign = WXXmlUtil.bulidMD5Str(MD5str.toString());

				map.put("appId", appid);
				map.put("timeStamp", timeStamp);
				map.put("nonceStr", nonceStr);
				map.put("prepay_id", responseMap.get("prepay_id"));
				map.put("signType", "MD5");
				map.put("paySign", paySign);
			}
			else{
				return responseMap;
			}
		}
		return map;
	}

}
