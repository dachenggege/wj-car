package org.springblade.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author bond
 * @date 2021/10/1 23:52
 * @desc
 */
@Data
public class carsBeenBrowseDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "车源ID")
    private Long carId;
    @ApiModelProperty(value = "车源名称")
    private String carName;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "浏览者id")
    private Long browserId;

    @ApiModelProperty(value = "浏览者")
    private String browserName;

    @ApiModelProperty(value = "浏览者电话")
    private String browserPhone;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("浏览时间")
    private Date browserTime;

}
