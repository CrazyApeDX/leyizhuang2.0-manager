package com.ynyes.lyz.excp;

/**
 * <p>标题：AppInvalidActionExcp.java</p>
 * <p>描述：App无效操作异常</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午3:09:45
 */
public class AppInvalidActionExcp extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppInvalidActionExcp(String message) {
		super(message);
	}
}
