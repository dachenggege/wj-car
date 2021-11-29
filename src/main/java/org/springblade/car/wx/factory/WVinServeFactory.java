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
package org.springblade.car.wx.factory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.GeneralEfficientOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralEfficientOCRResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import liquibase.pro.packaged.N;
import lombok.AllArgsConstructor;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.lang3.StringUtils;
import org.springblade.car.dto.CarsVinParseReq;
import org.springblade.car.dto.VinParseData;
import org.springblade.car.dto.VinVehicle;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.Series;
import org.springblade.car.entity.Styles;
import org.springblade.car.entity.VinParse;
import org.springblade.car.service.IBrandService;
import org.springblade.car.service.ISeriesService;
import org.springblade.car.service.IStylesService;
import org.springblade.car.service.IVinParseService;
import org.springblade.car.vo.VinParseVO;
import org.springblade.car.wx.constants.JuheConstant;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.FindVinUtil;
import org.springblade.util.JsonUtils;
import org.springblade.util.SimilarityUtils;
import org.springblade.util.VinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-07-28
 */
@Component
public class WVinServeFactory{
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private  IVinParseService vinParseService;
	@Autowired
	private BladeRedis bladeRedis;
	@Autowired
	private  ISeriesService seriesService;
	@Autowired
	private  IStylesService stylesService;

	String SecretId="AKIDRgfPtTnpb6LJ0QaT5QUHn32lERN2QXO0";
	String SecretKey="MRFm33mEPKxKRHrACwPbYGhrmrgEVVFi";

	/**
	 * 发布车源时的vin查询返回信息
	 */
	public CarsVinParseReq carVinQuery(String vin) {
		System.out.println("发布车源vin查询vin=："+vin);
		VinVehicle entity=vinParse(vin).getVinVehicle();
		CarsVinParseReq cars = new CarsVinParseReq();
		cars.setPvin(vin);
		if(Func.isNotEmpty(entity)) {
			Double ListPrice= checkNumber(entity.getListPrice())/10000;
			Map<String, Object> queryMap = new HashMap<>();
			//queryMap.put("styles_price", ListPrice);
			queryMap.put("styles_year",entity.getYearPattern());
			String brandName=entity.getBrandName();
			brandName=StringUtils.substringBefore(brandName, "(");
			String vehicleAlias=entity.getVehicleAlias();
			vehicleAlias=StringUtils.substringBefore(vehicleAlias, " ");
			queryMap.put("group_name", brandName);
			queryMap.put("series_name", vehicleAlias);
			List<Styles> stylesList = stylesService.selectStylesVin(queryMap);
			String keywords= entity.getVehicleName()+entity.getVehicleAlias();
			Long stylesId= getMaxValue(stylesList,keywords);
			Styles styles=stylesService.getById(stylesId);
			if(Func.isNotEmpty(styles)) {
				cars.setBrandId(Long.valueOf(styles.getBrandId()));
				cars.setBrandName(styles.getBrandName());
				cars.setSeriesId(Long.valueOf(styles.getSeriesId()));
				cars.setSeriesName(styles.getSeriesName());
				cars.setStylesId(styles.getId());
				cars.setStylesName(styles.getStylesName());
				Series series = seriesService.getById(styles.getSeriesId());
				if (Func.isNotEmpty(series)) {
					cars.setModelId(Long.valueOf(series.getModelId()));
					cars.setModelName(series.getModelName());
				}
			}
				cars.setPprice(BigDecimal.valueOf(ListPrice));
				cars.setPallname(entity.getVehicleName());
				cars.setPcolor(entity.getVehicleColor());//颜色
				cars.setPgas(entity.getDisplacement());//排量
				cars.setPtransmission(entity.getGearboxType());//变速箱
				cars.setPemission(entity.getEffluentStandard());//排放标准
				cars.setPfuel(entity.getPowerType());//燃油类型

		}




		return cars;

	}
	public Long getMaxValue(List<Styles> stylesList ,String keywords){
		keywords=keywords.replace(" ","");
		//System.out.println("keywords="+keywords);
		String str="";
		//取匹配最高的
		Map<Long,Integer> maxSim=new HashMap<>();
		for (Styles entity : stylesList) {
		//	keywords= entity.getYearPattern()+entity.getBrandName()+entity.getFamilyName()+entity.getGroupName();
			//styles_name,styles_year,brand_name,series_name,group_name,configuration
			str= entity.getStylesYear()+entity.getBrandName()+entity.getStylesName() +entity.getConfiguration()+entity.getGroupName()+entity.getSeriesName();
			//System.out.println(str);
			str=SimilarityUtils.removeRepeatChar(str).replace(" ","");;
			//System.out.println(str);
			maxSim.put(entity.getId(),SimilarityUtils.subCount(keywords,str));
		}
		return SimilarityUtils.getMaxValue(maxSim);
	}


	public VinParseData vinParse(String vin) {
		String key=CacheNames.VIN_KEY+vin;
		String res=bladeRedis.get(key);
		if(Func.isEmpty(res)){
			VinParse queryvinParse=new VinParseVO();
			queryvinParse.setVin(vin);
			List<VinParse> vinParseList = vinParseService.list(Condition.getQueryWrapper(queryvinParse));
			if(Func.isNotEmpty(vinParseList)) {
				if(vinParseList.size()>0) {
					res=vinParseList.get(0).getRes();
				}
			}
			else{
				StringBuffer url = new StringBuffer();
				url.append(JuheConstant.vinParse_URL).append("?key=").append(JuheConstant.vinParse_KEY)
						.append("&vin=").append(vin);
				res = restTemplate.getForEntity(url.toString(), String.class).getBody();
				bladeRedis.set(CacheNames.VIN_KEY + vin, res);

				VinParse vinParse = new VinParseVO();
				vinParse.setVin(vin);
				vinParse.setRes(res);
				vinParseService.save(vinParse);
			}
		}

		return buildVinVehicle(res);

	}
	public Double checkNumber(String str){

		Double price=0d;
		if(null == str || "" == str){
			return price;
		}
		str=str.replace("万", "");
		boolean int_flag = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean double_flag = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
		if(int_flag){
			return price=Double.parseDouble(str);
		} else if(double_flag){
			return price=Double.parseDouble(str);
		} else {
			return price;
		}

	}

	public VinParseData buildVinVehicle(String res){
		List<VinVehicle> vehicleList = new ArrayList<>();
		VinParseData vinParseData=new VinParseData();
		if (StringUtils.isNotEmpty(res)) {
			JSONObject jsonObject = JSONObject.parseObject(res);
			if (jsonObject.containsKey("result") && jsonObject.containsKey("reason") && jsonObject.containsKey("error_code")) {
				String reason = jsonObject.getString("reason");
				String error_code = jsonObject.getString("error_code");
				vinParseData.setError_code(error_code);
				vinParseData.setReason(reason);
				String result = jsonObject.getString("result");
				if (!Func.equals("success", reason)){
					throw new ServiceException("请上传清晰的VIN："+reason);
				}
				if (!Func.equals("0", error_code)){
					throw new ServiceException("请上传清晰的VIN："+reason);
				}
				if (Func.isNotEmpty(result)) {
					JSONObject lotteryJson = JSONObject.parseObject(result);
					if (lotteryJson.containsKey("vehicleList")) {
						vehicleList = JsonUtils.getObjectFromJsonString(lotteryJson.getString("vehicleList"), new TypeReference<List<VinVehicle>>(VinVehicle.class) {
						});
					}
				}
			}
		}
		VinVehicle vehicle=new VinVehicle();
		for (VinVehicle entity : vehicleList) {
			vehicle =entity;
		}
		vinParseData.setVinVehicle(vehicle);
	return vinParseData;

	}

	public boolean isCheckVin(String vin){
		Boolean res=true;
		if(Func.isEmpty(vin)){
			throw new ServiceException("vin不能为空");
		}
		if(vin.length()!=17){
			throw new ServiceException("vin格式不正确");
		}
		Pattern p = Pattern.compile("[0-9A-Z]{1,}");
		Matcher m = p.matcher(vin);
		if(!m.matches()){
			throw new ServiceException("vin格式不正确");
		}
		return m.matches();
	}
	public boolean isqcrCheckVin(String vin){
		Boolean res=true;
		if(Func.isEmpty(vin)){
			throw new ServiceException("无法识别，请上传清晰的vin图片");
		}
		if(vin.length()!=17){
			throw new ServiceException("无法识别，请上传清晰的vin图片");
		}
		Pattern p = Pattern.compile("^[A-HJ-NPR-Z\\d]{17}$");
		Matcher m = p.matcher(vin);
		if(!m.matches()){
			throw new ServiceException("无法识别，请上传清晰的vin图片");
		}
		return m.matches();
	}


	public String getVin(String str) {
		String vin="";
		if (Func.isEmpty(str)) {
			return vin;
		}
		//String str="{\"TextDetections\":[{\"DetectedText\":\"EYD比亚迪汽车有限公司制造\",\"Confidence\":92,\"Polygon\":[{\"X\":85,\"Y\":676},{\"X\":794,\"Y\":641},{\"X\":798,\"Y\":706},{\"X\":88,\"Y\":741}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":149,\"Y\":681,\"Width\":711,\"Height\":66}},{\"DetectedText\":\"品牌比亚迪\",\"Confidence\":99,\"Polygon\":[{\"X\":81,\"Y\":789},{\"X\":371,\"Y\":774},{\"X\":374,\"Y\":825},{\"X\":83,\"Y\":840}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":139,\"Y\":793,\"Width\":292,\"Height\":52}},{\"DetectedText\":\"制造国中国\",\"Confidence\":99,\"Polygon\":[{\"X\":564,\"Y\":769},{\"X\":771,\"Y\":758},{\"X\":774,\"Y\":805},{\"X\":567,\"Y\":815}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":623,\"Y\":797,\"Width\":208,\"Height\":48}},{\"DetectedText\":\"型号QCJI7240E\",\"Confidence\":92,\"Polygon\":[{\"X\":81,\"Y\":839},{\"X\":450,\"Y\":820},{\"X\":453,\"Y\":873},{\"X\":84,\"Y\":892}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":137,\"Y\":843,\"Width\":370,\"Height\":54}},{\"DetectedText\":\"座位数5\",\"Confidence\":98,\"Polygon\":[{\"X\":572,\"Y\":824},{\"X\":747,\"Y\":815},{\"X\":749,\"Y\":858},{\"X\":574,\"Y\":867}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":628,\"Y\":853,\"Width\":176,\"Height\":44}},{\"DetectedText\":\"出厂日期\",\"Confidence\":97,\"Polygon\":[{\"X\":84,\"Y\":884},{\"X\":292,\"Y\":873},{\"X\":295,\"Y\":935},{\"X\":87,\"Y\":946}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":137,\"Y\":888,\"Width\":210,\"Height\":63}},{\"DetectedText\":\"2010\",\"Confidence\":99,\"Polygon\":[{\"X\":332,\"Y\":882},{\"X\":442,\"Y\":877},{\"X\":444,\"Y\":931},{\"X\":335,\"Y\":936}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":385,\"Y\":899,\"Width\":111,\"Height\":55}},{\"DetectedText\":\"02月\",\"Confidence\":99,\"Polygon\":[{\"X\":604,\"Y\":862},{\"X\":743,\"Y\":855},{\"X\":746,\"Y\":917},{\"X\":607,\"Y\":924}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":658,\"Y\":892,\"Width\":140,\"Height\":63}},{\"DetectedText\":\"发动机型号\",\"Confidence\":99,\"Polygon\":[{\"X\":84,\"Y\":944},{\"X\":268,\"Y\":935},{\"X\":271,\"Y\":989},{\"X\":86,\"Y\":999}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":134,\"Y\":948,\"Width\":186,\"Height\":56}},{\"DetectedText\":\"4G69S4M\",\"Confidence\":98,\"Polygon\":[{\"X\":334,\"Y\":940},{\"X\":515,\"Y\":931},{\"X\":517,\"Y\":977},{\"X\":336,\"Y\":986}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":384,\"Y\":957,\"Width\":182,\"Height\":47}},{\"DetectedText\":\"额定功率\",\"Confidence\":99,\"Polygon\":[{\"X\":91,\"Y\":1010},{\"X\":236,\"Y\":1002},{\"X\":238,\"Y\":1047},{\"X\":93,\"Y\":1055}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":138,\"Y\":1014,\"Width\":146,\"Height\":46}},{\"DetectedText\":\"118kW\",\"Confidence\":99,\"Polygon\":[{\"X\":345,\"Y\":997},{\"X\":492,\"Y\":989},{\"X\":495,\"Y\":1034},{\"X\":347,\"Y\":1042}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":6}}\",\"ItemPolygon\":{\"X\":392,\"Y\":1014,\"Width\":149,\"Height\":46}},{\"DetectedText\":\"VIN码\",\"Confidence\":97,\"Polygon\":[{\"X\":91,\"Y\":1064},{\"X\":237,\"Y\":1056},{\"X\":240,\"Y\":1115},{\"X\":94,\"Y\":1123}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":135,\"Y\":1068,\"Width\":148,\"Height\":60}},{\"DetectedText\":\"LGXC36DG3A1030154\",\"Confidence\":96,\"Polygon\":[{\"X\":345,\"Y\":1051},{\"X\":808,\"Y\":1028},{\"X\":811,\"Y\":1083},{\"X\":348,\"Y\":1106}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":390,\"Y\":1068,\"Width\":464,\"Height\":56}},{\"DetectedText\":\"发动机排量\",\"Confidence\":99,\"Polygon\":[{\"X\":87,\"Y\":1125},{\"X\":287,\"Y\":1115},{\"X\":290,\"Y\":1171},{\"X\":90,\"Y\":1181}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":128,\"Y\":1129,\"Width\":202,\"Height\":57}},{\"DetectedText\":\"2378mL总质量1855kg\",\"Confidence\":99,\"Polygon\":[{\"X\":345,\"Y\":1109},{\"X\":877,\"Y\":1082},{\"X\":879,\"Y\":1137},{\"X\":348,\"Y\":1164}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":387,\"Y\":1126,\"Width\":533,\"Height\":56}}],\"Angel\":-2.875,\"RequestId\":\"974f1519-3af5-481a-a456-3f707928dc5c\"}";
		JSONObject jsonObject=JSONObject.parseObject(str);
		JSONArray DetectedText=jsonObject.getJSONArray("TextDetections");
		System.out.println(vin);
		for(int i=0;i<DetectedText.size();i++) {
			JSONObject detectedText=DetectedText.getJSONObject(i);
			vin= detectedText.getString("DetectedText").trim();
			System.out.println(vin);
			vin=FindVinUtil.readVinByPattern(vin.replace(" ",""));
			if(Func.isNotEmpty(vin)){
				return  vin;
			}
		}
		//System.out.println(vin);
		return vin;
	}

	public String jjqcrVinQuery(String imageBase64) {
		String vin="";
		try{

			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SecretId, SecretKey);
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("ocr.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的
			OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

			// 实例化一个请求对象,每个接口都会对应一个request对象
			GeneralEfficientOCRRequest req = new GeneralEfficientOCRRequest();
			req.setImageBase64(imageBase64);
			//req.setImageUrl("http://120.25.247.50:9000/car/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20211123161341.jpg");
			// 返回的resp是一个GeneralEfficientOCRResponse的实例，与请求对象对应
			GeneralEfficientOCRResponse resp = client.GeneralEfficientOCR(req);
			// 输出json格式的字符串回包
			String respString=GeneralEfficientOCRResponse.toJsonString(resp);
			if(Func.isEmpty(respString)){
				throw new ServiceException("识别失败，请上传清晰的图片");
			}
			vin=getVin(respString);
			if(Func.isEmpty(vin)){
				throw new ServiceException("识别失败，请上传清晰的图片");
			}

			if(!VinUtil.isValidVin(vin)){
				throw new ServiceException("识别失败，请上传清晰的图片");
			}
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return vin;
	}
	public String gjqcrVinQuery(String imageBase64) {
		String vin="";
		try{
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SecretId, SecretKey);
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("ocr.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的
			OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			GeneralAccurateOCRRequest req = new GeneralAccurateOCRRequest();
			req.setImageBase64(imageBase64);
			// 返回的resp是一个GeneralAccurateOCRResponse的实例，与请求对象对应
			GeneralAccurateOCRResponse resp = client.GeneralAccurateOCR(req);
			// 输出json格式的字符串回包
			System.out.println(GeneralAccurateOCRResponse.toJsonString(resp));
			String respString=GeneralEfficientOCRResponse.toJsonString(resp);
			if(Func.isEmpty(respString)){
				throw new ServiceException("识别失败，请上传清晰的图片");
			}
			vin=getVin(respString);
			if(Func.isEmpty(vin)){
				throw new ServiceException("识别失败，请上传清晰的图片");
			}
			isqcrCheckVin(vin);
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return vin;
	}
//	public static void main(String[] args) {
//		//String vin="{\"TextDetections\":[{\"DetectedText\":\"BYD\",\"Confidence\":72,\"Polygon\":[{\"X\":91,\"Y\":504},{\"X\":196,\"Y\":517},{\"X\":190,\"Y\":563},{\"X\":86,\"Y\":550}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":110,\"Y\":538,\"Width\":105,\"Height\":46}},{\"DetectedText\":\"比亚迪汽车有限公司\",\"Confidence\":99,\"Polygon\":[{\"X\":282,\"Y\":519},{\"X\":770,\"Y\":567},{\"X\":764,\"Y\":632},{\"X\":275,\"Y\":585}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":301,\"Y\":546,\"Width\":490,\"Height\":65}},{\"DetectedText\":\"制造\",\"Confidence\":99,\"Polygon\":[{\"X\":829,\"Y\":573},{\"X\":929,\"Y\":582},{\"X\":923,\"Y\":640},{\"X\":824,\"Y\":631}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":850,\"Y\":581,\"Width\":100,\"Height\":58}},{\"DetectedText\":\"品牌\",\"Confidence\":98,\"Polygon\":[{\"X\":73,\"Y\":590},{\"X\":186,\"Y\":597},{\"X\":182,\"Y\":672},{\"X\":68,\"Y\":665}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":95,\"Y\":624,\"Width\":113,\"Height\":75}},{\"DetectedText\":\"比亚迪\",\"Confidence\":99,\"Polygon\":[{\"X\":282,\"Y\":615},{\"X\":428,\"Y\":627},{\"X\":423,\"Y\":689},{\"X\":277,\"Y\":677}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":304,\"Y\":642,\"Width\":146,\"Height\":62}},{\"DetectedText\":\"制造国中国\",\"Confidence\":99,\"Polygon\":[{\"X\":659,\"Y\":644},{\"X\":928,\"Y\":653},{\"X\":926,\"Y\":715},{\"X\":657,\"Y\":706}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":682,\"Y\":658,\"Width\":269,\"Height\":62}},{\"DetectedText\":\"整车型号\",\"Confidence\":99,\"Polygon\":[{\"X\":70,\"Y\":661},{\"X\":275,\"Y\":674},{\"X\":270,\"Y\":747},{\"X\":66,\"Y\":734}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":94,\"Y\":695,\"Width\":205,\"Height\":73}},{\"DetectedText\":\"BYD7152WT3\",\"Confidence\":99,\"Polygon\":[{\"X\":336,\"Y\":685},{\"X\":610,\"Y\":698},{\"X\":607,\"Y\":758},{\"X\":333,\"Y\":746}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":361,\"Y\":710,\"Width\":274,\"Height\":60}},{\"DetectedText\":\"乘坐人数\",\"Confidence\":99,\"Polygon\":[{\"X\":665,\"Y\":705},{\"X\":835,\"Y\":712},{\"X\":833,\"Y\":771},{\"X\":663,\"Y\":765}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":690,\"Y\":718,\"Width\":170,\"Height\":59}},{\"DetectedText\":\"，5\",\"Confidence\":63,\"Polygon\":[{\"X\":835,\"Y\":717},{\"X\":896,\"Y\":723},{\"X\":891,\"Y\":775},{\"X\":830,\"Y\":769}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":861,\"Y\":724,\"Width\":61,\"Height\":52}},{\"DetectedText\":\"制造年月\",\"Confidence\":99,\"Polygon\":[{\"X\":65,\"Y\":734},{\"X\":266,\"Y\":742},{\"X\":264,\"Y\":814},{\"X\":63,\"Y\":807}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":92,\"Y\":768,\"Width\":201,\"Height\":72}},{\"DetectedText\":\"2018年09月\",\"Confidence\":99,\"Polygon\":[{\"X\":342,\"Y\":758},{\"X\":585,\"Y\":763},{\"X\":583,\"Y\":824},{\"X\":341,\"Y\":818}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":369,\"Y\":783,\"Width\":243,\"Height\":61}},{\"DetectedText\":\"发动机型号\",\"Confidence\":99,\"Polygon\":[{\"X\":58,\"Y\":809},{\"X\":322,\"Y\":809},{\"X\":322,\"Y\":885},{\"X\":58,\"Y\":885}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":87,\"Y\":844,\"Width\":264,\"Height\":76}},{\"DetectedText\":\"BYD476ZQA\",\"Confidence\":99,\"Polygon\":[{\"X\":326,\"Y\":825},{\"X\":620,\"Y\":831},{\"X\":619,\"Y\":891},{\"X\":324,\"Y\":885}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":356,\"Y\":850,\"Width\":294,\"Height\":60}},{\"DetectedText\":\"发动机排量\",\"Confidence\":99,\"Polygon\":[{\"X\":54,\"Y\":892},{\"X\":331,\"Y\":892},{\"X\":331,\"Y\":964},{\"X\":54,\"Y\":964}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":86,\"Y\":927,\"Width\":276,\"Height\":71}},{\"DetectedText\":\"1497mL\",\"Confidence\":98,\"Polygon\":[{\"X\":329,\"Y\":901},{\"X\":509,\"Y\":904},{\"X\":508,\"Y\":962},{\"X\":328,\"Y\":959}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":361,\"Y\":926,\"Width\":180,\"Height\":58}},{\"DetectedText\":\"发动机最大净功率105kW\",\"Confidence\":99,\"Polygon\":[{\"X\":50,\"Y\":974},{\"X\":619,\"Y\":959},{\"X\":621,\"Y\":1032},{\"X\":52,\"Y\":1047}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":6}}\",\"ItemPolygon\":{\"X\":85,\"Y\":1009,\"Width\":569,\"Height\":73}},{\"DetectedText\":\"车辆识别代号LGXCG6CF8J0180649\",\"Confidence\":98,\"Polygon\":[{\"X\":49,\"Y\":1046},{\"X\":848,\"Y\":1017},{\"X\":850,\"Y\":1089},{\"X\":51,\"Y\":1118}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":87,\"Y\":1081,\"Width\":799,\"Height\":72}},{\"DetectedText\":\"最大允许总质量\",\"Confidence\":99,\"Polygon\":[{\"X\":40,\"Y\":1126},{\"X\":431,\"Y\":1105},{\"X\":435,\"Y\":1176},{\"X\":43,\"Y\":1197}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":80,\"Y\":1161,\"Width\":391,\"Height\":71}},{\"DetectedText\":\"1735kg\",\"Confidence\":99,\"Polygon\":[{\"X\":466,\"Y\":1112},{\"X\":615,\"Y\":1112},{\"X\":615,\"Y\":1173},{\"X\":466,\"Y\":1173}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":506,\"Y\":1132,\"Width\":148,\"Height\":60}}],\"Angel\":2.0000098,\"RequestId\":\"b6165dcb-200c-41ba-a24d-fcec1099d128\"}";
//// //String vin="{\"TextDetections\":[{\"DetectedText\":\"国东风柳州汽车有限公司制\",\"Confidence\":99,\"Polygon\":[{\"X\":10,\"Y\":237},{\"X\":368,\"Y\":253},{\"X\":366,\"Y\":297},{\"X\":8,\"Y\":281}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":20,\"Y\":252,\"Width\":358,\"Height\":44}},{\"DetectedText\":\"VIN:LGG7E3D2612512997\",\"Confidence\":87,\"Polygon\":[{\"X\":31,\"Y\":288},{\"X\":333,\"Y\":298},{\"X\":332,\"Y\":339},{\"X\":30,\"Y\":330}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":44,\"Y\":302,\"Width\":302,\"Height\":41}},{\"DetectedText\":\"520MQ20M\",\"Confidence\":96,\"Polygon\":[{\"X\":18,\"Y\":339},{\"X\":127,\"Y\":342},{\"X\":126,\"Y\":379},{\"X\":17,\"Y\":376}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":33,\"Y\":354,\"Width\":109,\"Height\":37}},{\"DetectedText\":\"发动机排量:1997山\",\"Confidence\":90,\"Polygon\":[{\"X\":177,\"Y\":341},{\"X\":366,\"Y\":341},{\"X\":366,\"Y\":379},{\"X\":177,\"Y\":379}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":192,\"Y\":349,\"Width\":188,\"Height\":38}},{\"DetectedText\":\"G63S4T\",\"Confidence\":87,\"Polygon\":[{\"X\":20,\"Y\":387},{\"X\":99,\"Y\":387},{\"X\":99,\"Y\":423},{\"X\":20,\"Y\":423}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":37,\"Y\":402,\"Width\":79,\"Height\":36}},{\"DetectedText\":\"最大允许总质量:26\",\"Confidence\":94,\"Polygon\":[{\"X\":181,\"Y\":386},{\"X\":366,\"Y\":386},{\"X\":366,\"Y\":424},{\"X\":181,\"Y\":424}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":197,\"Y\":394,\"Width\":185,\"Height\":37}},{\"DetectedText\":\"为率:130 kW\",\"Confidence\":77,\"Polygon\":[{\"X\":23,\"Y\":432},{\"X\":159,\"Y\":432},{\"X\":159,\"Y\":470},{\"X\":23,\"Y\":470}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":41,\"Y\":447,\"Width\":136,\"Height\":38}},{\"DetectedText\":\"乘坐人数.7\",\"Confidence\":77,\"Polygon\":[{\"X\":182,\"Y\":430},{\"X\":302,\"Y\":429},{\"X\":302,\"Y\":465},{\"X\":182,\"Y\":467}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":200,\"Y\":438,\"Width\":120,\"Height\":36}},{\"DetectedText\":\"7/10\",\"Confidence\":99,\"Polygon\":[{\"X\":28,\"Y\":479},{\"X\":84,\"Y\":479},{\"X\":84,\"Y\":515},{\"X\":28,\"Y\":515}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":48,\"Y\":493,\"Width\":56,\"Height\":35}}],\"Angel\":2.3333533,\"RequestId\":\"54865bdb-2266-4ce8-a46f-450766192004\"}\n";
////
////
////	String vin="{\"TextDetections\":[{\"DetectedText\":\"北京现代汽车有限公司制造BHMC\",\"Confidence\":99,\"Polygon\":[{\"X\":247,\"Y\":206},{\"X\":702,\"Y\":210},{\"X\":702,\"Y\":238},{\"X\":247,\"Y\":234}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":247,\"Y\":206,\"Width\":455,\"Height\":28}},{\"DetectedText\":\"品牌:\",\"Confidence\":99,\"Polygon\":[{\"X\":173,\"Y\":238},{\"X\":224,\"Y\":238},{\"X\":224,\"Y\":261},{\"X\":173,\"Y\":261}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":173,\"Y\":238,\"Width\":51,\"Height\":23}},{\"DetectedText\":\"车辆识别号(VIN):\",\"Confidence\":99,\"Polygon\":[{\"X\":319,\"Y\":240},{\"X\":514,\"Y\":240},{\"X\":514,\"Y\":262},{\"X\":319,\"Y\":262}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":319,\"Y\":240,\"Width\":195,\"Height\":22}},{\"DetectedText\":\"制造国:\",\"Confidence\":94,\"Polygon\":[{\"X\":595,\"Y\":242},{\"X\":673,\"Y\":244},{\"X\":673,\"Y\":268},{\"X\":595,\"Y\":267}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":595,\"Y\":242,\"Width\":78,\"Height\":24}},{\"DetectedText\":\"北京现代\",\"Confidence\":99,\"Polygon\":[{\"X\":176,\"Y\":259},{\"X\":278,\"Y\":259},{\"X\":278,\"Y\":282},{\"X\":176,\"Y\":282}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":176,\"Y\":259,\"Width\":102,\"Height\":23}},{\"DetectedText\":\"LBEYE AKD7BY007178\",\"Confidence\":92,\"Polygon\":[{\"X\":324,\"Y\":257},{\"X\":567,\"Y\":262},{\"X\":567,\"Y\":290},{\"X\":324,\"Y\":286}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":324,\"Y\":257,\"Width\":243,\"Height\":28}},{\"DetectedText\":\"中国\",\"Confidence\":72,\"Polygon\":[{\"X\":594,\"Y\":266},{\"X\":656,\"Y\":266},{\"X\":656,\"Y\":286},{\"X\":594,\"Y\":286}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":594,\"Y\":266,\"Width\":62,\"Height\":20}},{\"DetectedText\":\"型号:\",\"Confidence\":99,\"Polygon\":[{\"X\":178,\"Y\":281},{\"X\":231,\"Y\":281},{\"X\":231,\"Y\":301},{\"X\":178,\"Y\":301}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":6}}\",\"ItemPolygon\":{\"X\":178,\"Y\":281,\"Width\":53,\"Height\":20}},{\"DetectedText\":\"最大设计总质量:\",\"Confidence\":94,\"Polygon\":[{\"X\":376,\"Y\":285},{\"X\":536,\"Y\":285},{\"X\":536,\"Y\":308},{\"X\":376,\"Y\":308}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":376,\"Y\":285,\"Width\":160,\"Height\":23}},{\"DetectedText\":\"制造年月:\",\"Confidence\":99,\"Polygon\":[{\"X\":581,\"Y\":285},{\"X\":676,\"Y\":285},{\"X\":676,\"Y\":306},{\"X\":581,\"Y\":306}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":8}}\",\"ItemPolygon\":{\"X\":581,\"Y\":285,\"Width\":95,\"Height\":21}},{\"DetectedText\":\"BH7201DAY\",\"Confidence\":98,\"Polygon\":[{\"X\":182,\"Y\":299},{\"X\":313,\"Y\":299},{\"X\":313,\"Y\":323},{\"X\":182,\"Y\":323}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":9}}\",\"ItemPolygon\":{\"X\":182,\"Y\":299,\"Width\":131,\"Height\":24}},{\"DetectedText\":\"1835 kg\",\"Confidence\":85,\"Polygon\":[{\"X\":400,\"Y\":305},{\"X\":497,\"Y\":305},{\"X\":497,\"Y\":325},{\"X\":400,\"Y\":325}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":10}}\",\"ItemPolygon\":{\"X\":400,\"Y\":305,\"Width\":97,\"Height\":20}},{\"DetectedText\":\"2011/04\",\"Confidence\":99,\"Polygon\":[{\"X\":578,\"Y\":303},{\"X\":673,\"Y\":307},{\"X\":672,\"Y\":329},{\"X\":577,\"Y\":326}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":11}}\",\"ItemPolygon\":{\"X\":578,\"Y\":303,\"Width\":95,\"Height\":22}},{\"DetectedText\":\"发动机型号:发动机排，额定功率\",\"Confidence\":94,\"Polygon\":[{\"X\":185,\"Y\":321},{\"X\":560,\"Y\":321},{\"X\":560,\"Y\":345},{\"X\":185,\"Y\":345}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":185,\"Y\":321,\"Width\":375,\"Height\":24}},{\"DetectedText\":\"乘坐人数;\",\"Confidence\":93,\"Polygon\":[{\"X\":574,\"Y\":324},{\"X\":666,\"Y\":326},{\"X\":666,\"Y\":349},{\"X\":574,\"Y\":348}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":574,\"Y\":324,\"Width\":92,\"Height\":23}},{\"DetectedText\":\"G4KD\",\"Confidence\":97,\"Polygon\":[{\"X\":188,\"Y\":342},{\"X\":246,\"Y\":342},{\"X\":246,\"Y\":363},{\"X\":188,\"Y\":363}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":188,\"Y\":342,\"Width\":58,\"Height\":21}},{\"DetectedText\":\"1998ml 121 kw\",\"Confidence\":79,\"Polygon\":[{\"X\":331,\"Y\":341},{\"X\":540,\"Y\":344},{\"X\":539,\"Y\":366},{\"X\":330,\"Y\":363}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":13}}\",\"ItemPolygon\":{\"X\":331,\"Y\":341,\"Width\":209,\"Height\":22}},{\"DetectedText\":\"5人\",\"Confidence\":99,\"Polygon\":[{\"X\":593,\"Y\":343},{\"X\":640,\"Y\":343},{\"X\":640,\"Y\":365},{\"X\":593,\"Y\":365}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":13}}\",\"ItemPolygon\":{\"X\":593,\"Y\":343,\"Width\":47,\"Height\":22}},{\"DetectedText\":\"变速器:P7\",\"Confidence\":88,\"Polygon\":[{\"X\":193,\"Y\":364},{\"X\":298,\"Y\":361},{\"X\":299,\"Y\":382},{\"X\":193,\"Y\":385}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":14}}\",\"ItemPolygon\":{\"X\":193,\"Y\":364,\"Width\":105,\"Height\":21}},{\"DetectedText\":\"主减速比:N漆色:9F内装色:YDA\",\"Confidence\":94,\"Polygon\":[{\"X\":308,\"Y\":358},{\"X\":662,\"Y\":362},{\"X\":662,\"Y\":388},{\"X\":308,\"Y\":384}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":14}}\",\"ItemPolygon\":{\"X\":308,\"Y\":358,\"Width\":354,\"Height\":26}}],\"Angel\":359.99,\"RequestId\":\"fa9a7b2c-1bc6-45d1-a8cb-82e279f81256\"}";
////
//		String vin="{\"TextDetections\":[{\"DetectedText\":\"北京现代汽车有限公司制造BHMC\",\"Confidence\":99,\"Polygon\":[{\"X\":247,\"Y\":206},{\"X\":702,\"Y\":210},{\"X\":702,\"Y\":238},{\"X\":247,\"Y\":234}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":247,\"Y\":206,\"Width\":455,\"Height\":28}},{\"DetectedText\":\"品牌:\",\"Confidence\":99,\"Polygon\":[{\"X\":173,\"Y\":238},{\"X\":224,\"Y\":238},{\"X\":224,\"Y\":261},{\"X\":173,\"Y\":261}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":173,\"Y\":238,\"Width\":51,\"Height\":23}},{\"DetectedText\":\"车辆识别号(VIN):\",\"Confidence\":99,\"Polygon\":[{\"X\":319,\"Y\":240},{\"X\":514,\"Y\":240},{\"X\":514,\"Y\":262},{\"X\":319,\"Y\":262}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":319,\"Y\":240,\"Width\":195,\"Height\":22}},{\"DetectedText\":\"制造国:\",\"Confidence\":94,\"Polygon\":[{\"X\":595,\"Y\":242},{\"X\":673,\"Y\":244},{\"X\":673,\"Y\":268},{\"X\":595,\"Y\":267}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":595,\"Y\":242,\"Width\":78,\"Height\":24}},{\"DetectedText\":\"北京现代\",\"Confidence\":99,\"Polygon\":[{\"X\":176,\"Y\":259},{\"X\":278,\"Y\":259},{\"X\":278,\"Y\":282},{\"X\":176,\"Y\":282}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":176,\"Y\":259,\"Width\":102,\"Height\":23}},{\"DetectedText\":\"LBEYE AKD7BY007178\",\"Confidence\":92,\"Polygon\":[{\"X\":324,\"Y\":257},{\"X\":567,\"Y\":262},{\"X\":567,\"Y\":290},{\"X\":324,\"Y\":286}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":324,\"Y\":257,\"Width\":243,\"Height\":28}},{\"DetectedText\":\"中国\",\"Confidence\":72,\"Polygon\":[{\"X\":594,\"Y\":266},{\"X\":656,\"Y\":266},{\"X\":656,\"Y\":286},{\"X\":594,\"Y\":286}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":594,\"Y\":266,\"Width\":62,\"Height\":20}},{\"DetectedText\":\"型号:\",\"Confidence\":99,\"Polygon\":[{\"X\":178,\"Y\":281},{\"X\":231,\"Y\":281},{\"X\":231,\"Y\":301},{\"X\":178,\"Y\":301}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":6}}\",\"ItemPolygon\":{\"X\":178,\"Y\":281,\"Width\":53,\"Height\":20}},{\"DetectedText\":\"最大设计总质量:\",\"Confidence\":94,\"Polygon\":[{\"X\":376,\"Y\":285},{\"X\":536,\"Y\":285},{\"X\":536,\"Y\":308},{\"X\":376,\"Y\":308}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":376,\"Y\":285,\"Width\":160,\"Height\":23}},{\"DetectedText\":\"制造年月:\",\"Confidence\":99,\"Polygon\":[{\"X\":581,\"Y\":285},{\"X\":676,\"Y\":285},{\"X\":676,\"Y\":306},{\"X\":581,\"Y\":306}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":8}}\",\"ItemPolygon\":{\"X\":581,\"Y\":285,\"Width\":95,\"Height\":21}},{\"DetectedText\":\"BH7201DAY\",\"Confidence\":98,\"Polygon\":[{\"X\":182,\"Y\":299},{\"X\":313,\"Y\":299},{\"X\":313,\"Y\":323},{\"X\":182,\"Y\":323}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":9}}\",\"ItemPolygon\":{\"X\":182,\"Y\":299,\"Width\":131,\"Height\":24}},{\"DetectedText\":\"1835 kg\",\"Confidence\":85,\"Polygon\":[{\"X\":400,\"Y\":305},{\"X\":497,\"Y\":305},{\"X\":497,\"Y\":325},{\"X\":400,\"Y\":325}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":10}}\",\"ItemPolygon\":{\"X\":400,\"Y\":305,\"Width\":97,\"Height\":20}},{\"DetectedText\":\"2011/04\",\"Confidence\":99,\"Polygon\":[{\"X\":578,\"Y\":303},{\"X\":673,\"Y\":307},{\"X\":672,\"Y\":329},{\"X\":577,\"Y\":326}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":11}}\",\"ItemPolygon\":{\"X\":578,\"Y\":303,\"Width\":95,\"Height\":22}},{\"DetectedText\":\"发动机型号:发动机排，额定功率\",\"Confidence\":94,\"Polygon\":[{\"X\":185,\"Y\":321},{\"X\":560,\"Y\":321},{\"X\":560,\"Y\":345},{\"X\":185,\"Y\":345}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":185,\"Y\":321,\"Width\":375,\"Height\":24}},{\"DetectedText\":\"乘坐人数;\",\"Confidence\":93,\"Polygon\":[{\"X\":574,\"Y\":324},{\"X\":666,\"Y\":326},{\"X\":666,\"Y\":349},{\"X\":574,\"Y\":348}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":574,\"Y\":324,\"Width\":92,\"Height\":23}},{\"DetectedText\":\"G4KD\",\"Confidence\":97,\"Polygon\":[{\"X\":188,\"Y\":342},{\"X\":246,\"Y\":342},{\"X\":246,\"Y\":363},{\"X\":188,\"Y\":363}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":12}}\",\"ItemPolygon\":{\"X\":188,\"Y\":342,\"Width\":58,\"Height\":21}},{\"DetectedText\":\"1998ml 121 kw\",\"Confidence\":79,\"Polygon\":[{\"X\":331,\"Y\":341},{\"X\":540,\"Y\":344},{\"X\":539,\"Y\":366},{\"X\":330,\"Y\":363}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":13}}\",\"ItemPolygon\":{\"X\":331,\"Y\":341,\"Width\":209,\"Height\":22}},{\"DetectedText\":\"5人\",\"Confidence\":99,\"Polygon\":[{\"X\":593,\"Y\":343},{\"X\":640,\"Y\":343},{\"X\":640,\"Y\":365},{\"X\":593,\"Y\":365}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":13}}\",\"ItemPolygon\":{\"X\":593,\"Y\":343,\"Width\":47,\"Height\":22}},{\"DetectedText\":\"变速器:P7\",\"Confidence\":88,\"Polygon\":[{\"X\":193,\"Y\":364},{\"X\":298,\"Y\":361},{\"X\":299,\"Y\":382},{\"X\":193,\"Y\":385}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":14}}\",\"ItemPolygon\":{\"X\":193,\"Y\":364,\"Width\":105,\"Height\":21}},{\"DetectedText\":\"主减速比:N漆色:9F内装色:YDA\",\"Confidence\":94,\"Polygon\":[{\"X\":308,\"Y\":358},{\"X\":662,\"Y\":362},{\"X\":662,\"Y\":388},{\"X\":308,\"Y\":384}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":14}}\",\"ItemPolygon\":{\"X\":308,\"Y\":358,\"Width\":354,\"Height\":26}}],\"Angel\":359.99,\"RequestId\":\"d1a8a39b-016f-4923-85f8-cf7a37f094f8\"}\n";
//		WVinServeFactory N=new WVinServeFactory();
//		//N.getVin(vin);
//		System.out.println("--------------------------"+N.getVin(vin));
//	}
}
