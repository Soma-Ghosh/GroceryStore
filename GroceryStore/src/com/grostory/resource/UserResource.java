package com.grostory.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.grostory.bean.Customer;
import com.grostory.bean.ExceptionMessage;
import com.grostory.exception.UserNotFoundException;
import com.grostory.service.GrostoryService;


@Path("/users")
public class UserResource 
{

	@Context 
	private ResourceContext resourceContext;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{username}")  
	public Customer fetchUserDetails(@PathParam("username") String username) throws UserNotFoundException 
	{
		System.out.println("USER RESOURCE");
		Customer user;
		user=new GrostoryService().fetchCustomer(username);
		return user;
	}
	
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void addCustomer(Customer cust)
	{
		System.out.println("Add new Customer");
		new GrostoryService().addCustomer(cust);
	}

	@Path("{username}/{sub-resource-name}")
	public Object getSubResource(@PathParam("sub-resource-name")String subResourceName)
	{
		if("orders".equals(subResourceName))
		{
			return resourceContext.getResource(UserOrderResource.class);
		}
		else if("wishlists".equals(subResourceName))
		{
			return resourceContext.getResource(UserWishlistResource.class);
		}
		else if("carts".equals(subResourceName))
		{
			return resourceContext.getResource(UserCartResource.class);
		}
		throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
		.entity(new ExceptionMessage("No such resource")).build());
	}
}
