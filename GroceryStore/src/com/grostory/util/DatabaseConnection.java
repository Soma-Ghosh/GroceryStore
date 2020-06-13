package com.grostory.util;

import java.sql.*;

public class DatabaseConnection implements DatabaseConnectionManager
{
	private Connection conn;
	static
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery_store?useSSL=false", "root", "");
		return conn;
	}

	@Override
	public void closeConnection() throws SQLException 
	{
		conn.close();
		
	}
}
