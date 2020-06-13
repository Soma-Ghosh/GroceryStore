package com.grostory.exception;

import javax.xml.bind.annotation.XmlElementRef;

import com.grostory.bean.ExceptionMessage;

public class GrostoryException  extends Exception {

	private static final long serialVersionUID = 1666497646172567401L;
	
	private ExceptionMessage exceptionMessage;

	public GrostoryException() {
		super();
	}

	public GrostoryException(ExceptionMessage exceptionMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
	}

	@XmlElementRef
	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		//return super.fillInStackTrace();
		return this;
	}
}

