package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.grostory.bean.Product;
import com.grostory.service.GrostoryService;

public class UserWishlistResource 
{
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Product> fetchWishlist(@PathParam("username")String username)
	{
		Set<Product> products;
		System.out.println("get Wishlist");
		products=new GrostoryService().fetchWishlist(username);
		return products;
	}
	
	
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void addToWishlist(@PathParam("username")String username,Product prod)
	{
		System.out.println("add to wishlist");
		new GrostoryService().addToWishlist(username,prod);
	}

	@DELETE
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteFromWishlist(@PathParam("username")String username,Product prod)
	{
		System.out.println("delete from wishlist");
		new GrostoryService().deleteFromWishlist(username,prod);
	}
}
