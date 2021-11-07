package org.springblade.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bond
 * @date 2021/10/14 11:04
 * @desc
 */
@Data
public class MerchantRegist {
    /**
     * 用户个人资料填写的省份
     */
    @ApiModelProperty(value = "用户个人资料填写的省份")
    @NotNull
    private String province;
    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    @NotNull
    private String provinceName;
    /**
     * 用户个人资料填写的城市
     */
    @ApiModelProperty(value = "用户个人资料填写的城市")
    @NotNull
    private String city;
    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    @NotNull
    private String cityName;
    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    @NotNull
    private String county;
    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    @NotNull
    private String countyName;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    @NotNull
    private String name;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotNull
    private String phone;
    /**
     * 车行名称
     */
    @ApiModelProperty(value = "车行名称")
    @NotNull
    private String carDealer;
    /**
     * 车行地址
     */
    @ApiModelProperty(value = "车行地址")
    @NotNull
    private String dealerAddress;

    @ApiModelProperty(value = "车行经度")
    private Double lng;

    @ApiModelProperty(value = "车行维度")
    private Double lat;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @NotNull
    private String company;
    /**
     * 法人
     */
    @ApiModelProperty(value = "法人")
    @NotNull
    private String corporate;
    /**
     * 公司证件照多个用英文逗号分隔
     */
    @ApiModelProperty(value = "公司证件照多个用英文逗号分隔")
    @NotNull
    private String companyCertificate;
}
