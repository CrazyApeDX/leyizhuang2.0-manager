package com.ynyes.fitment.core.exception;

/**
 * 未定义的条件异常 
 * @author dengxiao
 */
public class UndefinedConditionException extends ApplicationException {

	public UndefinedConditionException() {
		super();
	}

	public UndefinedConditionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UndefinedConditionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UndefinedConditionException(String notice) {
		super(notice);
	}

	public UndefinedConditionException(Throwable cause) {
		super(cause);
	}
}
