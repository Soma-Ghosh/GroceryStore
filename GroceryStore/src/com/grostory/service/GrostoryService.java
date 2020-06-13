package com.grostory.service;

import java.util.Set;

import com.grostory.bean.Admin;
import com.grostory.bean.Cart;
import com.grostory.bean.Customer;
import com.grostory.bean.Order;
import com.grostory.bean.Product;
import com.grostory.exception.UserNotFoundException;
import com.grostory.util.DataStore;

public class GrostoryService 
{

	public void addProduct(Product prod) 
	{
		new DataStore().addProduct(prod);
	}

	public Product fetchProduct(int prodID)
	{
		return new DataStore().fetchProduct(prodID);
	}

	public void removeProduct(int prodID)
	{
		new DataStore().removeProduct(prodID);
	}

	public void updateProduct(Product prod) 
	{
		new DataStore().updateProduct(prod);
	}

	public Set<Order> fetchAllOrders() 
	{
		return new DataStore().fetchAllOrders();
	}

	public Set<Customer> fetchAllCustomer() 
	{  
		return new DataStore().fetchAllCustomers();
	}

	public Set<Product> fetchAllProducts() 
	{  
		return new DataStore().fetchAllProducts();
	}

	public Order fetchOrder(int orderId) 
	{
		return new DataStore().fetchOrder(orderId);
	}

	public Customer fetchCustomer(String value) throws UserNotFoundException 
	{
		return new DataStore().fetchCustomer(value);
	}

	public Set<Product> fetchProductByCategory(String category) 
	{
		return new DataStore().fetchProductByCategory(category);
	}

	public Admin fetchAdmin(String email) throws UserNotFoundException
	{
		return new DataStore().fetchAdmin(email);
	}

	public void addAdmin(Admin admin)
	{
		new DataStore().addAdmin(admin);
	}

	public void addCustomer(Customer cust)
	{
		new DataStore().addCustomer(cust);
	}

	public Set<Cart> fetchCart(String username) 
	{
		return new DataStore().fetchCart(username);
	}

	public void addToCart(String username, Cart prod) 
	{
		new DataStore().addToCart(username,prod);
	}

	public void deleteFromCart(String username, Product prod) 
	{
		new DataStore().deleteFromCart(username,prod);
	}

	public Set<Product> fetchWishlist(String username) 
	{
		return new DataStore().fetchWishlist(username);
	}

	public void addToWishlist(String username, Product prod) 
	{
		new DataStore().addToWishlist(username,prod);
	}

	public void deleteFromWishlist(String username, Product prod) 
	{
		new DataStore().deleteFromWishlist(username,prod);
	}

	public Set<Order> fetchAllOrdersByUser(String username) 
	{
		return new DataStore().fetchAllOrdersByCustomer(username);
	}

	public Order fetchOrderByUser(String username, int orderId) 
	{
		return fetchOrder(orderId);
	}

	public void orderNow(String username,Set<Cart> order) 
	{
		new DataStore().orderNow(username, order);
	}

	public Set<Product> searchProducts(String var) 
	{
		return new DataStore().searchProducts(var);
	}
	
		
	
		

}
