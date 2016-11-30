package com.ynyes.lyz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.icu.text.DecimalFormat;

public class Utils {
	
	/**
	 * 检查是否为null 
	 * @param dou 要检查的值
	 * @return 如果为null,返回0.0其他情况直接返回
	 * @author zp
	 */
	public static Double checkNull(Double dou){
		if(dou==null){
			dou=0.0;
		}
		return dou;
	}

	/**
	 * 预存款加密
	 * @param dou 加密前的预存款
	 * @return 加密后的字符串
	 */
	public static String encryptionBalance(Double dou){
		DecimalFormat df = new DecimalFormat("#.00");
		String res="td";
		if(dou!=null && dou!=0.0){
			if(dou%2==1){
				res+="1";
				dou=99999-dou;
			}else{
				res+="2";
				dou=9999-dou;
			}
			res+=df.format(dou).toString();
		}
		return res;
	}
	
	/**
	 * 预存款解密
	 * @param str 加密后的字符串
	 * @return 加密前的预存款
	 */
	public static Double decryptionBalance(String str){
		Double res=0.0;
		try {
			if(str!=null && !"td".equals(str)){
				String type=str.substring(2, 3);
				res=Double.valueOf(str.substring(3, str.length()));
				if("1".equals(type)){
					res=99999-res;
				}else if("2".equals(type)){
					res=9999-res;
				}else{
					res=0.0;
				}
			}
		} catch (Exception e) {
			System.out.println("解密"+str+"出错");
			res=0.0;
		}
		return res;
	}
	
	/**
	 * 获取系统开始时间 2016-01-01
	 * @return
	 * @throws ParseException
	 */
	public static Date getSysStartDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse("2016-01-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
			
		}
	}
}
