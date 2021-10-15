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
public class carsBeenCallDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "车源ID")
    private Long carId;
    @ApiModelProperty(value = "车源名称")
    private String carName;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "咨询者id")
    private Long callerId;

    @ApiModelProperty(value = "咨询者")
    private String callerName;

    @ApiModelProperty(value = "咨询者电话")
    private String callerPhone;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("咨询时间")
    private Date callTime;

}
