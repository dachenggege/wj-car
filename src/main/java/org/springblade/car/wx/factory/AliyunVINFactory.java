package org.springblade.car.wx.factory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springblade.car.dto.CarsVinParseReq;
import org.springblade.car.dto.VinVehicle;
import org.springblade.car.entity.Series;
import org.springblade.car.entity.Styles;
import org.springblade.car.service.IStylesService;
import org.springblade.car.vehiData.entity.Vbrand;
import org.springblade.car.wx.dto.VinRepCar;
import org.springblade.car.wx.dto.VinRepDetail;
import org.springblade.car.wx.pay.WXConfig;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bond
 * @date 2021/4/28 17:45
 * @desc
 */
@Component
public class AliyunVINFactory {
	@Autowired
	private BladeRedis bladeRedis;
	@Autowired
	private IStylesService stylesService;

	public String vinappcode = "580594f573c44d6596eab5da7b533fea";
	/**
	 * 发布车源时的vin查询返回信息
	 */
	public CarsVinParseReq carVinQuery(String vin) {
		System.out.println("发布车源vin查询vin=："+vin);
		VinRepDetail entity=vinQuery(vin);
		CarsVinParseReq cars = new CarsVinParseReq();
		if(Func.isEmpty(entity)) {
			return cars;
		}
		if(Func.isNotEmpty(entity)) {
			//String ListPrice= entity.getPrice().replace("万","");
			List<VinRepCar> carlist=entity.getCarlist();
			if(Func.isNotEmpty(carlist)){
				VinRepCar repCar=carlist.get(0);

				Long carid= repCar.getCarid().longValue();
				Styles styles=stylesService.getById(carid);
				if(Func.isNotEmpty(styles)) {
					cars.setPvin(vin);
					cars.setBrandId(Long.valueOf(styles.getBrandId()));
					cars.setBrandName(styles.getBrandName());
					cars.setSeriesId(Long.valueOf(styles.getSeriesId()));
					cars.setSeriesName(styles.getSeriesName());
					cars.setStylesId(styles.getId());
					cars.setStylesName(styles.getStylesName());
					cars.setPprice(styles.getStylesPrice());
					cars.setModelId(Long.valueOf(styles.getModelId()));
					cars.setModelName(styles.getModelName());
				}
			}



			//cars.setPprice(BigDecimal.valueOf(ListPrice));
			cars.setPallname(entity.getName());
			//cars.setPcolor(entity.getVehicleColor());//颜色
			cars.setPgas(entity.getDisplacement());//排量
			cars.setPtransmission(entity.getGearbox());//变速箱
			cars.setPemission(entity.getEnvironmentalstandards());//排放标准
			cars.setPfuel(entity.getFueltype());//燃油类型

		}




		return cars;

	}
	public VinRepDetail vinQuery(String vin) {
		VinRepDetail vinRepDetail=new VinRepDetail();
		String key= CacheNames.ALIYUN_VIN_KEY+vin;
		String str=bladeRedis.get(key);
		if(Func.isEmpty(str))
		{
			String host = "https://jisuvindm.market.alicloudapi.com";
			String path = "/vin/query";
			String method = "ANY";//GET/POST 任意
			//String appcode = "580594f573c44d6596eab5da7b533fea";
			Map<String, String> headers = new HashMap<String, String>();
			//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			headers.put("Authorization", "APPCODE " + vinappcode);
			//根据API的要求，定义相对应的Content-Type
			headers.put("Content-Type", "application/json; charset=UTF-8");
			Map<String, String> querys = new HashMap<String, String>();
			querys.put("vin", vin);


			try {
				HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
				//获取response的body
				str= EntityUtils.toString(response.getEntity(),"utf-8");
				System.out.println("Aliyun-VIN返回信息="+str);
				bladeRedis.set(key,str);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(Func.isNotEmpty(str)){
			//Aliyun-VIN返回信息={"status":"201","msg":"VIN为空","result":""}

			JSONObject jsonObject = JSONObject.parseObject(str);
			JSONObject resultJson = jsonObject.getJSONObject("result");
			if(Func.isNotEmpty(resultJson)) {
				vinRepDetail = JSONObject.parseObject(resultJson.toJSONString(), VinRepDetail.class);
			}

		}
		System.out.println(vinRepDetail.getVin());
		return vinRepDetail;
	}


}
