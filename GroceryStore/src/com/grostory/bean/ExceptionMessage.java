package com.grostory.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="exception-message")
public class ExceptionMessage
{
	private String message;

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	public ExceptionMessage()
	{
		
	}

	public ExceptionMessage(String message) 
	{
		this.message = message;
	}


}
