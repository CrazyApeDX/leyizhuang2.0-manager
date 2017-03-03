package com.wechat;

import java.util.UUID;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.util.MD5;
import com.ynyes.lyz.util.SiteMagConstant;

public class WechatOrder {

	// 应用ID
	private String appid = "wx6ea338a1c0fc1978";
	// 商户号
	private String mch_id;
	// 随机字符串
	private String nonce_str = UUID.randomUUID().toString().replace("-", "").toUpperCase();
	// 签名
	private String sign;
	// 商品描述
	private String body;
	// 商户订单号
	private String out_trade_no;
	// 总金额
	private Integer total_fee;
	// 终端IP
	private String spbill_create_ip;
	// 异步通知地址
	private String notify_url = SiteMagConstant.wechatReturnUrlAsnyc;
	// 交易类型
	private String trade_type = "APP";

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public final WechatOrder CREATE(TdOrder order, String ip) {
		this.setBody(order.getOrderNumber().contains("CZ") ? "乐易装电子钱包充值" : "乐易装交易线上支付");
		this.setOut_trade_no(order.getOrderNumber());
		Double fen = order.getTotalPrice() * 100;
		String fee = fen.toString();
		fee = fee.substring(0, fee.indexOf("."));
		this.setTotal_fee(Integer.parseInt(fee));
		this.setSpbill_create_ip(ip);
		this.setSign(this.createSign());
		return this;
	}

	private String createSign() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("appid=").append(this.appid).append('&');
		buffer.append("mch_id=").append(this.mch_id).append('&');
		buffer.append("nonce_str=").append(this.nonce_str).append('&');
		buffer.append("body=").append(this.body).append('&');
		buffer.append("out_trade_no=").append(this.out_trade_no).append('&');
		buffer.append("total_fee=").append(this.total_fee).append('&');
		buffer.append("spbill_create_ip").append(this.spbill_create_ip).append('&');
		buffer.append("notify_url").append(this.notify_url).append('&');
		buffer.append("trade_type").append(this.trade_type).append('&');
		return MD5.md5(buffer.toString(), 32);
	}
}
