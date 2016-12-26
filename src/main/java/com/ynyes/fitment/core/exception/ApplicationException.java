package com.ynyes.fitment.core.exception;

/**
 * App公共异常
 * @author dengxiao
 */
public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String notice;

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public ApplicationException(String notice) {
		super();
		this.notice = notice;
	}

	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
