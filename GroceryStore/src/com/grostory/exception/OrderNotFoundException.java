package com.grostory.exception;

import com.grostory.bean.ExceptionMessage;

public class OrderNotFoundException extends GrostoryException {
	private static final long serialVersionUID = 4174143153994349652L;

	public OrderNotFoundException() {
		super();
	}

	public OrderNotFoundException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}

}
