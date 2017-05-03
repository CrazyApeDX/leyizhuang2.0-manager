package com.ynyes.lyz.util;

/**
 * 后台常用常量
 * 
 * @author Sharon
 *
 */
public class SiteMagConstant {

	public static final int pageSize = 20;

	public static final String templatePath = "src/main/resources/templates/client/";

	// public static final String backupPath = "src/main/resources/backup/";
	// public static final String imagePath =
	// "src/main/resources/static/images";

	// public static final String backupPath = "/root/backup/";
	// public static final String imagePath = "/root/images/";

	public static String backupPath;
	public static String imagePath;
	public static String alipayReturnUrl;
	public static String alipayReturnUrlAsnyc;
	public static String wechatReturnUrl;
	public static String wechatReturnUrlAsnyc;

//	static {
//		String ip;
//		try {
//			ip = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e) {
//			ip = "localhost";
//			e.printStackTrace();
//		}
//		if (ip.contains("10.27.190.171")) {
//			backupPath = "/mnt/root/backup/";
//			imagePath = "/mnt/root/images/goods";
//			alipayReturnUrl = "http://www.leyizhuang.com.cn/pay/alipay/return";
//			alipayReturnUrlAsnyc = "http://www.leyizhuang.com.cn/pay/alipay/return/async";
//			wechatReturnUrlAsnyc = "http://www.leyizhuang.com.cn/pay/wechat/return/async";
//		} else {
//			backupPath = "src/main/resources/backup/";
//			imagePath = "src/main/resources/static/images";
//			alipayReturnUrl = "http://test.leyizhuang.com.cn/pay/alipay/return";
//			alipayReturnUrlAsnyc = "http://test.leyizhuang.com.cn/pay/alipay/return/async";
//			wechatReturnUrlAsnyc = "http://test.leyizhuang.com.cn/pay/wechat/return/async";
//		}
//	}
}