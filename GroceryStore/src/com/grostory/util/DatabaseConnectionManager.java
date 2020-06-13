package com.grostory.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionManager
{
	public Connection getConnection() throws SQLException;
	public void closeConnection() throws SQLException;

}
