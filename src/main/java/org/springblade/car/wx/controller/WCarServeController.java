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

import com.alibaba.excel.util.FileUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.qcloud.cos.utils.Md5Utils;
import com.sun.mail.imap.Rights;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralEfficientOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralEfficientOCRResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.EmptyNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springblade.car.dto.CarsVinParseReq;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.dto.VinParseData;
import org.springblade.car.dto.VinVehicle;
import org.springblade.car.entity.*;
import org.springblade.car.service.*;
import org.springblade.car.vo.PayOrderVO;
import org.springblade.car.vo.VinParseVO;
import org.springblade.car.wx.constants.JuheConstant;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WVinServeFactory;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.resource.builder.oss.OssBuilder;
import org.springblade.modules.resource.utils.MyImgUtil;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.vo.DictVO;
import org.springblade.util.BigDecimalUtil;
import org.springblade.util.JsonUtils;
import org.springblade.util.NumberUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/carServe")
@ApiSort(1010)
@Api(value = "微信-车服务", tags = "v2微信-车服务接口")
public class WCarServeController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private WVinServeFactory wVinServeFactory;
	private final IShareRecordService shareRecordService;
	private final IDictService dictService;
	private IPayOrderService payOrderService;

	@PostMapping("/qcrVinQuery")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "vin图片识别车辆信息", notes = "imageBase64")
	public R<VinVehicle> qcrVinQuery(@ApiParam(value = "imageBase64") @RequestBody String imageBase64) {
		VinVehicle vehicle= new VinVehicle();
		Member cl= wMemberFactory.getMember(request);
		String vin=wVinServeFactory.gjqcrVinQuery(imageBase64);
		System.out.println("车服务vin图片识别车辆信息vin="+vin);

		if(Func.isEmpty(vin)){
			return R.fail(201,vin);
		}

		VinParseData data=wVinServeFactory.vinParse(vin);
		vehicle=data.getVinVehicle();
		vehicle.setPvin(vin);
			if(Func.isNotEmpty(vehicle)) {
				PayOrder order = new PayOrder();
				order.setMemberId(cl.getId());
				order.setVin(vin);
				order.setType(3);
				order.setStatus(2);
				PayOrder payOrder = payOrderService.getOne(Condition.getQueryWrapper(order));
				if (Func.isNotEmpty(payOrder)) {
					vehicle.setOutTradeNo(payOrder.getOutTradeNo());
				}
			}

		return R.data(vehicle);
	}

	//@ApiLog("车辆VIN查询车辆信息")
	@GetMapping("/vinParse")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "车辆VIN查询车辆信息", notes = "传入vin号")
	public R<VinVehicle> vinParse(@ApiParam(value = "车架号") @RequestParam(value = "vin", required = true)String vin) {
		Member cl= wMemberFactory.getMember(request);
		wVinServeFactory.isCheckVin(vin);
		VinParseData data=wVinServeFactory.vinParse(vin);
		VinVehicle vehicle=data.getVinVehicle();
		if(Func.isNotEmpty(vehicle)) {
			PayOrder order = new PayOrder();
			order.setMemberId(cl.getId());
			order.setVin(vin);
			order.setType(3);
			order.setStatus(2);
			PayOrder payOrder = payOrderService.getOne(Condition.getQueryWrapper(order));
			if (Func.isNotEmpty(payOrder)) {
				vehicle.setOutTradeNo(payOrder.getOutTradeNo());
			}
		}
		return R.data(vehicle);

	}
	//@ApiLog("查询服务参数")
	@GetMapping("/carServiceParm")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询服务参数")
	public R<Map<String,Object>> carServiceParm() {
		MemberDTO cl= wMemberFactory.getMemberDTO(request);
		MemberRights right= cl.getRights();
		Map<String,Object> reMap=new HashMap<>();
		int hadShareCount=shareRecordService.sharerecordCount(cl.getId());
		int freeOrderCount=payOrderService.freeOrderCount(cl.getId());

		Long id= NumberUtil.getRandomNum(16);
		int remainFreeNum=0;
		if(right.getFreeVinParseNum()>freeOrderCount){
			remainFreeNum=right.getFreeVinParseNum()-freeOrderCount;
		}
		int remainShareCount=0;
		if(right.getFreeVinParseNum1()>hadShareCount){
			remainShareCount=right.getFreeVinParseNum1()-hadShareCount;
		}
		reMap.put("vinParsePrice",right.getVinParsePrice());//--vin查询金额
		reMap.put("remainShareCount",remainShareCount);//剩余免费分享微信次数
		reMap.put("freeShareCount",right.getFreeVinParseNum1());//免费分享微信总数
		reMap.put("remainFreeNum",remainFreeNum);//剩余免费查询次数
		reMap.put("freeVinParseNum",right.getFreeVinParseNum());//免费查询总数
		reMap.put("outTradeNo",id.toString());//订单号
		//--是否免费
		if(right.getVinParsePrice().equals(0.0)){
			reMap.put("isFree",true);
		}else {
			reMap.put("isFree",false);
		}

		return R.data(reMap);

	}


	@GetMapping("/vinPrderPage")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "VIN订单记录", notes = "传入payOrder")
	public R<IPage<PayOrderVO>> vinPrderPage(Query query) {
		Member cl = wMemberFactory.getMember(request);
		PayOrderVO payOrder=new PayOrderVO();
		payOrder.setMemberId(cl.getId());
		List<Integer> types=new ArrayList<>();
		types.add(0,3);
		types.add(1,4);

		payOrder.setTypes(types);
		payOrder.setStatus(2);
		IPage<PayOrderVO> pages = payOrderService.selectPayOrderPage(Condition.getPage(query), payOrder);
		return R.data(pages);
	}
//	/**
//	 * 详情
//	 */
//	@GetMapping("/vinQuerydetail")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "vin查询记录详情", notes = "传入vinParse")
//	public R<VinVehicle> detail(VinParse vinParse) {
//		VinParse detail = vinParseService.getOne(Condition.getQueryWrapper(vinParse));
//		VinVehicle vehicle=new VinVehicle();
//		if(Func.isNotEmpty(detail)) {
//			vehicle = buildVinVehicle(detail.getRes());
//		}
//		return R.data(vehicle);
//	}

}
