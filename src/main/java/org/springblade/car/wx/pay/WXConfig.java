package org.springblade.car.wx.pay;

import java.io.Serializable;


public class WXConfig implements Serializable {

	private static final long serialVersionUID = 7222554356603425654L;

	//接口获得临时登录凭证 auth.code2Session
	public static final String CODE2SESSION="https://api.weixin.qq.com/sns/jscode2session";
	//统一下订单
	public final static String UNIFIEDORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	//查询订单
	public final static String ORDERQUERY_URL="https://api.mch.weixin.qq.com/pay/orderquery";


	//万家车源小程序 应用APPID
	public final static String appid="wxaa5b37d7ee9e126a";
	//脉联 微信支付商户号
	//public final static String mch_id="1553857021";
	//public final static String mch_id="1514755721";
	//脉联 api密钥
	//public final static String sign_key="mailianzhihuichaobiao0755QAZWSXE";
	//public final static String sign_key="longnanyihaotechandianwww1007008";

	//支付回调地址
	//public final static String notify_url="http://www.longnan1.com/ml-amr/amr/wx/notify";
	//服务器公网ip
	//public final static String spbill_create_ip="47.105.34.190";
	//小程序 appSecret
	public final static String AppSecret="ff3cb326625c5bf1f22ac551c8901238";

}
