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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
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
import lombok.AllArgsConstructor;
import org.springblade.car.dto.CarsDTO;
import org.springblade.car.dto.CarsVinParseReq;
import org.springblade.car.dto.ShopMemberRoleRightDTO;
import org.springblade.car.entity.*;
import org.springblade.car.enums.AuditStatus;
import org.springblade.car.enums.CarSort;
import org.springblade.car.service.*;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.wx.factory.AliyunVINFactory;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.car.wx.factory.WVinServeFactory;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Region;
import org.springblade.modules.system.service.IRegionService;
import org.springblade.util.NumberUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 车源表 控制器
 *
 * @author BladeX
 * @since 2021-07-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/wx/car")
@Api(value = "微信-车源", tags = "v2微信-车源相关接口")
@ApiSort(1007)
public class WCarController extends BladeController {
	private HttpServletRequest request;
	private WMemberFactory wMemberFactory;
	private final IShopService shopService;
	private final IRegionService regionService;
	private final IShopMemberService shopMemberService;
	private final ICarsService carsService;
	private final ISeriesService seriesService;
	private final ICarsCollectService casCollectService;
	private final IStylesService stylesService;
	private final ICarsBrowseService carsBrowseService;
	private IMemberService memberService;
	private WVinServeFactory wVinServeFactory;
	private AliyunVINFactory aliyunVINFactory;


	@PostMapping("/qcrVinQuery")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "vin图片识别车辆信息", notes = "imageBase64")
	public R<CarsVinParseReq> qcrVinQuery(@ApiParam(value = "imageBase64") @RequestBody String imageBase64) {
		CarsVinParseReq cars = new CarsVinParseReq();
		String vin=wVinServeFactory.gjqcrVinQuery(imageBase64);
		if(Func.isEmpty(vin)){
			return R.fail("无法识别，请上传清晰的vin图片");
		}
		System.out.println("发布车源vin图片识别车辆信息vin="+vin);
		//cars = wVinServeFactory.carVinQuery(vin);
		 cars = aliyunVINFactory.carVinQuery(vin);
		 if(Func.isEmpty(cars)){
			 cars = new CarsVinParseReq();
			 cars.setPvin(vin);
			return R.data(201,cars,"未识别到车辆信息");
		 }
		if(Func.isEmpty(cars.getPvin())){
			cars.setPvin(vin);
			return R.data(201,cars,"未识别到车辆信息");
		}
		return R.data(cars);
	}

	/**
	 * 车辆VIN识别
	 */
	@ApiLog("发布车源VIN查询")
	@GetMapping("/carVinQuery")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "发布车源VIN查询", notes = "传入vin号")
	public R<CarsVinParseReq> carVinQuery(@ApiParam(value = "车架号") @RequestParam(value = "vin", required = true)String vin) {
		wVinServeFactory.isCheckVin(vin);
		//CarsVinParseReq cars = wVinServeFactory.carVinQuery(vin);
		CarsVinParseReq cars = aliyunVINFactory.carVinQuery(vin);
		if(Func.isEmpty(cars)){
			cars = new CarsVinParseReq();
			cars.setPvin(vin);
			return R.data(201,cars,"未识别到车辆信息");
		}
		if(Func.isEmpty(cars.getPvin())){
			cars.setPvin(vin);
			return R.data(201,cars,"未识别到车辆信息");
		}
		return R.data(cars);

	}

	@PostMapping("/saveCar")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "发布(编辑)车辆", notes = "传入cars")
	public R saveCar(@Valid @RequestBody Cars cars) {

		wVinServeFactory.isCheckVin(cars.getPvin());
		if(Func.isEmpty(cars.getVest())){
			return R.fail("车源所属不能为空");
		}
		if(Func.isEmpty(cars.getOfficialprice())){
			return R.fail("官方指导价不能为空");
		}
		if(Func.isEmpty(cars.getPprice())){
			return R.fail("零售价不能为空");
		}
		Cars car=new Cars();
		car.setPvin(cars.getPvin());
		car.setIsDeleted(0);
		Integer count= carsService.count(Condition.getQueryWrapper(car));

		//如果是新增
		if(Func.isEmpty(cars.getId()) && count>0) {
			return R.fail("该车已经存在，不能在发布了");
		}



		Member cl = wMemberFactory.getMember(request);

		//个人车源
		if(Func.equals(cars.getVest(),1)){
			cars.setProvince(cl.getProvince());
			cars.setProvinceName(cl.getProvinceName());
			cars.setCity(cl.getCity());
			cars.setCityName(cl.getCityName());
			cars.setCounty(cl.getCounty());
			cars.setCountyName(cl.getCountyName());
		}

		//门店车源
		if(Func.equals(cars.getVest(),2)){

			if(Func.isEmpty(cars.getShopId())){
				return R.fail("门店id不能为空");
			}
			Shop shop= shopService.getById(cars.getShopId());
			if(Func.isEmpty(shop)){
				return R.fail("门店ID不存在");
			}

			Map<String,Object> map=new HashMap<>();
			map.put("staff_id",cl.getId());
			map.put("shop_id",cars.getShopId());
			ShopMemberRoleRightDTO  right = shopMemberService.getShopMemberRight(map);

			if(Func.isEmpty(right)){
				return R.fail("您不是该门店该门店的员工，不能发布车源哦");
			}
			if(!right.getIsPublishCar()){
				return R.fail("您没有该门店发布车源权限哦，去找店主开通吧");
			}

			cars.setProvince(shop.getProvince());
			Region pr= regionService.getById(shop.getProvince());
			if(Func.isNotEmpty(pr)) {
				cars.setProvinceName(pr.getProvinceName());
			}
			cars.setCity(shop.getCity());
			Region city= regionService.getById(shop.getCity());
			if(Func.isNotEmpty(city)) {
				cars.setCityName(city.getCityName());
			}
			cars.setCounty(shop.getCounty());
			Region co= regionService.getById(shop.getCounty());
			if(Func.isNotEmpty(co)) {
				cars.setCountyName(co.getTownName());
			}
		}


		cars.setMemberId(cl.getId());
		cars.setListtime(DateUtil.format(new Date(),DateUtil.PATTERN_DATETIME));
		if(Func.isEmpty(cars.getId())) {
			cars.setAuditStatus(AuditStatus.AUDITING.id);
			cars.setId(NumberUtil.getRandomNumber(1, 10));
		}
		else{
			Cars old=carsService.getById(cars.getId());
			cars.setAuditStatus(AuditStatus.AUDITING.id);
//审核通过后的车子，用户选择重新编辑，如果只改动了零售价、表显里程、年检到期、强险到期、
// 描述的其中一项或多项，而没有其他的项改动，是不需要后台重新审核的
			if(Func.equals(old.getAuditStatus(),AuditStatus.PASS.id)){
				if( Func.equals(old.getPmainpic(),cars.getPmainpic())//车源主图图片
						&& Func.equals(old.getPpics(),cars.getPpics())//图片
						&& Func.equals(old.getVest(),cars.getVest())//车源所属
						&& Func.equals(old.getPvin(),cars.getPvin())//车架号
						&& Func.equals(old.getBrandId(),cars.getBrandId())//品牌
						&& Func.equals(old.getSeriesId(),cars.getSeriesId())//车型
						&& Func.equals(old.getStylesId(),cars.getStylesId())//车系
						&& Func.equals(old.getOfficialprice(),cars.getOfficialprice())//官方指导价
						&& Func.equals(old.getPtradePrice(),cars.getPtradePrice())//批发价
						&& Func.equals(old.getPafprice(),cars.getPafprice())//内部价
						&& Func.equals(old.getPcostprice(),cars.getPcostprice())//成本价
						&& Func.equals(old.getModelId(),cars.getModelId())//车辆类型
						&& Func.equals(old.getPcolor(),cars.getPcolor())//颜色
						&& Func.equals(old.getPtransmission(),cars.getPtransmission())//变速箱
						&& Func.equals(old.getPgas(),cars.getPgas())//排量
						&& Func.equals(old.getPemission(),cars.getPemission())//排放标准
						&& Func.equals(old.getPfuel(),cars.getPfuel())//燃油类型
						&& Func.equals(old.getPontime(),cars.getPontime())//上牌时间
				){
					cars.setAuditStatus(AuditStatus.PASS.id);
				}

//				if(Func.equals(old.getPprice(),cars.getPprice())
//						&& Func.equals(old.getPkilometre(),cars.getPkilometre())
//						&& Func.equals(old.getPinsurance(),cars.getPinsurance())
//						&& Func.equals(old.getPinspection(),cars.getPinspection())
//				){
//
//				}


			}

			return R.status(carsService.updateById(cars));
		}
		return R.status(carsService.save(cars));
	}

	/**
	 * 车源详情
	 */
	@GetMapping("/carDetail")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "车源详情", notes = "传入cars")
	public R<CarsDTO> carDetail(Long carId) {

		CarsDTO carDetail=new CarsDTO();
		Cars cars = carsService.getById(carId);
		if(Func.isEmpty(cars)){
			throw new ServiceException("为获取到车源信息");
		}
		Member member= memberService.getById(cars.getMemberId());
		BeanUtils.copyProperties(cars,carDetail);
		carDetail.setCertificationLv(member.getCertificationLv());

		if(Func.isNotEmpty(member)){
			//个人车源
			if(Func.equals(cars.getVest(),1)){
				carDetail.setMemberName(member.getName());
				carDetail.setPhone1(member.getPhone());
				carDetail.setShopName(member.getCarDealer());
				carDetail.setShopAddress(member.getDealerAddress());
				carDetail.setLat(member.getLat());
				carDetail.setLng(member.getLng());
			}

			//门店车源
			if(Func.equals(cars.getVest(),2)){
				Shop shop= shopService.getById(cars.getShopId());
				if(Func.isNotEmpty(shop)) {

					carDetail.setMemberName(member.getName());
					carDetail.setPhone1(shop.getPhone1());
					carDetail.setPhone2(shop.getPhone2());
					carDetail.setPhone3(shop.getPhone3());
					carDetail.setPhone4(shop.getPhone4());
					carDetail.setPhone5(shop.getPhone5());
					carDetail.setShopName(shop.getShopName());
					carDetail.setShopAddress(shop.getShopAddress());
					carDetail.setLat(shop.getLat());
					carDetail.setLng(shop.getLng());
				}
			}
		}

		String openid = request.getHeader("openid");
		if(Func.isNotEmpty(openid)) {
			Member cl = wMemberFactory.getMember(request);
			Long memberId=cl.getId();
			CarsCollect carsCollect=new CarsCollect();
			carsCollect.setCarId(carId);
			carsCollect.setMemberId(memberId);
			CarsCollect entity=casCollectService.selectCarsCollect(carsCollect);
			if (Func.isNotEmpty(entity)){
				carDetail.setIsCollect(entity.getIsCollect());
			}
			//添加浏览记录
			CarsBrowse carsBrowse=new CarsBrowse();
			carsBrowse.setMemberId(memberId);
			carsBrowse.setCarId(carId);
			List<CarsBrowse> list =carsBrowseService.list(Condition.getQueryWrapper(carsBrowse));
			if(Func.isEmpty(list)){
				carsBrowseService.save(carsBrowse);
			}else{
				for(CarsBrowse cb:list){
					cb.setUpdateTime(LocalDateTime.now());
				}
				carsBrowseService.updateBatchById(list);
			}
		}

		return R.data(carDetail);
	}
	/**
	 *
	 */
	@PostMapping("/carCollect")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "车源收藏", notes = "传入carsCollect")
	public R collect(@Valid @RequestBody CarsCollect carsCollect) {
		Member cl = wMemberFactory.getMember(request);
		carsCollect.setMemberId(cl.getId());
		CarsCollect entity=casCollectService.selectCarsCollect(carsCollect);
		Boolean res=false;
		if(Func.isEmpty(entity)){
			res=casCollectService.save(carsCollect);
		}else {
			entity.setIsCollect(carsCollect.getIsCollect());
			res=casCollectService.updateById(entity);
		}

		return R.status(res);
	}

	@PostMapping("/memberCarsPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "会员车源分页", notes = "传入cars")
	public R<IPage<CarsDTO>> memberCarsPage(
			@ApiParam(value = "会员id") @RequestParam(value = "memberId", required = true) Long memberId,
			@ApiParam(value = "车源所属：1个人 2门店") @RequestParam(value = "vest", required = true) Integer vest,
										Query query) {
		CarsVO cars=new CarsVO();
		cars.setMemberId(memberId);
		cars.setVest(vest);
		cars.setStatus(1);
		cars.setAuditStatus(2);
		if (Func.isNotEmpty(cars.getSort())) {
			cars.setSort(CarSort.getValue(Integer.valueOf(cars.getSort())));
		} else {
			cars.setSort(CarSort.TIME.value);
		}

		IPage<CarsDTO> pages = carsService.selectCarsPage(Condition.getPage(query), cars);
		return R.data(pages);
	}


}
