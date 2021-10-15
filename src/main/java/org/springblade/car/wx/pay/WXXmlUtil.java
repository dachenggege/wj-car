package org.springblade.car.wx.pay;

import org.jdom2.Element;
import org.springblade.car.entity.CommunityWx;
import org.springblade.car.wx.sdk.WXPayUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class WXXmlUtil {

	/**
	 * 构建统一订单报文方法公众号支付
	 *
	 * @param orderPay
	 * @return
	 */
	public static String buildWXOrderRequestXml(WXOrderPayVo orderPay, CommunityWx wx) {
		String xml = null;
		StringBuffer str = new StringBuffer();

		/**
		 * a,b,c,d,e,f,g || h,i,j,k,l,m,n || o,p,q,r,s,t || u,v,w,x,y,z ||
		 *
		 */
	//	String appid = WXConfig.appid;//微信商户公众账号ID;
		String appid =wx.getAppid();
		String body = orderPay.getBody();//订单说明
		String device_info="WEB";
/*		if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
			 device_info = "WEB";
		}*/
		String fee_type = orderPay.getFee_type();//币种
		//String mch_id = WXConfig.mch_id;//微信商户号
		String mch_id =wx.getMchId();
		String nonce_str = WXNonceStrUtil.getRandomStringByLength();// 随机码 znlb4i2ddh68w4l0ai7s2se29rfft4m
		//String notify_url = WXConfig.notify_url;
		String notify_url =wx.getNotifyUrl();
		String openid=orderPay.getOpenid();


		String out_trade_no = orderPay.getOut_trade_no();//商户订单号
		String product_id = orderPay.getProduct_id();//商品号
		String sign = "";// 签名
		//String spbill_create_ip =WXConfig.spbill_create_ip; //Native支付填调用微信支付API的机器IP
		String spbill_create_ip=wx.getSpbillCreateIp();
		String total_fee = String.valueOf(orderPay.getTotal_fee());//
		String trade_type = orderPay.getTrade_type();
		String time_start=orderPay.getTime_start();
		String time_expire=orderPay.getTime_expire();

		StringBuffer MD5str=new StringBuffer();
		MD5str.append("appid=").append(appid);
		MD5str.append("&body=").append(body);
		//if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
		MD5str.append("&device_info=").append(device_info);
		//}
		MD5str.append("&fee_type=").append(fee_type);
		MD5str.append("&mch_id=").append(mch_id);
		MD5str.append("&nonce_str=").append(nonce_str);
		MD5str.append("&notify_url=").append(notify_url);

		MD5str.append("&openid=").append(openid);

		MD5str.append("&out_trade_no=").append(out_trade_no);
		MD5str.append("&product_id=").append(product_id);
		MD5str.append("&spbill_create_ip=").append(spbill_create_ip);
		MD5str.append("&time_expire=").append(time_expire);
		MD5str.append("&time_start=").append(time_start);
		MD5str.append("&total_fee=").append(total_fee);
		MD5str.append("&trade_type=").append(trade_type);


		//String sign_key=WXConfig.sign_key;
		String sign_key=wx.getSignKey();
		StringBuffer signStr=new StringBuffer();
		signStr.append(MD5str).append("&key=").append(sign_key);
//		System.out.println("signStr:"+signStr.toString());
	     String signStr1=signStr.toString();
		sign=bulidMD5Str(signStr1);
//		System.out.println("nonce_str:"+nonce_str);
//		System.out.println("sign======:"+sign);



		str.append("<xml>");
		str.append("<appid>").append(appid).append("</appid>");
		str.append("<body>").append(body).append("</body>");
		//if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
			str.append("<device_info>").append(device_info).append("</device_info>");
		//}
		str.append("<fee_type>").append(fee_type).append("</fee_type>");
		str.append("<mch_id>").append(mch_id).append("</mch_id>");
		str.append("<nonce_str>").append(nonce_str).append("</nonce_str>");// 加密算法生成
		str.append("<notify_url>").append(notify_url).append("</notify_url>");
		str.append("<openid>").append(openid).append("</openid>");
		str.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		str.append("<product_id>").append(product_id).append("</product_id>");
		str.append("<spbill_create_ip>").append(spbill_create_ip).append("</spbill_create_ip>");
		str.append("<time_expire>").append(time_expire).append("</time_expire>");
		str.append("<time_start>").append(time_start).append("</time_start>");
		str.append("<total_fee>").append(total_fee).append("</total_fee>");
		str.append("<trade_type>").append(trade_type).append("</trade_type>");
		str.append("<sign>").append(sign).append("</sign>");// 将所有的参数加密MD5
		str.append("</xml>");
		xml = str.toString();
//		System.out.println("requestXml:" + xml);
		return xml;
	 }


	/**
	 * 构建统一订单报文方法  扫码支付
	 *
	 * @param orderPay
	 * @return
	 */
	public static String buildWXOrderNVTRequestXml(WXOrderPayVo orderPay, CommunityWx wx) {
		String xml = null;
		StringBuffer str = new StringBuffer();

		/**
		 * a,b,c,d,e,f,g || h,i,j,k,l,m,n || o,p,q,r,s,t || u,v,w,x,y,z ||
		 *
		 */
	//	String appid = WXConfig.appid;//微信商户公众账号ID;
		String appid =wx.getAppid();
		String body = orderPay.getBody();//订单说明
		String device_info="WEB";
/*		if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
			 device_info = "WEB";
		}*/
		String fee_type = orderPay.getFee_type();//币种
		//String mch_id = WXConfig.mch_id;//微信商户号
		String mch_id =wx.getMchId();
		String nonce_str = WXNonceStrUtil.getRandomStringByLength();// 随机码 znlb4i2ddh68w4l0ai7s2se29rfft4m
	//	String notify_url = WXConfig.notify_url;
		String notify_url =wx.getNotifyUrl();
		String out_trade_no = orderPay.getOut_trade_no();//商户订单号
		String product_id = orderPay.getProduct_id();//商品号
		String sign = "";// 签名
		//String spbill_create_ip =WXConfig.spbill_create_ip; //Native支付填调用微信支付API的机器IP
		String spbill_create_ip =wx.getSpbillCreateIp();
		String total_fee = String.valueOf(orderPay.getTotal_fee());//
		String trade_type = orderPay.getTrade_type();
		String time_start=orderPay.getTime_start();
		String time_expire=orderPay.getTime_expire();

		StringBuffer MD5str=new StringBuffer();
		MD5str.append("appid=").append(appid);
		MD5str.append("&body=").append(body);
		//if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
			MD5str.append("&device_info=").append(device_info);
		//}
		MD5str.append("&fee_type=").append(fee_type);
		MD5str.append("&mch_id=").append(mch_id);
		MD5str.append("&nonce_str=").append(nonce_str);
		MD5str.append("&notify_url=").append(notify_url);
		MD5str.append("&out_trade_no=").append(out_trade_no);
		MD5str.append("&product_id=").append(product_id);
		MD5str.append("&spbill_create_ip=").append(spbill_create_ip);
		MD5str.append("&time_expire=").append(time_expire);
		MD5str.append("&time_start=").append(time_start);
		MD5str.append("&total_fee=").append(total_fee);
		MD5str.append("&trade_type=").append(trade_type);


		//String sign_key=WXConfig.sign_key;
		String sign_key=wx.getSignKey();
		StringBuffer signStr=new StringBuffer();
		signStr.append(MD5str).append("&key=").append(sign_key);
//		System.out.println("signStr:"+signStr.toString());
	     String signStr1=signStr.toString();
		sign=bulidMD5Str(signStr1);
//		System.out.println("nonce_str:"+nonce_str);
//		System.out.println("sign======:"+sign);



		str.append("<xml>");
		str.append("<appid>").append(appid).append("</appid>");
		str.append("<body>").append(body).append("</body>");
		//if(StringUtils.equals("JSAPI", orderPay.getTrade_type())){//网页与公众号支付
			str.append("<device_info>").append(device_info).append("</device_info>");
		//}
		str.append("<fee_type>").append(fee_type).append("</fee_type>");
		str.append("<mch_id>").append(mch_id).append("</mch_id>");
		str.append("<nonce_str>").append(nonce_str).append("</nonce_str>");// 加密算法生成
		str.append("<notify_url>").append(notify_url).append("</notify_url>");
		str.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		str.append("<product_id>").append(product_id).append("</product_id>");
		str.append("<spbill_create_ip>").append(spbill_create_ip).append("</spbill_create_ip>");
		str.append("<time_expire>").append(time_expire).append("</time_expire>");
		str.append("<time_start>").append(time_start).append("</time_start>");
		str.append("<total_fee>").append(total_fee).append("</total_fee>");
		str.append("<trade_type>").append(trade_type).append("</trade_type>");
		str.append("<sign>").append(sign).append("</sign>");// 将所有的参数加密MD5
		str.append("</xml>");
		xml = str.toString();
//		System.out.println("requestXml:" + xml);
		return xml;
	 }

	/**
	 * 查询订单报文方法
	 *
	 * @param out_trade_no
	 * @return
	 */
	public static String queryOrderRequestXml(String out_trade_no, CommunityWx wx) {
		String xml = null;
		StringBuffer str = new StringBuffer();

		/**
		 * a,b,c,d,e,f,g || h,i,j,k,l,m,n || o,p,q,r,s,t || u,v,w,x,y,z ||
		 *
		 */
	//	String appid = WXConfig.appid;//微信商户公众账号ID;
		String appid =wx.getAppid();
		//String mch_id = WXConfig.mch_id;//微信商户号
		String mch_id =wx.getMchId();
		String nonce_str = WXNonceStrUtil.getRandomStringByLength();// 随机码 znlb4i2ddh68w4l0ai7s2se29rfft4m

		String sign = "";// 签名

		/*String MD5str = "appid=" + appid
				+ "&body=" + body
				+"&device_info="+device_info
				+ "&fee_type=" + fee_type
				+ "&mch_id=" + mch_id
				+ "&nonce_str=" + nonce_str
				+ "&notify_url=" + notify_url
				+ "&out_trade_no=" + out_trade_no
				+ "&product_id=" + product_id
				+ "&spbill_create_ip="+ spbill_create_ip
				+ "&time_expire="+time_expire
				+ "&time_start="+time_start
				+ "&total_fee=" + total_fee
				+ "&trade_type=" + trade_type;*/


		StringBuffer MD5str=new StringBuffer();
		MD5str.append("appid=").append(appid);
		MD5str.append("&mch_id=").append(mch_id);
		MD5str.append("&nonce_str=").append(nonce_str);
		MD5str.append("&out_trade_no=").append(out_trade_no);


		//String sign_key=WXConfig.sign_key;
		String sign_key=wx.getSignKey();
		StringBuffer signStr=new StringBuffer();
		signStr.append(MD5str).append("&key=").append(sign_key);
		System.out.println("signStr:"+signStr.toString());
	     String signStr1=signStr.toString();
		sign=bulidMD5Str(signStr1);
		System.out.println("nonce_str:"+nonce_str);
		System.out.println("sign======:"+sign);



		str.append("<xml>");
		str.append("<appid>").append(appid).append("</appid>");

		str.append("<mch_id>").append(mch_id).append("</mch_id>");
		str.append("<nonce_str>").append(nonce_str).append("</nonce_str>");// 加密算法生成
		str.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");

		str.append("<sign>").append(sign).append("</sign>");// 将所有的参数加密MD5
		str.append("</xml>");
		xml = str.toString();
//		System.out.println("requestXml:" + xml);
		return xml;
	 }

	public  static String bulidMD5Str(String key) {
			String isResult = null;
			isResult = MD5Util.MD5Encode(key,"UTF-8").toUpperCase();
			return isResult;
		}

		/**
		 * 解析返回报文,从Map中得到对应的内容
		 *
		 * @param resultXml
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static Map<String, String> buildXmlToMap(String resultXml) {
			try {
				return WXPayUtil.xmlToMap(resultXml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			/*Map<String, String> map = null;
			if (null == resultXml || "".equals(resultXml)) {
				return null;
			} else {
				try {
					map = new HashMap<String, String>();
					InputStream in = new ByteArrayInputStream(
							resultXml.getBytes("UTF-8"));
					SAXBuilder builder = new SAXBuilder();
					Document doc = builder.build(in);
					Element root = doc.getRootElement();
					List list = root.getChildren();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Element e = (Element) it.next();
						String k = e.getName();
						String v = "";
						List children = e.getChildren();
						if (children.isEmpty()) {
							v = e.getTextNormalize();
						} else {
							v = getChildrenText(children);
						}
						map.put(k, v);
					}
					// 关闭流
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return map;
			}*/

		}

		/**
		 * 获取子结点的xml
		 *
		 * @param children
		 * @return String
		 */
		@SuppressWarnings("rawtypes")
		public static String getChildrenText(List children) {
			StringBuffer sb = new StringBuffer();
			if (!children.isEmpty()) {
				Iterator it = children.iterator();
				while (it.hasNext()) {
					Element e = (Element) it.next();
					String name = e.getName();
					String value = e.getTextNormalize();
					List list = e.getChildren();
					sb.append("<" + name + ">");
					if (!list.isEmpty()) {
						sb.append(getChildrenText(list));
					}
					sb.append(value);
					sb.append("</" + name + ">");
				}
			}
			return sb.toString();
		}


}
