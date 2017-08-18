package com.pro.test.mappercreater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySqlConnection {
	
	private MySqlConnection connection;
	
	private MySqlConnection(){}
	
	private static String path = "jdbc:mysql://192.168.1.200:3306/cloudnetdb";
	private static String user = "root";
	private static String pass = "cloudnetcom123";
	private static Connection conn = null;
	
	public static Connection getConnection(){
		
		if(conn != null){
			return conn;
		}else{
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				 conn = DriverManager.getConnection(path, user, pass);
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		return conn;
		
		
	}
	
	public static  void closeConnection()throws SQLException{
		if(conn != null){
			conn.close();
		}
	}
	
	

}
