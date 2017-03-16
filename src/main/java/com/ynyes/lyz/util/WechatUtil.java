package com.ynyes.lyz.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tenpay.AccessTokenRequestHandler;
import com.tenpay.ClientRequestHandler;
import com.tenpay.PackageRequestHandler;
import com.tenpay.PrepayIdRequestHandler;
import com.tenpay.util.ConstantUtil;
import com.tenpay.util.WXUtil;
import com.ynyes.lyz.entity.TdOrder;

public class WechatUtil {
	
	private final String appid = "wx6ea338a1c0fc1978";
	private final String mch_id = "1446016602";
	private final String notify_url = SiteMagConstant.wechatReturnUrlAsnyc;

	public String wechatSend(TdOrder order, HttpServletRequest request, HttpServletResponse response) {
		PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类 
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
		packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);

		int retcode;
		String retmsg = "";
		String xml_body = "";
		//获取token值 
		String token = AccessTokenRequestHandler.getAccessToken();
		if (!"".equals(token)) {
			//设置package订单参数
			packageReqHandler.setParameter("appid", this.appid);//银行渠道
			packageReqHandler.setParameter("body", "乐易装线上支付"); //商品描述   
			packageReqHandler.setParameter("notify_url", this.notify_url); //接收财付通通知的URL  
			packageReqHandler.setParameter("out_trade_no", order.getOrderNumber()); //商家订单号   
			packageReqHandler.setParameter("total_fee", order.getTotalPrice() * 100 +""); //商品金额,以分为单位  
			packageReqHandler.setParameter("spbill_create_ip",request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP  

//			//获取package包
//			String packageValue = null;
//			try {
//				packageValue = packageReqHandler.getRequestURL();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}

			String noncestr = WXUtil.getNonceStr();
			String timestamp = WXUtil.getTimeStamp();
			////设置获取prepayid支付参数
			prepayReqHandler.setParameter("appid", this.appid);
//			prepayReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
			prepayReqHandler.setParameter("noncestr", noncestr);
//			prepayReqHandler.setParameter("package", packageValue);
//			prepayReqHandler.setParameter("timestamp", timestamp);
//			prepayReqHandler.setParameter("traceid", traceid);

			//生成获取预支付签名
			String sign = prepayReqHandler.createSHA1Sign();
			//增加非参与签名的额外参数
			prepayReqHandler.setParameter("sign", sign);
			prepayReqHandler.setParameter("sign_method",
					ConstantUtil.SIGN_METHOD);
			String gateUrl = ConstantUtil.GATEURL + token;
			prepayReqHandler.setGateUrl(gateUrl);

			//获取prepayId
			String prepayid = prepayReqHandler.sendPrepay();
			System.err.println(prepayid);
			//吐回给客户端的参数
			if (null != prepayid && !"".equals(prepayid)) {
				//输出参数列表
				clientHandler.setParameter("appid", ConstantUtil.APP_ID);
				clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
				clientHandler.setParameter("noncestr", noncestr);
//				clientHandler.setParameter("package", "Sign=" + packageValue);
				clientHandler.setParameter("package", "Sign=WXPay");
				clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
				clientHandler.setParameter("prepayid", prepayid);
				clientHandler.setParameter("timestamp", timestamp);
				//生成签名
				sign = clientHandler.createSHA1Sign();
				clientHandler.setParameter("sign", sign);

				xml_body = clientHandler.getXmlBody();
				retcode = 0;
				retmsg = "OK";
			} else {
				retcode = -2;
				retmsg = "错误：获取prepayId失败";
			}
		} else {
			retcode = -1;
			retmsg = "错误：获取不到Token";
		}
		System.err.println(retcode);
		System.err.println(retmsg);
		System.err.println(xml_body);
		return "";
	}
}
