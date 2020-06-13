package com.grostory.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.grostory.bean.Address;
import com.grostory.bean.Admin;
import com.grostory.bean.Cart;
import com.grostory.bean.Customer;
import com.grostory.bean.ExceptionMessage;
import com.grostory.bean.Order;
import com.grostory.bean.Product;
import com.grostory.exception.OrderNotFoundException;
import com.grostory.exception.UserNotFoundException;

public class DataStore {
	static Connection conn;
	PreparedStatement ps;
	static
	{
		DatabaseConnection dc=new DatabaseConnection();
		try 
		{
			conn=dc.getConnection();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	//CUSTOMER
	
	public Set<Customer> fetchAllCustomers() 
	{
		Set<Customer> customers=new HashSet<Customer>();
		try
		{
			ps=conn.prepareStatement("select * from customers order by customer_id");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Customer cust=new Customer();
				
				cust.setCustomerId(rs.getInt("customer_id"));
				cust.setCustomerName(rs.getString("customer_name"));
				cust.setUsername(rs.getString("username"));
				cust.setEmail(rs.getString("email"));
				cust.setContact(rs.getString("contact"));
				cust.setPassword(rs.getString("password"));
				cust.setAddress(fetchAddress(cust.getCustomerId()));
				
				customers.add(cust);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return customers;
	}

	public void addCustomer(Customer cust) 
	{
		try
		{
			ps=conn.prepareStatement("insert into customers(customer_name, username, email, contact, password) values(?,?,?,?,?)");
			ps.setString(1, cust.getCustomerName());
			ps.setString(2, cust.getUsername());
			ps.setString(3, cust.getEmail());
			ps.setString(4, cust.getContact());
			ps.setString(5, cust.getPassword());
			ps.executeUpdate();
			
			addAddress(cust.getUsername(),cust.getAddress());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void addAddress(String username,Address address)
	{
		try
		{
			ps=conn.prepareStatement("select customer_id from customers  where username=? ");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			int customerId=0;
			if(rs.next())
			{
				customerId=rs.getInt("customer_id");
			}
			ps=conn.prepareStatement("insert into customer_address(customer_id,location,city,pincode,_state) values(?,?,?,?,?)");
			ps.setInt(1, customerId);
			ps.setString(2, address.getLocation());
			ps.setString(3, address.getCity());
			ps.setInt(4, address.getPincode());
			ps.setString(5, address.getState());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public Customer fetchCustomer(String username) throws UserNotFoundException 
	{
		Customer cust=new Customer();
		try
		{
			ps=conn.prepareStatement("select * from customers where username=?");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				cust.setCustomerId(rs.getInt("customer_id"));
				cust.setCustomerName(rs.getString("customer_name"));
				cust.setUsername(username);
				cust.setEmail(rs.getString("email"));
				cust.setContact(rs.getString("contact"));
				cust.setPassword(rs.getString("password"));
				cust.setAddress(fetchAddress(cust.getCustomerId()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new UserNotFoundException(new ExceptionMessage("No such user found"));
		}
		return cust;
	}

	//ADDRESS
	
	private Address fetchAddress(int customerId) 
	{
		Address ad=new Address();
		try
		{
			ps=conn.prepareStatement("select * from customer_address where customer_id=?");
			ps.setInt(1, customerId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				
				ad.setCity(rs.getString("city"));
				ad.setLocation(rs.getString("location"));
				ad.setPincode(rs.getInt("pincode"));
				ad.setState(rs.getString("_state"));
			
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return ad;
	}

	//ORDERS
	
	public Set<Order> fetchAllOrders() 
	{
		Set<Order> orders=new HashSet<Order>();
		try
		{
			ps=conn.prepareStatement("select * from orders order by order_id");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Order or=new Order();
				
				or.setOrderId(rs.getInt("order_id"));
				or.setOrderedBy(fetchCustomer(rs.getString("ordered_by")));
				or.setOrderedOn(rs.getDate("ordered_on"));
				or.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
				or.setOrderedItems(fetchOrderedItems(or.getOrderId()));
				
				orders.add(or);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;
	}

	private Set<Cart> fetchOrderedItems(int orderId) throws OrderNotFoundException 
	{
		Set<Cart> orderedItems=new HashSet<Cart>();
		try
		{
			ps=conn.prepareStatement("select * from ordered_items where order_id=? order by product_id");
			ps.setInt(1, orderId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Cart c=new Cart();
				
				c.setProduct(fetchProduct(rs.getInt("product_id")));
				c.setQuantity(rs.getInt("quantity"));
				
				orderedItems.add(c);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new OrderNotFoundException(new ExceptionMessage("No such order found"));
		}
		return orderedItems;
	}
	
	public Order fetchOrder(int orderId)
	{
		Order or=new Order();
		try
		{
			ps=conn.prepareStatement("select * from orders where order_id=?");
			ps.setInt(1, orderId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				or.setOrderId(orderId);
				or.setOrderedBy(fetchCustomer(rs.getString("ordered_by")));
				or.setOrderedOn(rs.getDate("ordered_on"));
				or.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
				or.setOrderedItems(fetchOrderedItems(or.getOrderId()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return or;
	}

	public Set<Order> fetchAllOrdersByCustomer(String username)
	{
		Set<Order> orders=new HashSet<Order>();
		try
		{
			ps=conn.prepareStatement("select * from orders where ordered_by=? order by order_id");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Order or=new Order();
				
				or.setOrderId(rs.getInt("order_id"));
				or.setOrderedBy(fetchCustomer(rs.getString("ordered_by")));
				or.setOrderedOn(rs.getDate("ordered_on"));
				or.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
				or.setOrderedItems(fetchOrderedItems(or.getOrderId()));
				
				orders.add(or);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;
	}
	
	//PRODUCT
	
	public Set<Product> fetchAllProducts() 
	{
		Set<Product> products=new HashSet<Product>();
		try
		{
			ps=conn.prepareStatement("select * from products order by product_id");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Product p=new Product();
				
				p.setProductId(rs.getInt("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setCategory(Category.valueOf(rs.getString("category")));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getInt("price"));
				p.setQuantityInStock(rs.getInt("quantity_in_stock"));
				p.setImage(rs.getString("image_location"));
				
				products.add(p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return products;
	}

	public void addProduct(Product prod) 
	{
		try
		{
			ps=conn.prepareStatement("insert into products(product_name, category, description, price, quantity_in_stock, image_location)"
					+ " values(?,?,?,?,?,?)");
			
			ps.setString(1,prod.getProductName());
			ps.setString(2, prod.getCategory().toString());
			ps.setString(3, prod.getDescription());
			ps.setInt(4, prod.getPrice());
			ps.setInt(5, prod.getQuantityInStock());
			ps.setString(6, prod.getImage());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Product fetchProduct(int prodId)
	{
		Product p=new Product();
		try
		{
			ps=conn.prepareStatement("select * from products where product_id=?");
			ps.setInt(1, prodId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				p.setProductId(rs.getInt("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setCategory(Category.valueOf(rs.getString("category")));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getInt("price"));
				p.setQuantityInStock(rs.getInt("quantity_in_stock"));
				p.setImage(rs.getString("image_location"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return p;
	}

	public void removeProduct(int prodId) 
	{
		try
		{
			ps=conn.prepareStatement("delete from products where product_id=?");
			ps.setInt(1, prodId);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateProduct(Product prod) 
	{
		try
		{
			ps=conn.prepareStatement("update products set product_name=?, category=?, description=?, price=?, quantity_in_stock=?,"
					+ " image_location=? where product_id=?");
			ps.setString(1, prod.getProductName());
			ps.setString(2, prod.getCategory().toString());
			ps.setString(3, prod.getDescription());
			ps.setInt(4, prod.getPrice());
			ps.setInt(5, prod.getQuantityInStock());
			ps.setString(6, prod.getImage());
			ps.setInt(7, prod.getProductId());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public Set<Product> fetchProductByCategory(String category) 
	{
		Set<Product> products=new HashSet<Product>();
		try
		{
			ps=conn.prepareStatement("select * from products where category=? order by product_id");
			ps.setString(1, category);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Product p=new Product();
				
				p.setProductId(rs.getInt("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setCategory(Category.valueOf(rs.getString("category")));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getInt("price"));
				p.setQuantityInStock(rs.getInt("quantity_in_stock"));
				p.setImage(rs.getString("image_location"));
				
				products.add(p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return products;
	}

	
	//ADMIN
	
	public Admin fetchAdmin(String username) throws UserNotFoundException
	{
		Admin ad=new Admin();
		try
		{
			ps=conn.prepareStatement("select * from admin_details where username=?");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				ad.setAdminId(rs.getInt("admin_id"));
				ad.setEmail(rs.getString("email"));
				ad.setUsername(username);
				ad.setPassword(rs.getString("password"));
			}
		}
		catch(Exception e)
		{
			throw new UserNotFoundException(new ExceptionMessage("No such user found"));
		}
		return ad;
	}

	public void addAdmin(Admin admin) 
	{
		try
		{
			ps=conn.prepareStatement("insert into admin_details (email,password,username) values(?,?,?);");
			ps.setString(1, admin.getEmail());
			ps.setString(2, admin.getPassword());
			ps.setString(3, admin.getUsername());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//WISHLIST
	
	public Set<Product> fetchWishlist(String username)
	{
		Set<Product> wishlist=new HashSet<Product>();
		try
		{
			ps=conn.prepareStatement("select product_id from wishlists where username=? order by product_id");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				wishlist.add(fetchProduct(rs.getInt("product_id")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return wishlist;
	}

	//CART
	
	public Set<Cart> fetchCart(String orderId)
	{
		Set<Cart> cart=new HashSet<Cart>();
		try
		{
			ps=conn.prepareStatement("select * from cart where username=? order by product_id");
			ps.setString(1, orderId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Cart c=new Cart();
				
				c.setProduct(fetchProduct(rs.getInt("product_id")));
				c.setQuantity(rs.getInt("quantity"));
				
				cart.add(c);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cart;
	}

	public void addToCart(String username, Cart prod) 
	{
		try
		{
			ps=conn.prepareStatement("insert into cart values(?,?,?)");
			ps.setString(1, username);
			ps.setInt(2, prod.getProduct().getProductId());
			ps.setInt(3, prod.getQuantity());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteFromCart(String username, Product prod) 
	{	
		try
		{
			ps=conn.prepareStatement("delete from cart where username=? and product_id=?");
			ps.setString(1, username);
			ps.setInt(2, prod.getProductId());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addToWishlist(String username, Product prod) 
	{
		try
		{
			ps=conn.prepareStatement("insert into wishlists values(?,?)");
			ps.setString(1, username);
			ps.setInt(2, prod.getProductId());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteFromWishlist(String username, Product prod) 
	{
		try
		{
			ps=conn.prepareStatement("delete from wishlists where username=? and product_id=?");
			ps.setString(1, username);
			ps.setInt(2, prod.getProductId());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void orderNow(String username,Set<Cart> order) 
	{
		try
		{
			ps=conn.prepareStatement("select max(order_id) from orders");
			ResultSet rs=ps.executeQuery();
			int orderId=0;
			if(rs.next())
			{
				orderId=rs.getInt("max(order_id)")+1;
			}
			
			ps=conn.prepareStatement("insert into orders(ordered_by,ordered_on,order_status) values(?,?,?)");
			ps.setString(1, username);
			ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
			ps.setString(3, OrderStatus.SHIPPED.toString());
			ps.executeUpdate();
			for(Cart c:order)
			{
				ps=conn.prepareStatement("insert into ordered_items(order_id,product_id,quantity) values(?,?,?)");
				ps.setInt(1,orderId);
				ps.setInt(2, c.getProduct().getProductId());
				ps.setInt(3, c.getQuantity());
				ps.executeUpdate();
			}
			ps=conn.prepareStatement("delete from cart where username=?");
			ps.setString(1, username);
			ps.executeUpdate();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Set<Product> searchProducts(String var)
	{
		Set<Product> prod=new HashSet<Product>();
		try
		{
			ps=conn.prepareStatement("select * from products where product_name like ?");
			ps.setString(1, var+"%");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Product p=new Product();
				
				p.setProductId(rs.getInt("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setCategory(Category.valueOf(rs.getString("category")));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getInt("price"));
				p.setQuantityInStock(rs.getInt("quantity_in_stock"));
				p.setImage(rs.getString("image_location"));
				
				prod.add(p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return prod;
	}


}
