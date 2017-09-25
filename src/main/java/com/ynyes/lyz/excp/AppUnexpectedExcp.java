package com.ynyes.lyz.excp;

/**
 * <p>标题：AppUnexpectedExcp.java</p>
 * <p>描述：App意外情况异常（严重，可能涉及到安全问题）</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午3:11:26
 */
public class AppUnexpectedExcp extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AppUnexpectedExcp(String message) {
		super(message);
	}
}
