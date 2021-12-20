package org.springblade.car.schedule;

import org.springblade.car.Req.MemberReq;
import org.springblade.car.entity.Member;
import org.springblade.car.enums.PayStatus;
import org.springblade.car.enums.RoleType;
import org.springblade.car.service.IMemberService;
import org.springblade.car.vo.MemberVO;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author bond
 * @date 2021/9/13 10:55
 * @desc 到期会员处理
 */
@Component
public class ExpiryMemberJob {

	@Autowired
	private IMemberService memberService;
	@Autowired
	private BladeRedis bladeRedis;


	//到期会员处理
	//@Scheduled(cron = "0 */2 * * * ?")
	//每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void run() {
		MemberReq query =new MemberReq();
		query.setIsExpiry(false);
		query.setEndExpiryDate(new Date());
		List<MemberVO> memberList = memberService.selectMemberList(query);
		List<Member> list =new ArrayList<>();
		if(Func.isNotEmpty(memberList)){
			Member entity=new Member();
			for(Member member:memberList){
				BeanUtils.copyProperties(member,entity);
				entity.setMemberLv(0);//到期会员
				entity.setIsExpiry(true);
				list.add(entity);
				bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+entity.getOpenid(),entity);
			}
			memberService.updateBatchById(list);
		}
	}


}
