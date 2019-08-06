package com.it.erpapp.systemservices.exceptions;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionMessage;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BusinessException(String exceptionMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
	}
	
	public BusinessException() {
		super();
	}
	
}
