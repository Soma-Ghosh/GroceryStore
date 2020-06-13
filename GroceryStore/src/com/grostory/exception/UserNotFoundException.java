package com.grostory.exception;

import com.grostory.bean.ExceptionMessage;

public class UserNotFoundException extends GrostoryException {
	private static final long serialVersionUID = -8726563052769816274L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}
}
