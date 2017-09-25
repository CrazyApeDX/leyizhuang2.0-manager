package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088121476147007";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	// 商户的私钥
	public static String private_key = "MIICXgIBAAKBgQCzubUDZZ50LpnSywKI6hmSc44EM53afHhGopVhLuzavBnTLanY6eXA6LEvW4XlNsHB8Rf7N36zhq8RCIKgD3HFlBqnHND5kME/IaGBBK7LUpV1mqnLXnLo45zbmWxJ2hWirWe3mn0iPeae2otbufYRAGQsHf8vrx7fl7ms4+m/bwIDAQABAoGAQVKiJBXvPbMiXadDyJetq7ksSQKpwRu0a5f0S1pO5EGx8TuLnDeHNlJc9CWDyQfZ7K/AU6Kb/oxZXQuGzr5OTzEbuWQN2FTp65EtCYzJHQgalfgLmzcfeiG3PwW0Tq7MIpd0tFPI8D4U9mko8myDZJ3LDyQhccoFJqqAEgBFAAECQQDsKG53YTLUlUCxXZchtrOXFd7ZHDeznWyYoPGWfQBMD+myFRrMTq6xpRgoXkWBYa29qBaKLfVQ8l+OIn8hWcABAkEAwtN1DFxXUhf0sJIJ2dOpUsf10dQ3QSNIQ2c0+WX53NWEUxIsmT+Pi1cZ9CvYNw4059v43pGrZFUpEwTLp79/bwJBALHY5RA8f/Y+jKDGNcnq+KbAjrn8mSpIVP9AvY+yhaXCmiFF6Y+RddhM17uW+0srj5dz3b2kTMnwLewxqIIqQAECQQCMSmiGthfK7aUtYHvndxr+mS7SIdaH+TgYEUF8NYpvEvFuQDANSk+7LsVppgjT3E8MhsH2wz5/yTMClvlvmd3RAkEA4PYmjM5LpWws2epSRX4l2ueGKX5DkrF8Z4gjjhosSzGFchzTuv8YRdvw566blo+rBaZA5lhOvckhTokYWX17Cw==";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
