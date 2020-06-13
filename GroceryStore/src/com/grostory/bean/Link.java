package com.grostory.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="link")
public class Link 
{
	private String href,type;
	
	public Link() 
	{
		
	}
	
	public Link(String href, String type) 
	{
		this.href = href;
		this.type = type;
	}



	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
