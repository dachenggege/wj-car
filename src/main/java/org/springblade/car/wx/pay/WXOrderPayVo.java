package org.springblade.car.wx.pay;

import java.io.Serializable;

public class WXOrderPayVo implements Serializable{
	private static final long serialVersionUID = 1124436149713932782L;
	    private String body;// * 商品或支付单简要描述  Ipad mini  16G  白色
	    private String out_trade_no;//* 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
	    private String fee_type="CNY";// * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	    private String time_start;//* 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	    private String time_expire;//* 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	    private String product_id;// * trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	    private Integer total_fee;// * 订单总金额，只能为整数，详见支付金额
	    private String trade_type;//交易类型
	    private String pay_status;//支付状态
	    private String openid;

		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getOut_trade_no() {
			return out_trade_no;
		}
		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		public String getFee_type() {
			return fee_type;
		}
		public void setFee_type(String fee_type) {
			this.fee_type = fee_type;
		}
		public String getTime_start() {
			return time_start;
		}
		public void setTime_start(String time_start) {
			this.time_start = time_start;
		}
		public String getTime_expire() {
			return time_expire;
		}
		public void setTime_expire(String time_expire) {
			this.time_expire = time_expire;
		}
		public String getProduct_id() {
			return product_id;
		}
		public void setProduct_id(String product_id) {
			this.product_id = product_id;
		}
		public Integer getTotal_fee() {
			return total_fee;
		}
		public void setTotal_fee(Integer total_fee) {
			this.total_fee = total_fee;
		}
		public String getTrade_type() {
			return trade_type;
		}
		public void setTrade_type(String trade_type) {
			this.trade_type = trade_type;
		}
		public String getPay_status() {
			return pay_status;
		}
		public void setPay_status(String pay_status) {
			this.pay_status = pay_status;
		}






}
