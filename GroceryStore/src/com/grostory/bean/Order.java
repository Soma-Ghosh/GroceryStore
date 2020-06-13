package com.grostory.bean;

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.grostory.util.OrderStatus;

@XmlRootElement(name="order")
public class Order 
{

	private int orderId;
	private Date orderedOn;
	private Customer orderedBy;
	private OrderStatus status;
	private Set<Cart> orderedItems;
	
	public Order()
	{
		
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int i)
	{
		this.orderId = i;
	}


	public Date getOrderedOn()
	{
		return orderedOn;
	}

	public void setOrderedOn(Date orderedOn) 
	{
		this.orderedOn = orderedOn;
	}

	public Customer getOrderedBy()
	{
		return orderedBy;
	}

	public void setOrderedBy(Customer orderedBy) 
	{
		this.orderedBy = orderedBy;
	}

	public OrderStatus getStatus()
	{
		return status;
	}

	public void setStatus(OrderStatus status) 
	{
		this.status = status;
	}


	public Set<Cart> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(Set<Cart> orderedItems) {
		this.orderedItems = orderedItems;
	}
	
	
	

}
