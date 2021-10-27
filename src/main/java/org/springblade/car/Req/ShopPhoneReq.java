package org.springblade.car.Req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopPhoneReq {
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;
    @JsonSerialize(
            using = ToStringSerializer.class
    )

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone1;
    @ApiModelProperty(value = "手机号码")
    private String phone2;
    @ApiModelProperty(value = "手机号码")
    private String phone3;
    @ApiModelProperty(value = "手机号码")
    private String phone4;
    @ApiModelProperty(value = "手机号码")
    private String phone5;
}
