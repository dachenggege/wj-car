package org.springblade.car.wx.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bond
 * @date 2021/10/17 0:04
 * @desc
 */
@Data
public class MemberRightsPayRep {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "会员权益id")
    private Integer rightsId;

    @ApiModelProperty(value = "对应会员角色")
    private Integer roleType;

    @ApiModelProperty(value = "对应会员等级权限")
    private Integer memberLv;

    @ApiModelProperty(value = "支付金额")
    private Double payMoney;

    @ApiModelProperty(value = "标签，1升级，2续费")
    private int type;

    @ApiModelProperty(value = "等级描述")
    private String levelName;


}
