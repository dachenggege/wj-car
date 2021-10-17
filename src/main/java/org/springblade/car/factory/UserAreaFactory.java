package org.springblade.car.factory;

import org.springblade.car.Req.MemberReq;
import org.springblade.car.dto.MemberDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.Shop;
import org.springblade.car.service.IMemberService;
import org.springblade.car.service.IShopService;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.MemberVO;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author bond
 * @date 2021/4/28 17:45
 * @desc
 */
@Component
public class UserAreaFactory {

	@Autowired
	private BladeRedis bladeRedis;

 public MemberReq getUserAreas() {
	 MemberReq memberReq=new MemberReq();
	 List<String> areas= bladeRedis.get(CacheNames.AREAS_KEY+ AuthUtil.getUser().getUserId());
	 List<String> noareas= bladeRedis.get(CacheNames.NO_AREAS_KEY+AuthUtil.getUser().getUserId());
	 memberReq.setUserId(AuthUtil.getUser().getUserId());
	 memberReq.setNoareas(noareas);
	 memberReq.setAreas(areas);
	 return memberReq;
 }
}
