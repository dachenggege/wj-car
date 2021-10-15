package org.springblade.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author bond
 * @date 2021/9/23 13;42
 * @desc
 */
@Data
public class VinVehicle {
        @ApiModelProperty(value = "品牌名称")
        private String brandName	;//	品牌名称
        @ApiModelProperty(value = "动力类型")
        private String powerType	;//	动力类型
        @ApiModelProperty(value = "车组编码")
        private String groupCode	;//	车组编码
        @ApiModelProperty(value = "车组名称")
        private String groupName	;//	车组名称
        @ApiModelProperty(value = "车型代码，唯一标识")
        private String vehicleId	;//	车型代码，唯一标识
        @ApiModelProperty(value = "车型名称")
        private String vehicleName	;//	车型名称
        @ApiModelProperty(value = "国产/进口")
        private String importFlag	;//	国产/进口
        @ApiModelProperty(value = "车型俗称")
        private String standardName	;//	车型俗称
        @ApiModelProperty(value = "排量")
        private String displacement	;//	排量
        @ApiModelProperty(value = "吨位")
        private String tonnage	;//	吨位
        @ApiModelProperty(value = "年款")
        private String yearPattern	;//	年款
        @ApiModelProperty(value = "出厂日期")
        private String uploadDate	;//	出厂日期
        @ApiModelProperty(value = "公告号")
        private String standardname2	;//	公告号
        @ApiModelProperty(value = "新车购置价")
        private String purchasePrice	;//	新车购置价
        @ApiModelProperty(value = "变速箱类型")
        private String gearboxType	;//	变速箱类型
        @ApiModelProperty(value = "供油方式")
        private String supplyOil	;//	供油方式
        @ApiModelProperty(value = "燃油喷射形式")
        private String fuelJetType	;//	燃油喷射形式
        @ApiModelProperty(value = "发动机型号")
        private String engineModel	;//	发动机型号
        @ApiModelProperty(value = "驱动形式")
        private String drivenType	;//	驱动形式
        @ApiModelProperty(value = "备注")
        private String remark	;//	备注
        @ApiModelProperty(value = "是否有更多配置")
        private String hasConfig	;//	是否有更多配置
        @ApiModelProperty(value = "厂商指导价")
        private String listPrice	;//	厂商指导价
        @ApiModelProperty(value = "车系名称")
        private String familyName	;//	车系名称
        @ApiModelProperty(value = "座位数")
        private String seat	;//	座位数
        @ApiModelProperty(value = "车辆类型")
        private String clzl	;//	车辆类型
        @ApiModelProperty(value = "配置等级")
        private String cfgLevel	;//	配置等级
        @ApiModelProperty(value = "上市年份")
        private String marketDate	;//	上市年份
        @ApiModelProperty(value = "外形尺寸")
        private String vehicleSize	;//	外形尺寸
        @ApiModelProperty(value = "轴距")
        private String wheelbase	;//	轴距
        @ApiModelProperty(value = "变速器档数")
        private String gearNum	;//	变速器档数
        @ApiModelProperty(value = "整备质量（千克）")
        private String fullWeight	;//	整备质量（千克）
        @ApiModelProperty(value = "功率")
        private String power	;//	功率
        @ApiModelProperty(value = "车身结构")
        private String bodyType	;//	车身结构
        @ApiModelProperty(value = "排放标准")
        private String effluentStandard	;//	排放标准
        @ApiModelProperty(value = "车身颜色")
        private String vehicleColor	;//	车身颜色
        @ApiModelProperty(value = "是否商用车")
        private String vehlsSeri	;//	是否商用车(不一定有值)
        @ApiModelProperty(value = "别名")
        private String vehicleAlias	;
}
