package org.springblade.car.Req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author bond
 * @date 2021/10/17 0:04
 * @desc
 */
@Data
public class CarsAuditReq {
    private List<String> areas;
    private List<String> noareas;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    @ApiModelProperty(value = "真实姓名")
    private String name;
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "区县")
    private String county;
    @ApiModelProperty(value = "车源审核状态1审核中,2审核通过，3审核不通过")
    private Integer personAuditStatus;
    @ApiModelProperty(value = "排序字段")
    private String sort="create_time";
    @ApiModelProperty(value = "升降序")
    private String mm="desc";
}
