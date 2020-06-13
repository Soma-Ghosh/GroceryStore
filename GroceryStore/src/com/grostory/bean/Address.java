package com.grostory.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="address")
public class Address {

	private String location;
	private String city;
	private int pincode;
	private String state;
	
	public Address()
	{
		
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
