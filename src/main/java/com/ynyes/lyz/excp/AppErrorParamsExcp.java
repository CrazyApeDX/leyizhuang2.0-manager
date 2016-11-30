package com.ynyes.lyz.excp;

/**
 * <p>标题：AppErrorParamsExcp.java</p>
 * <p>描述：参数不正确异常</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午3:08:50
 */
public class AppErrorParamsExcp extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppErrorParamsExcp(String message) {
		super(message);
	}
}
