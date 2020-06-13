package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.grostory.bean.Cart;
import com.grostory.bean.Product;
import com.grostory.service.GrostoryService;

public class UserCartResource 
{
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Cart> fetchCart(@PathParam("username")String username)
	{
		Set<Cart> products;
		System.out.println("get Cart");
		products=new GrostoryService().fetchCart(username);
		return products;
	}
	
	
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void addToCart(@PathParam("username")String username,Cart prod)
	{
		System.out.println("add to Cart"+ prod.getProduct().getProductId());
		new GrostoryService().addToCart(username,prod);
	}

	@DELETE
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteFromCart(@PathParam("username")String username,Product prod)
	{
		System.out.println("delete from cart");
		System.out.println(username + " "+prod.getProductId());
		new GrostoryService().deleteFromCart(username,prod);
	}
}
