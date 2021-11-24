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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springblade.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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


	/**
	 * 发布车源时的vin查询返回信息
	 */
	public CarsVinParseReq carVinQuery(String vin) {
		VinVehicle entity=vinParse(vin).getVinVehicle();
		CarsVinParseReq cars = new CarsVinParseReq();

		if(Func.isNotEmpty(entity)) {
			Double ListPrice= checkNumber(entity.getListPrice())/10000;
			Map<String, Object> queryMap = new HashMap<>();
			//queryMap.put("styles_price", ListPrice);
			queryMap.put("styles_year",entity.getYearPattern());
			String brandName=entity.getBrandName();
			brandName=StringUtils.substringBefore(brandName, "(");
			queryMap.put("group_name", brandName);
			List<Styles> stylesList = stylesService.selectStylesVin(queryMap);
			Styles styles=new Styles();

			if(Func.isNotEmpty(stylesList)){
				int i=0;
				for(Styles d:stylesList){
					String s=d.getBrandName()+d.getGroupName()+d.getSeriesName()+d.getStylesName()+d.getConfiguration();
					String s1=entity.getBrandName()+ entity.getVehicleName()+entity.getRemark()+entity.getCfgLevel()+entity.getVehicleAlias();
					int  ratio= FuzzySearch.ratio(s1, s);
					if(ratio>i){
						i=ratio;
						styles=d;
					}
				}

			}

			if(Func.isNotEmpty(stylesList)){
				if(stylesList.size()>0){
					//Styles styles=stylesList.get(0);
					if(Func.isNotEmpty(styles)) {
						cars.setBrandId(Long.valueOf(styles.getBrandId()));
						cars.setBrandName(styles.getBrandName());
						cars.setSeriesId(Long.valueOf(styles.getSeriesId()));
						cars.setSeriesName(styles.getSeriesName());
						cars.setStylesId(styles.getId());
						cars.setStylesName(styles.getStylesName());
						Series series=seriesService.getById(styles.getSeriesId());
						if(Func.isNotEmpty(series)) {
							cars.setModelId(Long.valueOf(series.getModelId()));
							cars.setModelName(series.getModelName());
						}
					}
				}
			}
			cars.setPvin(vin);

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
					throw new ServiceException(reason);
				}
				if (!Func.equals("0", error_code)){
					throw new ServiceException(reason);
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


	public String getVin(String str) {
		String vin="";
		if (Func.isEmpty(str)) {
			return vin;
		}
		//String str="{\"TextDetections\":[{\"DetectedText\":\"EYD比亚迪汽车有限公司制造\",\"Confidence\":92,\"Polygon\":[{\"X\":85,\"Y\":676},{\"X\":794,\"Y\":641},{\"X\":798,\"Y\":706},{\"X\":88,\"Y\":741}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":1}}\",\"ItemPolygon\":{\"X\":149,\"Y\":681,\"Width\":711,\"Height\":66}},{\"DetectedText\":\"品牌比亚迪\",\"Confidence\":99,\"Polygon\":[{\"X\":81,\"Y\":789},{\"X\":371,\"Y\":774},{\"X\":374,\"Y\":825},{\"X\":83,\"Y\":840}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":139,\"Y\":793,\"Width\":292,\"Height\":52}},{\"DetectedText\":\"制造国中国\",\"Confidence\":99,\"Polygon\":[{\"X\":564,\"Y\":769},{\"X\":771,\"Y\":758},{\"X\":774,\"Y\":805},{\"X\":567,\"Y\":815}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":623,\"Y\":797,\"Width\":208,\"Height\":48}},{\"DetectedText\":\"型号QCJI7240E\",\"Confidence\":92,\"Polygon\":[{\"X\":81,\"Y\":839},{\"X\":450,\"Y\":820},{\"X\":453,\"Y\":873},{\"X\":84,\"Y\":892}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":137,\"Y\":843,\"Width\":370,\"Height\":54}},{\"DetectedText\":\"座位数5\",\"Confidence\":98,\"Polygon\":[{\"X\":572,\"Y\":824},{\"X\":747,\"Y\":815},{\"X\":749,\"Y\":858},{\"X\":574,\"Y\":867}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":628,\"Y\":853,\"Width\":176,\"Height\":44}},{\"DetectedText\":\"出厂日期\",\"Confidence\":97,\"Polygon\":[{\"X\":84,\"Y\":884},{\"X\":292,\"Y\":873},{\"X\":295,\"Y\":935},{\"X\":87,\"Y\":946}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":137,\"Y\":888,\"Width\":210,\"Height\":63}},{\"DetectedText\":\"2010\",\"Confidence\":99,\"Polygon\":[{\"X\":332,\"Y\":882},{\"X\":442,\"Y\":877},{\"X\":444,\"Y\":931},{\"X\":335,\"Y\":936}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":2}}\",\"ItemPolygon\":{\"X\":385,\"Y\":899,\"Width\":111,\"Height\":55}},{\"DetectedText\":\"02月\",\"Confidence\":99,\"Polygon\":[{\"X\":604,\"Y\":862},{\"X\":743,\"Y\":855},{\"X\":746,\"Y\":917},{\"X\":607,\"Y\":924}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":4}}\",\"ItemPolygon\":{\"X\":658,\"Y\":892,\"Width\":140,\"Height\":63}},{\"DetectedText\":\"发动机型号\",\"Confidence\":99,\"Polygon\":[{\"X\":84,\"Y\":944},{\"X\":268,\"Y\":935},{\"X\":271,\"Y\":989},{\"X\":86,\"Y\":999}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":134,\"Y\":948,\"Width\":186,\"Height\":56}},{\"DetectedText\":\"4G69S4M\",\"Confidence\":98,\"Polygon\":[{\"X\":334,\"Y\":940},{\"X\":515,\"Y\":931},{\"X\":517,\"Y\":977},{\"X\":336,\"Y\":986}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":5}}\",\"ItemPolygon\":{\"X\":384,\"Y\":957,\"Width\":182,\"Height\":47}},{\"DetectedText\":\"额定功率\",\"Confidence\":99,\"Polygon\":[{\"X\":91,\"Y\":1010},{\"X\":236,\"Y\":1002},{\"X\":238,\"Y\":1047},{\"X\":93,\"Y\":1055}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":138,\"Y\":1014,\"Width\":146,\"Height\":46}},{\"DetectedText\":\"118kW\",\"Confidence\":99,\"Polygon\":[{\"X\":345,\"Y\":997},{\"X\":492,\"Y\":989},{\"X\":495,\"Y\":1034},{\"X\":347,\"Y\":1042}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":6}}\",\"ItemPolygon\":{\"X\":392,\"Y\":1014,\"Width\":149,\"Height\":46}},{\"DetectedText\":\"VIN码\",\"Confidence\":97,\"Polygon\":[{\"X\":91,\"Y\":1064},{\"X\":237,\"Y\":1056},{\"X\":240,\"Y\":1115},{\"X\":94,\"Y\":1123}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":135,\"Y\":1068,\"Width\":148,\"Height\":60}},{\"DetectedText\":\"LGXC36DG3A1030154\",\"Confidence\":96,\"Polygon\":[{\"X\":345,\"Y\":1051},{\"X\":808,\"Y\":1028},{\"X\":811,\"Y\":1083},{\"X\":348,\"Y\":1106}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":390,\"Y\":1068,\"Width\":464,\"Height\":56}},{\"DetectedText\":\"发动机排量\",\"Confidence\":99,\"Polygon\":[{\"X\":87,\"Y\":1125},{\"X\":287,\"Y\":1115},{\"X\":290,\"Y\":1171},{\"X\":90,\"Y\":1181}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":3}}\",\"ItemPolygon\":{\"X\":128,\"Y\":1129,\"Width\":202,\"Height\":57}},{\"DetectedText\":\"2378mL总质量1855kg\",\"Confidence\":99,\"Polygon\":[{\"X\":345,\"Y\":1109},{\"X\":877,\"Y\":1082},{\"X\":879,\"Y\":1137},{\"X\":348,\"Y\":1164}],\"AdvancedInfo\":\"{\\\"Parag\\\":{\\\"ParagNo\\\":7}}\",\"ItemPolygon\":{\"X\":387,\"Y\":1126,\"Width\":533,\"Height\":56}}],\"Angel\":-2.875,\"RequestId\":\"974f1519-3af5-481a-a456-3f707928dc5c\"}";
		JSONObject jsonObject=JSONObject.parseObject(str);
		JSONArray DetectedText=jsonObject.getJSONArray("TextDetections");
		for(int i=0;i<DetectedText.size();i++) {
			JSONObject detectedText=DetectedText.getJSONObject(i);
			vin= detectedText.getString("DetectedText");
			if (Func.isEmpty(vin)) {
				continue;
			}
			if (vin.length() != 17) {
				continue;
			}
			Pattern p = Pattern.compile("[0-9A-Z]{1,}");
			Matcher m = p.matcher(vin);
			if (!m.matches()) {
				continue;
			}
			if (m.matches()) {
				break;
			}
		}
		System.out.println(vin);
		return vin;
	}
}
