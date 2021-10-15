package org.springblade.car.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author bond
 * @date 2021/9/23 18:23
 * @desc
 */
@Data
public class CarsVinParseReq {
    @ApiModelProperty(value = "车架号")
    private String pvin;


    /**
     * 品牌id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "品牌id")
    private Long brandId;
    /**
     * 车系id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "车系id（对应t_series表id）")
    private Long seriesId;
    /**
     * 车型id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "车型id(对应t_styles表id)")
    private Long stylesId;

    @ApiModelProperty(value = "品牌名字")
    private String	brandName;
    @ApiModelProperty(value = "车系名字")
    private String	seriesName;
    @ApiModelProperty(value = "车型名字")
    private String	stylesName;
    @ApiModelProperty(value = "车级别名字")
    private String	modelName;
    /**
     * 车辆全称
     */
    @ApiModelProperty(value = "车辆全称")
    private String pallname;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal pprice;
    /**
     * 级别id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "级别id")
    private Long modelId;
    /**
     * 排量
     */
    @ApiModelProperty(value = "排量")
    private String pgas;
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String pcolor;
    /**
     * 变速箱
     */
    @ApiModelProperty(value = "变速箱")
    private String ptransmission;
    /**
     * 排放标准
     */
    @ApiModelProperty(value = "排放标准")
    private String pemission;
    /**
     * 燃油类型
     */
    @ApiModelProperty(value = "燃油类型")
    private String pfuel;
}
