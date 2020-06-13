package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.grostory.bean.Cart;
import com.grostory.bean.Order;
import com.grostory.service.GrostoryService;

public class UserOrderResource
{
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Order> fetchAllOrdersByUser(@PathParam("username")String username)
	{
		Set<Order> orders;
		System.out.println("get All oRders");
		orders=new GrostoryService().fetchAllOrdersByUser(username);
		return orders;
	}
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{orderId}")
	public Order fetchOrderByUser(@PathParam("username")String username,@PathParam("orderId")int orderId)
	{
		Order order;
		System.out.println("get Order");
		order=new GrostoryService().fetchOrderByUser(username,orderId);
		return order;
	}
	
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void orderNow(@PathParam("username")String username,Set<Cart> order)
	{
		System.out.println("order now");
		new GrostoryService().orderNow(username, order);
	}
}
