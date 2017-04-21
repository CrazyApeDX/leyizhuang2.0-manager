package com.common.oss.exception;

public class ImageClientException extends Exception {
	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
