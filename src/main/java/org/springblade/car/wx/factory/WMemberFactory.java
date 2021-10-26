package org.springblade.car.wx.factory;

import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.*;
import org.springblade.car.service.*;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bond
 * @date 2021/4/28 17:45
 * @desc
 */
@Component
public class WMemberFactory {

	@Autowired
	private BladeRedis bladeRedis;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private  IMemberRightsService memberRightsService;
	@Autowired
	private IMemberFansService memberFansService;
	@Autowired
	private ICarsService carsService;
	@Autowired
	private  IMemberCertificationService memberCertificationService;
	@Autowired
	private  IShopService shopService;


	public MemberDTO getMember(HttpServletRequest request) {
		MemberDTO memberDTO =new MemberDTO();
		Member cl =new Member();
		String openid = request.getHeader("openid");
		if (Func.isEmpty(openid)) {
			throw new ServiceException("为获取到用户信息");
		}
		cl = bladeRedis.get(openid);
		if (Func.isEmpty(cl)) {
		Member client = new Member();
		client.setOpenid(openid);
		 cl = memberService.getOne(Condition.getQueryWrapper(client));
			bladeRedis.set(cl.getOpenid(),cl);
		}
		if (Func.isEmpty(cl)) {
			throw new ServiceException("为获取到用户信息");
		}
		MemberRights qrights =new MemberRights();
		qrights.setRoletype(cl.getRoletype());
		qrights.setLevel(cl.getMemberLv());
		MemberRights rights =memberRightsService.getOne(Condition.getQueryWrapper(qrights));
		if (Func.isEmpty(rights)) {
			throw new ServiceException("为获取到用户权益信息");
		}
		BeanUtils.copyProperties(cl,memberDTO);

		//会员认证
		MemberCertification memberCertification=new MemberCertification();
		memberCertification.setMemberId(cl.getId());
		memberCertification.setRoletype(cl.getRoletype());
		MemberCertification certification=memberCertificationService.getOne(Condition.getQueryWrapper(memberCertification));
		if(Func.isNotEmpty(certification)){
			memberDTO.setCertificationLv(certification.getLevel());
		}
		//粉丝数量
		MemberFans fans=new MemberFans();
		fans.setMemberId(cl.getId());
		int fansNum= memberFansService.count(Condition.getQueryWrapper(fans));
		//关注数量
		MemberFans focus=new MemberFans();
		focus.setFansId(cl.getId());
		int focusNum= memberFansService.count(Condition.getQueryWrapper(focus));

		//我的车源数量
		Cars myCar=new Cars();
		myCar.setMemberId(cl.getId());
		int mycarNum= carsService.count(Condition.getQueryWrapper(myCar));

		//我的车源数量
		Cars myShopCar=new Cars();
		myShopCar.setMemberId(cl.getId());
		int myShopcarNum= carsService.count(Condition.getQueryWrapper(myShopCar));

		//门店数
		Shop queryshop =new  Shop();
		queryshop.setMemberId(cl.getId());
		Integer shopCount= shopService.count(Condition.getQueryWrapper(queryshop));


		memberDTO.setFansNum(fansNum);
		memberDTO.setFocusNum(focusNum);
		memberDTO.setMyCarNum(mycarNum);
		memberDTO.setMyShopCarNum(myShopcarNum);
		memberDTO.setMyShopNum(shopCount);
		memberDTO.setRights(rights);
		return memberDTO;
	}

	public MemberDTO getMemberByid(Long id) {
		MemberDTO memberDTO =new MemberDTO();
		Member cl =memberService.getById(id);

		if (Func.isEmpty(cl)) {
			throw new ServiceException("为获取到用户信息");
		}
		MemberRights qrights =new MemberRights();
		qrights.setRoletype(cl.getRoletype());
		qrights.setLevel(cl.getMemberLv());
		MemberRights rights =memberRightsService.getOne(Condition.getQueryWrapper(qrights));
		if (Func.isEmpty(rights)) {
			throw new ServiceException("为获取到用户权益信息");
		}
		BeanUtils.copyProperties(cl,memberDTO);

		//会员认证
		MemberCertification memberCertification=new MemberCertification();
		memberCertification.setMemberId(cl.getId());
		memberCertification.setRoletype(cl.getRoletype());
		memberCertification.setAuditStatus(2);
		MemberCertification certification=memberCertificationService.getOne(Condition.getQueryWrapper(memberCertification));
		if(Func.isNotEmpty(certification)){
			memberDTO.setCertificationLv(certification.getLevel());
		}
		//粉丝数量
		MemberFans fans=new MemberFans();
		fans.setMemberId(cl.getId());
		int fansNum= memberFansService.count(Condition.getQueryWrapper(fans));
		//关注数量
		MemberFans focus=new MemberFans();
		focus.setFansId(cl.getId());
		int focusNum= memberFansService.count(Condition.getQueryWrapper(focus));

		//我的车源数量
		Cars myCar=new Cars();
		myCar.setMemberId(cl.getId());
		int mycarNum= carsService.count(Condition.getQueryWrapper(myCar));

		//我的车源数量
		Cars myShopCar=new Cars();
		myShopCar.setMemberId(cl.getId());
		int myShopcarNum= carsService.count(Condition.getQueryWrapper(myShopCar));

		memberDTO.setFansNum(fansNum);
		memberDTO.setFocusNum(focusNum);
		memberDTO.setMyCarNum(mycarNum);
		memberDTO.setMyShopCarNum(myShopcarNum);
		memberDTO.setRights(rights);
		return memberDTO;
	}

	public MemberDTO getMemberRights(Member cl){
		MemberDTO dto = new MemberDTO();
		MemberRights qrights =new MemberRights();
		qrights.setRoletype(cl.getRoletype());
		qrights.setLevel(cl.getMemberLv());
		MemberRights rights =memberRightsService.getOne(Condition.getQueryWrapper(qrights));
		if (Func.isEmpty(rights)) {
			throw new ServiceException("为获取到用户权益信息");
		}
		BeanUtils.copyProperties(cl, dto);
		dto.setRights(rights);
		return dto;
	}

}
