package com.contact.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtil {
	
	private static final String url = "jdbc:mysql://localhost/contactdb";
	private static final String username = "root";
	private static final String password = "Km@02121997";
	private static Connection con = null;
	
	private DbUtil() {}
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
