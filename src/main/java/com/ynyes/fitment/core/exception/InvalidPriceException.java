package com.ynyes.fitment.core.exception;

/**
 * 无效价格异常
 * @author dengxiao
 */
public class InvalidPriceException extends ApplicationException {

	public InvalidPriceException() {
		super();
	}

	public InvalidPriceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPriceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPriceException(String notice) {
		super(notice);
	}

	public InvalidPriceException(Throwable cause) {
		super(cause);
	}
}
