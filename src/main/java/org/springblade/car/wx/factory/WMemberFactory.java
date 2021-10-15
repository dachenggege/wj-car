package org.springblade.car.wx.factory;

import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberRights;
import org.springblade.car.entity.Shop;
import org.springblade.car.service.IMemberCertificationService;
import org.springblade.car.service.IMemberRightsService;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IShopService;
import org.springblade.car.wx.pay.WXConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
