package org.springblade.car.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.car.Req.*;
import org.springblade.car.dto.MemberCertificationDTO;
import org.springblade.car.entity.Member;
import org.springblade.car.entity.MemberCertification;
import org.springblade.car.factory.UserAreaFactory;
import org.springblade.car.service.IMemberCertificationService;
import org.springblade.car.service.IMemberService;
import org.springblade.car.vo.CarsVO;
import org.springblade.car.vo.MemberCertificationVO;
import org.springblade.car.vo.MemberVO;
import org.springblade.car.wx.factory.WMemberFactory;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bond
 * @date 2021/10/16 23:56
 * @desc
 */
@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/workbench")
@Api(value = "用户表", tags = "v2后台-工作台相关接口")
@ApiSort(2002)
public class WorkbenchController {
    private BladeRedis bladeRedis;
    private final IMemberService memberService;
    private UserAreaFactory userAreaFactory;
    private final IMemberCertificationService memberCertificationService;


    @GetMapping("/memberAuditPage")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "会员审核分页", notes = "传入member")
    public R<IPage<MemberVO>> memberAuditPage(MemberAuditReq member, Query query) {
        MemberReq memberReq=userAreaFactory.getUserAreas();

        BeanUtils.copyProperties(member,memberReq);
        //memberReq.setRoletype(1);
        List<Integer> personStatus=new ArrayList<>(3);
        personStatus.add(0,1);
        personStatus.add(1,2);
        personStatus.add(2,3);
        memberReq.setPersonStatus(personStatus);

        IPage<MemberVO> pages = memberService.selectMemberPage(Condition.getPage(query), memberReq);
        return R.data(pages);
    }
    @GetMapping("/memberAudit")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "会员审核", notes = "传入member")
    public R memberAudit(@ApiParam(value = "用户id") @RequestParam(value = "id", required = true) Long id,
                           @ApiParam(value = "状态") @RequestParam(value = "personAuditStatus", required = true) int personAuditStatus,
                           @ApiParam(value = "原因") @RequestParam(value = "personNopassnotice", required = false) String personNopassnotice) {
        // 会员审核状态1审核中,2审核通过，3审核不通过
        Member cl=memberService.getById(id);
        if(Func.isEmpty(cl)){
            return R.fail("用户ID不存在");
        }
        //通过
        if(Func.equals(personAuditStatus,2)){
            cl.setRoletype(2);
            cl.setPersonAuditStatus(personAuditStatus);
            cl.setPersonAuditTime(new Date());
            cl.setCertificationLv(0);
        }
        //不通过
        if(Func.equals(personAuditStatus,3)){
            cl.setPersonAuditStatus(personAuditStatus);
            cl.setPersonNopassnotice(personNopassnotice);
            cl.setPersonAuditTime(new Date());
        }
        bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+cl.getOpenid(),cl);
        return R.status(memberService.updateById(cl));
    }

    @GetMapping("/merchantAuditPage")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "商家审核分页", notes = "传入member")
    public R<IPage<MemberVO>> merchantAuditPage(MerchantAuditReq member, Query query) {
        MemberReq memberReq=userAreaFactory.getUserAreas();
        BeanUtils.copyProperties(member,memberReq);
       // memberReq.setRoletype(2);
        List<Integer> companyStatus=new ArrayList<>(3);
        companyStatus.add(0,1);
        companyStatus.add(1,2);
        companyStatus.add(2,3);
        memberReq.setCompanyStatus(companyStatus);
        IPage<MemberVO> pages = memberService.selectMemberPage(Condition.getPage(query), memberReq);
        return R.data(pages);
    }
    @GetMapping("/merchantAudit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "商家审核")
    public R merchantAudit(@ApiParam(value = "用户id") @RequestParam(value = "id", required = true) Long id,
                    @ApiParam(value = "状态") @RequestParam(value = "companyAuditStatus", required = true) int companyAuditStatus,
                    @ApiParam(value = "原因") @RequestParam(value = "companyNopassnotice", required = false) String companyNopassnotice) {
       // 商家会员审核状态1审核中,2审核通过，3审核不通过
        Member cl=memberService.getById(id);
        if(Func.isEmpty(cl)){
            return R.fail("商家ID不存在");
        }
        //通过
        if(Func.equals(companyAuditStatus,2)){
            cl.setRoletype(3);
            cl.setCompanyAuditStatus(companyAuditStatus);
            cl.setCompanyAuditTime(new Date());
            cl.setCertificationLv(0);
            cl.setMemberLv(0);
        }
        //不通过
        if(Func.equals(companyAuditStatus,3)){
            cl.setCompanyAuditStatus(companyAuditStatus);
            cl.setCompanyNopassnotice(companyNopassnotice);
            cl.setCompanyAuditTime(new Date());
        }
        bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+cl.getOpenid(),cl);
        return R.status(memberService.updateById(cl));
    }

    @GetMapping("/certificationPage")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "认证审核分页", notes = "certification")
    public R<IPage<MemberCertificationDTO>> certificationPage(MemberCertificationReq authentication, Query query) {
        MemberReq memberReq=userAreaFactory.getUserAreas();
        authentication.setAreas(memberReq.getAreas());
        authentication.setNoareas(memberReq.getNoareas());
        authentication.setUserId(memberReq.getUserId());
        IPage<MemberCertificationDTO> pages = memberCertificationService.selectMemberCertificationPage(Condition.getPage(query), authentication);
        return R.data(pages);
    }
    /**
     * 详情
     */
    @GetMapping("/authenticationDetail")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "认证详情", notes = "传入authentication")
    public R<MemberCertificationDTO> authenticationDetail(Long id) {
        MemberCertificationDTO detail = memberCertificationService.getAuthenticationDetail(id);
        return R.data(detail);
    }
    @GetMapping("/authenticationAudit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "认证审核")
    public R authenticationAudit(@ApiParam(value = "认证id") @RequestParam(value = "id", required = true) Long id,
                           @ApiParam(value = "状态") @RequestParam(value = "auditStatus", required = true) int auditStatus,
                           @ApiParam(value = "原因") @RequestParam(value = "nopassnotice", required = false) String nopassnotice) {

        MemberCertification memberCertification= memberCertificationService.getById(id);
        if(Func.isEmpty(memberCertification)){
            return R.fail("审核信息不存在");
        }

        //审核状态1审核中,2审核通过，3审核不通过
        Member cl=memberService.getById(memberCertification.getMemberId());
        if(Func.isEmpty(cl)){
            return R.fail("用户不存在");
        }
        //通过
        if(Func.equals(auditStatus,2)){
            cl.setCertificationLv(memberCertification.getLevel());
            memberCertification.setAuditStatus(auditStatus);
            memberCertification.setAuditTime(new Date());
            memberService.updateById(cl);
        }
        //不通过
        if(Func.equals(auditStatus,3)){
            memberCertification.setAuditStatus(auditStatus);
            memberCertification.setNopassnotice(nopassnotice);
            memberCertification.setAuditTime(new Date());
        }
        bladeRedis.set(CacheNames.MEMBER_OPENID_KEY+cl.getOpenid(),cl);
        return R.status(memberCertificationService.updateById(memberCertification));
    }

}
