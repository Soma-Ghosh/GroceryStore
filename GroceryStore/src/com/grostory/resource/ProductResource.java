package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.grostory.bean.Product;
import com.grostory.service.GrostoryService;

@Path("/products")
public class ProductResource 
{
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Product> fetchAllProducts()
	{
		Set<Product> all_prod;
		System.out.println("fetch All products");
		all_prod=new GrostoryService().fetchAllProducts();
		return all_prod;
	}
	
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{category}/{prodID}")
	public Response fetchProduct(@PathParam("prodID")int prodID)
	{
		Product prod;
		System.out.println("fetch product");
		prod=new GrostoryService().fetchProduct(prodID);
		return Response.ok(prod).build();
	}
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{category}")
	public Set<Product> fetchProductByCategory(@PathParam("category")String category)
	{
		Set<Product> prod;
		System.out.println("fetch product by Category");
		prod=new GrostoryService().fetchProductByCategory(category);
		return prod;
	}

}
