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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.PayOrder;
import org.springblade.car.enums.PayStatus;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IPayOrderService;
import org.springblade.car.wx.pay.WXXmlUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.Func;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 *  控制器
 *
 * @author bond
 * @since 2021-04-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/second-hand-car/wx/pay")
@Api(value = "微信-支付接口", tags = "微信-支付接口")
public class WxPayBaskController extends BladeController {
	private IPayOrderService PayOrderService;
	private IMemberService memberService;
	//状态1待支付，2支付成功，3支付失败
	public static final Integer PAY_STUTAS_1 =1;//1未支付
	public static final Integer PAY_STUTAS_2 =2;//2支付成功
	public static final Integer PAY_STUTAS_3 =3;//3支付失败

	@PostMapping("/notify")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "微信支付回调")
	@Transactional
	public String doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("--------------------微信回调！------------------");

		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));

		String line = null;

		StringBuilder sb = new StringBuilder();

		while((line = br.readLine())!=null){

			sb.append(line);

		}
		System.out.println("-----回调内容------");
		System.out.println(sb.toString());
		System.out.println("-----回调内容------");



		String strXml = null;
		try {
			strXml = getStrXml(request, response);// 获取微信回调信息
		} catch (ServletException e1) {
			e1.printStackTrace();
		}

		System.out.println("获取微信回调信息==="+strXml);

		Map<String, String> resultMap = WXXmlUtil.buildXmlToMap(strXml);
		String responseXml = null;
		if(resultMap ==null){
			return null;
		}
		String return_code = resultMap.get("return_code");
		String result_code = resultMap.get("result_code");
		String sign = resultMap.get("sign");// 签名
		String out_trade_no = resultMap.get("out_trade_no");/// 订单号

		System.out.println("订单号===============out_trade_no=="+out_trade_no);

		//查询订单 如果不存在则退出 不处理
		PayOrder order= PayOrderService.getById(Long.valueOf(out_trade_no));

		if(Func.isEmpty(order)){
			return null;
		}
		Integer payStatus=order.getStatus();//订单状态

		if(payStatus.equals(PAY_STUTAS_2)){//已支付成功 无需再处理
			responseXml = buildOKXml();
			try {
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				out.write(responseXml.getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}


		/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 1) {
			responseXml = echostr;
		} else {

			// 正常的微信处理流程
			// 说明微信的返回的用户支付的结果是成功的 并且说明业务处理宗成
			if (Func.equals("SUCCESS", return_code)
				&& Func.equals("SUCCESS", result_code)) {
				// 调接口更改DB的order支付的状态

				order.setStatus(PAY_STUTAS_2);
				boolean results=PayOrderService.updateById(order);
				if (results) {// 说明后台更改成功
					if(Func.equals(order.getType(),1)) {
						updateMemberRights(order);
					}
					responseXml = buildOKXml();
					System.out.println("--------------------微信回调！//支付成功------------------");
				} else {
					responseXml = buildFailXml();
				}

			} else {
				responseXml = buildFailXml();
				//支付失败
				order.setStatus(PAY_STUTAS_3);
				boolean results=PayOrderService.updateById(order);
			}

		}

		try {
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(responseXml.getBytes());
			out.flush();
			out.close();
		//	response.getWriter().write(responseXml);
		} catch (Exception e) {
			e.printStackTrace();
		}

	return null;
	}
	public String getStrXml(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		/** 读取接收到的xml消息 */
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
		return xml;
	}

	private String buildOKXml() {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<return_code><![CDATA[SUCCESS]]></return_code>");
		str.append("<return_msg><![CDATA[OK]]></return_msg>");
		str.append("</xml>");
		return str.toString();
	}
	public String buildFailXml() {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<return_code><![CDATA[FAIL]]></return_code>");
		str.append("<return_msg><![CDATA[FAIL]]></return_msg>");
		str.append("</xml>");
		return str.toString();
	}


	public boolean updateMemberRights(PayOrder order){
		Boolean res=false;
		Member cl = memberService.getById(order.getMemberId());
		cl.setMemberLv(order.getRightId());
		//cl.setAuditStatus(AuditStatus.AUDITING.id);
		res=memberService.updateById(cl);
		return res;
	}





//	//-------------------=
//	@PostMapping("/test")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "微信支付回调")
//	@Transactional
//	public String test(HttpServletRequest request,HttpServletResponse response) throws IOException {
//
//		String out_trade_no = "7365115539952756";/// 订单号
//
//		System.out.println("订单号===============out_trade_no=="+out_trade_no);
//
//		//查询订单 如果不存在则退出 不处理
//		PayFee payFee= new PayFee();
//		payFee.setOutTradeNo(out_trade_no);
//		List<PayFee> payFeeList= payFeeService.list(Condition.getQueryWrapper(payFee));
//
//		if(Func.isEmpty(payFeeList)){
//			return null;
//		}
//		Integer payStatus=PAY_STUTAS_1;
//		for(PayFee payFee1:payFeeList){
//			payStatus=payFee1.getStatus();
//			System.out.println("订单号================payFee1.getOut_trade_no()="+payFee1.getOutTradeNo());
//		}
//
//		updateMeterMoney(payFeeList);
//
//
//		return null;
//	}

}
