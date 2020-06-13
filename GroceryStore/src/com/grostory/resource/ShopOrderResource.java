package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.grostory.bean.Order;
import com.grostory.service.GrostoryService;

public class ShopOrderResource 
{
	@Context 
	private ResourceContext resourceContext;
	
	@Context
	private UriInfo uriInfo;
	

	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Order> fetchAllOrders()
	{
		Set<Order> orders;
		System.out.println("fetch All Orders");
		orders=new GrostoryService().fetchAllOrders();
		return orders;
	}
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{order_id}")
	public Response fetchOrder(@PathParam("order_id")int orderId)
	{
		System.out.println("fetch Order");
		Order or=new GrostoryService().fetchOrder(orderId);
		return Response.ok(or).build();
	}
	
	
	

}
