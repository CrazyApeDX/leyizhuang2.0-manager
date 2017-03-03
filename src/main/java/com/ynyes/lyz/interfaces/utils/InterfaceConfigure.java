package com.ynyes.lyz.interfaces.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InterfaceConfigure 
{
	/*
	 * 根据不同的服务器注释不同的部分
	 */
	
	/*-----测试环境 start----*/
	
	
	/**
	 * 在测试服务器抛给WMS的WEBSERVICE接口地址
	 */
	public static String WMS_WS_URL = null;
	
	/**
	 * 在测试服务器抛给EBS的WEBSERVICE接口地址
	 */
	public static String EBS_WS_URL = null;
	
	/**
	 * 在测试环境的微信回调地址
	 */
	public static String WX_NOTIFY_RETURN_URL = null;
	
	static {
		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			System.err.print("===================================================================");
			System.err.println("服务器IP地址：" + ip + "===================================================================");
			if (ip.contains("10.27.190.171")) {//10.27.190.171
				WMS_WS_URL = "http://120.76.23.226:8999/WmsInterServer.asmx?wsdl";
				EBS_WS_URL = "http://erpap.zghuarun.com:8008/webservices/SOAProvider/plsql/cux_app_webservice_pkg/?wsdl";
				WX_NOTIFY_RETURN_URL = "http://101.200.128.65:8080/pay/wx_notify";
				System.err.print("===================================================================");
				System.err.println("WMS环境：正式环境===================================================================");
				System.err.print("===================================================================");
				System.err.println("EBS境：正式环境===================================================================");
			} else {
				WMS_WS_URL = "http://120.76.214.99:8199/WmsInterServer.asmx?wsdl";
				EBS_WS_URL = "http://erpap.zghuarun.com:8008/webservices/SOAProvider/plsql/cux_app_webservice_pkg/?wsdl";
				//EBS_WS_URL = "http://erptest.zghuarun.com:8030/webservices/SOAProvider/plsql/cux_app_webservice_pkg/?wsdl";
				//EBS_WS_URL = "http://erptest.zghuarun.com:8030/webservices/SOAProvider/plsql/cux_app_webservice_pkg/?wsdl";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/*-----正式环境 start----*/
	
//	public static String WMS_WS_URL = "http://101.200.75.73:8999/WmsInterServer.asmx?wsdl";
	
//	public static String EBS_WS_URL = "http://erpap.zghuarun.com:8008/webservices/SOAProvider/plsql/cux_app_webservice_pkg/?wsdl";
	
//	public static String WX_NOTIFY_RETURN_URL = "http://101.200.128.65:8080/pay/wx_notify";
	
	/*-----正式环境    end----*/
	
	
}
