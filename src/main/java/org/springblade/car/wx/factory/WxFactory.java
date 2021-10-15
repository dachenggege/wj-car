package org.springblade.car.wx.factory;

import org.springblade.car.wx.pay.WXConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author bond
 * @date 2021/4/28 17:45
 * @desc
 */
@Component
public class WxFactory {
	@Autowired
	private RestTemplate restTemplate;
	public  String code2Session(String code) {

		//String url= ;
		StringBuffer url = new StringBuffer();
		url.append(WXConfig.CODE2SESSION).append("?&appid=").append(WXConfig.appid)
			.append("&secret=").append(WXConfig.AppSecret)
			.append("&js_code=").append(code)
		.append("&grant_type=authorization_code");

		//GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
		String res = restTemplate.getForEntity(url.toString(), String.class).getBody();
		System.out.println(res);
		return res;
	}
}
