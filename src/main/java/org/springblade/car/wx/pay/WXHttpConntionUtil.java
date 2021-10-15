package org.springblade.car.wx.pay;

import org.springblade.car.entity.CommunityWx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WXHttpConntionUtil {
private static HttpURLConnection conn;

	public static HttpURLConnection buildConn() {
		try {

			//String unifiedorder_url=ConfigUtil.getWeiXin("unifiedorder_url");
			String unifiedorder_url=WXConfig.UNIFIEDORDER_URL;
			URL url = new URL(unifiedorder_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(100000);
			conn.setReadTimeout(100000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			conn.setRequestMethod("POST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static HttpURLConnection queryConn() {
		try {

			String orderquery_url=WXConfig.ORDERQUERY_URL;
			URL url = new URL(orderquery_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(100000);
			conn.setReadTimeout(100000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			conn.setRequestMethod("POST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 向微信发送订单信息
	 *
	 * @param xml
	 */
	public static void sendWXRequestXml(String xml) {
		try {
			OutputStream out = conn.getOutputStream();
			out.write(xml.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到微信的返回报文
	 * @return
	 */
	public static String getWXResponseXml() {
		String resultXml = null;
		InputStream in = null;
		BufferedReader read = null;
		try {
			in = conn.getInputStream();
			read = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String valueStr = null;
			StringBuffer bufferRes = new StringBuffer();
			while ((valueStr = read.readLine()) != null) {
				bufferRes.append(valueStr);
			}
			resultXml = bufferRes.toString();
			System.out.println("resultXml:"+resultXml);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				read.close();
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return resultXml;
	}

	public static Map<String,String> sendGZH(WXOrderPayVo orderPay, CommunityWx wx){
		buildConn();
		String requestXml=WXXmlUtil.buildWXOrderRequestXml(orderPay,wx);
		sendWXRequestXml(requestXml);
		String responseXml=getWXResponseXml();
		Map<String,String> resultMap=null;
		if(responseXml!=null){
			resultMap=new HashMap<String,String>();
			resultMap=WXXmlUtil.buildXmlToMap(responseXml);
		}
		return resultMap;
	}

	public static Map<String,String> sendNVT(WXOrderPayVo orderPay, CommunityWx wx){
		buildConn();
		String requestXml=WXXmlUtil.buildWXOrderNVTRequestXml(orderPay,wx);
		sendWXRequestXml(requestXml);
		String responseXml=getWXResponseXml();
		Map<String,String> resultMap=null;
		if(responseXml!=null){
			resultMap=new HashMap<String,String>();
			resultMap=WXXmlUtil.buildXmlToMap(responseXml);
		}
		return resultMap;
	}
	public static Map<String,String> queryOrder(String out_trade_no, CommunityWx wx){
		queryConn();
		String requestXml=WXXmlUtil.queryOrderRequestXml(out_trade_no,wx);
		sendWXRequestXml(requestXml);
		String responseXml=getWXResponseXml();
		Map<String,String> resultMap=null;
		if(responseXml!=null){
			resultMap=new HashMap<String,String>();
			resultMap=WXXmlUtil.buildXmlToMap(responseXml);
		}
		return resultMap;
	}
}
