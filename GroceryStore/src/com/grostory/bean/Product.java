package com.grostory.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.grostory.util.Category;

@XmlRootElement(name="product")
public class Product 
{
	private int quantityInStock,productId;
	private String productName,description;
	private Category category;
	private int price;
	private String image; 
	
	public Product()
	{
	
	}


	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String name)
	{
		this.productName = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getPrice() 
	{
		return price;
	}

	public void setPrice(int d) 
	{
		this.price = d;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantity) {
		this.quantityInStock = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

}
