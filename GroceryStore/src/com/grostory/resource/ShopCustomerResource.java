package com.grostory.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.grostory.bean.Customer;
import com.grostory.exception.UserNotFoundException;
import com.grostory.service.GrostoryService;


public class ShopCustomerResource 
{
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Set<Customer> fetchAllCustomers()
	{
		Set<Customer> customers;
		System.out.println("fetch All Customers");
		customers=new GrostoryService().fetchAllCustomer();
		return customers;
	}
	
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("{username}")
	public Customer fetchCustomer(@PathParam("username") String username) throws UserNotFoundException
	{
		Customer cust;
		System.out.println("fetch Customer");
		cust=new GrostoryService().fetchCustomer(username);
		return cust;
	}
}
