package org.springblade.car.wx.WeixinPhone;

import lombok.Data;

/**
 * @author bond
 * @date 2021/8/28 16:00
 * @desc
 */
@Data
public class WeixinPhoneDecryptInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private int countryCode;
    private String weixinWaterMark;
    private WaterMark watermark;
}
