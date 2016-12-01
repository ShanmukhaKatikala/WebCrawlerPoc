package com.wipro.poc.exception;

public final class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	private ServiceException() {
		super();
	}

	public ServiceException(String message) {
		this();
		this.message = message;
	}

	@Override
	public String toString() {
		return "ServiceException [message=" + message + "]";
	}

}
