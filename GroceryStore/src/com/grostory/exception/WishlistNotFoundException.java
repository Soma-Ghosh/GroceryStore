package com.grostory.exception;

import com.grostory.bean.ExceptionMessage;

public class WishlistNotFoundException extends GrostoryException {

	private static final long serialVersionUID = -1857472482798126451L;
	public WishlistNotFoundException() {
		super();
	}

	public WishlistNotFoundException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}
}
