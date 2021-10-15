package org.springblade.car.wx.pay;

import java.util.Random;

/**
 * 生成随机数
 * @author Suker
 * 2016-07-08
 */
public class WXNonceStrUtil {
	 public static String getRandomStringByLength() {
	        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	        Random random = new Random();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < 31; i++) {
	            int number = random.nextInt(base.length());
	            sb.append(base.charAt(number));
	        }
	        return sb.toString();
	    }
}
