package com.grostory.util;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.grostory.exception.GrostoryExceptionMapper;
import com.grostory.resource.ProductResource;
import com.grostory.resource.SearchResource;
import com.grostory.resource.ShopResource;
import com.grostory.resource.UserResource;

@ApplicationPath("/home")
public class GrostoryApplication extends Application 
{
	@Override
	public Set<Class<?>> getClasses() 
	{
		System.out.println("Application");
		Set<Class<?>> classes;
		classes=new HashSet<Class<?>>();
		classes.add(UserResource.class);
		classes.add(ShopResource.class);
		classes.add(ProductResource.class);
		classes.add(GrostoryExceptionMapper.class);
		classes.add(SearchResource.class);
		return super.getClasses();
	}

}
