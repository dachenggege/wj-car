package org.springblade.car.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bond
 * @date 2021/8/29 15:50
 * @desc
 */
@Data
public class ShopCarReq {

    /**
     * 门店id
     */
    @ApiModelProperty(value = "门店id")
    @NotNull
    private Long shopId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 省份id
     */
    @ApiModelProperty(value = "省份id")
    private String province;
    /**
     * 城市id
     */
    @ApiModelProperty(value = "城市id")
    private String city;
    /**
     * 区县id
     */
    @ApiModelProperty(value = "区县id")
    private String county;

    @ApiModelProperty(value = "车名称")
    private String pallname;


    @ApiModelProperty(value = "审核状态1审核中,2审核通过，3审核不通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "上架状态状态0下架，1上架")
    private Integer status;



}
