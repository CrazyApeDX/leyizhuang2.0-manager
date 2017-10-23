package com.ynyes.lyz.excp;

/**
 * @title app并发异常
 * @describe 
 * @author Generation Road
 * @date 2017年9月26日
 */
public class AppConcurrentExcp extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AppConcurrentExcp(String message){
		super(message);
	}
}
