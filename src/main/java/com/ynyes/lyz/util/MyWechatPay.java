package com.ynyes.lyz.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;

import com.tenpay.util.WXUtil;

public class MyWechatPay {

	public static Map<String, String> weixinPrePay(String sn, BigDecimal totalAmount, HttpServletRequest request) {
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("appid", MyWechatUtil.APPID);
		parameterMap.put("mch_id", MyWechatUtil.MCH_ID);
		// parameterMap.put("nonce_str", MyWechatUtil.getRandomString(32));
		parameterMap.put("nonce_str", WXUtil.getNonceStr());
		if (sn.contains("XN")) {
			parameterMap.put("body", "乐易装交易线上支付");
		} else {
			parameterMap.put("body", "乐易装电子钱包充值");
		}
		parameterMap.put("out_trade_no", sn);
		parameterMap.put("fee_type", "CNY");
		BigDecimal total = totalAmount.multiply(new BigDecimal(100));
		DecimalFormat df = new DecimalFormat("0");
		parameterMap.put("total_fee", df.format(total));
		String ip = request.getRemoteAddr();
		ip = "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip) ? "127.0.0.1" : ip;
		parameterMap.put("spbill_create_ip", ip);
		parameterMap.put("notify_url", SiteMagConstant.wechatReturnUrlAsnyc);
		parameterMap.put("trade_type", "APP");
		String sign = MyWechatUtil.createSign("UTF-8", parameterMap);
		parameterMap.put("sign", sign);
		System.err.println(sign);
		String requestXML = MyWechatUtil.getRequestXml(parameterMap);
		System.err.println(requestXML);
		String result = MyWechatUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);
		System.err.println(result);
		Map<String, String> map = null;
		try {
			map = MyWechatUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
