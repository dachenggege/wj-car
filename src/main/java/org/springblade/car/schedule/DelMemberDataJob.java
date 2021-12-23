package org.springblade.car.schedule;

import org.springblade.car.Req.MemberReq;
import org.springblade.car.entity.Member;
import org.springblade.car.service.IMemberService;
import org.springblade.car.vo.MemberVO;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bond
 * @date 2021/9/13 10:55
 * @desc 处理没用的数据
 */
@Component
public class DelMemberDataJob {

	@Autowired
	private IMemberService memberService;
	@Autowired
	private BladeRedis bladeRedis;


	//处理没用的数据
	//@Scheduled(cron = "0 */2 * * * ?")
	//每天5分钟点执行一次
	@Scheduled(cron = "*/5 * * * * ?")
	public void run() {
		memberService.delCars();
		memberService.delCarsBrowse();
		memberService.delCarsCollect();
		memberService.delForun();
		memberService.delForunComment();
		memberService.delForunLike();
		memberService.delMemberCertification();
		memberService.delMemberfans();
		memberService.delPhoneRecord();
		memberService.delShop();
		memberService.delShopMember();
		memberService.delShopAllied();
	}


}
