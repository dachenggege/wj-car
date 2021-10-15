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
}
