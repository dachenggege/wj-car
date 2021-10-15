package org.springblade.car.wx.WeixinPhone;

import lombok.Data;

/**
 * @author bond
 * @date 2021/8/28 16:03
 * @desc
 */
@Data
public class WaterMark {
    private Long timestamp;// 时间戳做转换的时候，记得先乘以1000，再通过simpledateformat完成date类型转换
    private String appid;
}
