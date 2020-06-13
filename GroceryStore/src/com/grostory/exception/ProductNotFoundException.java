package com.grostory.exception;

import com.grostory.bean.ExceptionMessage;

public class ProductNotFoundException extends GrostoryException
{
	private static final long serialVersionUID = -4490882794182328862L;

	public ProductNotFoundException() {
		super();
	}

	public ProductNotFoundException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}

}
