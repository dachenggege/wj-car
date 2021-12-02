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
package org.springblade.car.vehiData.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springblade.car.vehiData.entity.Vcar;
import org.springblade.car.vehiData.entity.Vcardetail;
import org.springblade.car.vehiData.entity.Vtype;
import org.springblade.car.vehiData.service.IVcarService;
import org.springblade.car.vehiData.service.IVcardetailService;
import org.springblade.car.vehiData.service.IVtypeService;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.car.vehiData.entity.Vbrand;
import org.springblade.car.vehiData.vo.VbrandVO;
import org.springblade.car.vehiData.wrapper.VbrandWrapper;
import org.springblade.car.vehiData.service.IVbrandService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author bond
 * @since 2021-11-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vbrand")
@Api(value = "", tags = "接口")
public class VbrandController extends BladeController {

	private IVbrandService vbrandService;
	private IVtypeService vtypeService;
	private IVcarService vcarService;
	private IVcardetailService vcardetailService;


//	@GetMapping("/brand")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "1品牌")
	public R brand() {
		String host = "https://jisucxdq.market.alicloudapi.com";
		String path = "/car/brand";
		String method = "GET";
		String appcode = "580594f573c44d6596eab5da7b533fea";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		String str=null;
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			 HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// System.out.println(response.toString());
			//获取response的body

			 //System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));

			 str= EntityUtils.toString(response.getEntity(),"utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}

		//StringBuffer str = new StringBuffer();
		//str.append("{\"status\":0,\"msg\":\"ok\",\"result\":[{\"id\":1,\"name\":\"奥迪\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/1.png\",\"depth\":1},{\"id\":148147,\"name\":\"AEV ROBOTICS\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148147.png\",\"depth\":1},{\"id\":147483,\"name\":\"Aria\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/147483.png\",\"depth\":1},{\"id\":147482,\"name\":\"ATS\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/147482.png\",\"depth\":1},{\"id\":33702,\"name\":\"ALPINA\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/33702.png\",\"depth\":1},{\"id\":41347,\"name\":\"ARCFOX极狐\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/41347.png\",\"depth\":1},{\"id\":45673,\"name\":\"爱驰\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/45673.png\",\"depth\":1},{\"id\":47641,\"name\":\"ABT\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/47641.png\",\"depth\":1},{\"id\":47642,\"name\":\"AC Schnitzer\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/47642.png\",\"depth\":1},{\"id\":48263,\"name\":\"艾康尼克\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/48263.png\",\"depth\":1},{\"id\":67118,\"name\":\"Aspark\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/67118.png\",\"depth\":1},{\"id\":128954,\"name\":\"APEX\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/128954.png\",\"depth\":1},{\"id\":140500,\"name\":\"Apollo\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/140500.png?v=2020-11-06 21:13:00\",\"depth\":1},{\"id\":140499,\"name\":\"Atlis\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/140499.png?v=2020-11-03 18:30:25\",\"depth\":1},{\"id\":148148,\"name\":\"Agile Automotive\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148148.png\",\"depth\":1},{\"id\":148149,\"name\":\"Arash\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148149.png\",\"depth\":1},{\"id\":148154,\"name\":\"ARMADILLO\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148154.png\",\"depth\":1},{\"id\":3,\"name\":\"阿尔法·罗密欧\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/3.png\",\"depth\":1},{\"id\":2,\"name\":\"阿斯顿·马丁\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/2.png\",\"depth\":1},{\"id\":148153,\"name\":\"安凯客车\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148153.png\",\"depth\":1},{\"id\":148152,\"name\":\"Aviar\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148152.png\",\"depth\":1},{\"id\":148151,\"name\":\"Aurus\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148151.png\",\"depth\":1},{\"id\":148150,\"name\":\"Ariel\",\"initial\":\"A\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148150.png\",\"depth\":1},{\"id\":49281,\"name\":\"北京清行\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/49281.png\",\"depth\":1},{\"id\":145624,\"name\":\"北汽瑞翔\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/145624.png\",\"depth\":1},{\"id\":148159,\"name\":\"博世\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148159.png\",\"depth\":1},{\"id\":48264,\"name\":\"博郡汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/48264.png\",\"depth\":1},{\"id\":148158,\"name\":\"比克汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148158.png\",\"depth\":1},{\"id\":141181,\"name\":\"奔驰卡车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/141181.png\",\"depth\":1},{\"id\":148160,\"name\":\"北汽黑豹\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148160.png\",\"depth\":1},{\"id\":148157,\"name\":\"保斐利\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148157.png\",\"depth\":1},{\"id\":148156,\"name\":\"宝骐汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148156.png\",\"depth\":1},{\"id\":141182,\"name\":\"Brabham\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/141182.png\",\"depth\":1},{\"id\":141912,\"name\":\"北奔重卡\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/141912.png\",\"depth\":1},{\"id\":148155,\"name\":\"Bowler\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/148155.png\",\"depth\":1},{\"id\":39998,\"name\":\"铂驰\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/39998.png\",\"depth\":1},{\"id\":39989,\"name\":\"北汽道达\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/39989.png\",\"depth\":1},{\"id\":33691,\"name\":\"比速汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/33691.png\",\"depth\":1},{\"id\":47643,\"name\":\"拜腾\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/47643.png\",\"depth\":1},{\"id\":65631,\"name\":\"比德文汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/65631.png\",\"depth\":1},{\"id\":13,\"name\":\"标致\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/13.png\",\"depth\":1},{\"id\":14,\"name\":\"北汽幻速\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/14.png\",\"depth\":1},{\"id\":15,\"name\":\"保时捷\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/15.png\",\"depth\":1},{\"id\":16,\"name\":\"北京汽车\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/16.png\",\"depth\":1},{\"id\":17,\"name\":\"奔腾\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/17.png\",\"depth\":1},{\"id\":18,\"name\":\"北京\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/18.png\",\"depth\":1},{\"id\":20,\"name\":\"北汽制造\",\"initial\":\"B\",\"parentid\":0,\"logo\":\"http:\\/\\/pic1.jisuapi.cn\\/car\\/static\\/images\\/logo\\/300\\/20.png\",\"depth\":1}]}\n");
		boolean r=false;
		if(Func.isNotEmpty(str)) {
			JSONObject jsonObject = JSONObject.parseObject(str);
			JSONArray result = jsonObject.getJSONArray("result");
			List<Vbrand> list = new ArrayList<>();
			for (int i = 0; i < result.size(); i++) {
				JSONObject brandJson = result.getJSONObject(i);

				Vbrand brand = JSONObject.parseObject(brandJson.toJSONString(), Vbrand.class);
				System.out.println("id=" + brand.getId());
				list.add(brand);
			}
			 r = vbrandService.saveOrUpdateBatch(list);
		}
		return R.status(r);
	}

//	@GetMapping("/type")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "2车型")
	public R type() {
		Vbrand brand = new Vbrand();
		//brand.setId(1l);
		List<Vbrand> list= vbrandService.list(Condition.getQueryWrapper(brand));
		for(Vbrand vbrand:list){
			rundate(String.valueOf(vbrand.getId()));
		}
		return R.success("OK");
	}
	@GetMapping("/cardetail")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "3车型详细年款式")
	public R cardetail() {
		Vcar vcar = new Vcar();
		//vcar.setId(220l);
		//List<Vcar> list= vcarService.list(Condition.getQueryWrapper(vcar));
		List<Vcar> list= vcarService.RunCardetaillist();
		for(Vcar vcar1:list){
			carDetaldate(vcar1);
		}
		return R.success("OK");
	}
	public R rundate(String parentid) {
		String host = "https://jisucxdq.market.alicloudapi.com";
		String path = "/car/type";
		String method = "GET";
		String appcode = "580594f573c44d6596eab5da7b533fea";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("parentid", parentid);


		String str=null;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
			str=EntityUtils.toString(response.getEntity(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean r=false;
		if(Func.isNotEmpty(str)) {
			JSONObject jsonObject = JSONObject.parseObject(str);
			JSONArray result = jsonObject.getJSONArray("result");
			List<Vtype> Vtypelist = new ArrayList<>();
			List<Vcar> Vcarlist = new ArrayList<>();
			if(Func.isNotEmpty(result)) {
				for (int i = 0; i < result.size(); i++) {
					JSONObject vtypeJson = result.getJSONObject(i);
					Vtype vtype = JSONObject.parseObject(vtypeJson.toJSONString(), Vtype.class);
					//System.out.println("id=" + brand.getId());
					Vtypelist.add(vtype);

					JSONArray vcarJsonList = vtypeJson.getJSONArray("carlist");
					if(Func.isNotEmpty(vcarJsonList)) {
						for (int j = 0; j < vcarJsonList.size(); j++) {
							JSONObject vcarJson = vcarJsonList.getJSONObject(j);
							Vcar vcar = JSONObject.parseObject(vcarJson.toJSONString(), Vcar.class);
							Vcarlist.add(vcar);
						}
					}
				}
			}
			r=vcarService.saveOrUpdateBatch(Vcarlist);
			r = vtypeService.saveOrUpdateBatch(Vtypelist);
		}
		return R.status(r);
	}

	public R carDetaldate(Vcar vcar) {
		String host = "http://jisucxdq.market.alicloudapi.com";
		String path = "/car/car";
		String method = "GET";
		String appcode = "580594f573c44d6596eab5da7b533fea";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("parentid", String.valueOf(vcar.getId()));
		querys.put("sort", "sort");


		String str=null;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
			str=EntityUtils.toString(response.getEntity(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean r=false;
		if(Func.isNotEmpty(str)) {
			JSONObject jsonObject = JSONObject.parseObject(str);
			JSONObject resultJson = jsonObject.getJSONObject("result");
			Vtype Vtype = new Vtype();
			if(Func.isNotEmpty(resultJson)) {
				List<Vcardetail> VcardetailList = new ArrayList<>();
				JSONArray vcarJsonList = resultJson.getJSONArray("list");
				if(Func.isNotEmpty(vcarJsonList)) {
					for (int j = 0; j < vcarJsonList.size(); j++) {
						JSONObject vcarddtailJson = vcarJsonList.getJSONObject(j);
						Vcardetail vcardetail = JSONObject.parseObject(vcarddtailJson.toJSONString(), Vcardetail.class);
						vcardetail.setParentid(vcar.getId().intValue());
						vcardetail.setDepth(4);
						VcardetailList.add(vcardetail);
					}

					r = vcardetailService.saveOrUpdateBatch(VcardetailList);
				}
			}

		}
		return R.status(r);
	}
}
