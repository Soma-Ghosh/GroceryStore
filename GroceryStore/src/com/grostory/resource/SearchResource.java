package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.grostory.bean.Product;
import com.grostory.service.GrostoryService;

@Path("/search")
public class SearchResource
{
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{var}")
	public Set<Product> searchProducts(@PathParam("var") String var)
	{
		Set<Product> all_prod;
		System.out.println("search");
		all_prod=new GrostoryService().searchProducts(var);
		return all_prod;
	}

}
