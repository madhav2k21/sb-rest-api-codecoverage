package com.techleads.app.exception;

public class ResourseNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public ResourseNotFoundException(String msg) {
		super(msg);
	}

	public ResourseNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
