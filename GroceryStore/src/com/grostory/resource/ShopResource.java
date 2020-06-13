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

import com.grostory.bean.Admin;
import com.grostory.bean.ExceptionMessage;
import com.grostory.exception.UserNotFoundException;
import com.grostory.service.GrostoryService;

@Path("/shops")
public class ShopResource 
{
	@Context 
	private ResourceContext resourceContext;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{uname}")  
	public Response fetchAdmin(@PathParam("uname") String username) throws UserNotFoundException
	{
		System.out.println("Fetch admin");
		
		Admin admin;
		
		admin=new GrostoryService().fetchAdmin(username);
		
		
		return Response.ok(admin).build(); 
	}
	
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void addAdmin(Admin admin)
	{
		new GrostoryService().addAdmin(admin);
	}

	@Path("{uname}/{sub-resource-name}")
	public Object getSubResource(@PathParam("sub-resource-name")String subResourceName)
	{
		if("orders".equals(subResourceName))
		{
			return resourceContext.getResource(ShopOrderResource.class);
		}
		else if("products".equals(subResourceName))
		{
			return resourceContext.getResource(ShopProductResource.class);
		}
		else if("customers".equals(subResourceName))
		{
			return resourceContext.getResource(ShopCustomerResource.class);
		}
		throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
		.entity(new ExceptionMessage("No such resource")).build());
	}

}
