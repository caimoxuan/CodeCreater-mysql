package com.pro.test.mappercreater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


public class MySqlConnection {
	
	private MySqlConnection(){}
	
	private static String path = "jdbc:mysql://192.168.1.200:3306/cloudnetdb";
	private static String user = "root";
	private static String pass = "cloudnetcom123";
	private static Connection conn = null;
	
	private static Map<String, Object> configMap;
	
	
	public static void init(Map<String, Object> map){
		configMap = map;
	}

	public static Connection getConnection(){
		
		if(conn != null){
			return conn;
		}else{
			
			try {
				if(configMap.get("jdbcDriver") != null){
					Class.forName(configMap.get("jdbcDriver").toString());
				}else{
					Class.forName("com.mysql.jdbc.Driver");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				if(configMap.get("url") != null && configMap.get("password") != null && configMap.get("username") != null){
					conn = DriverManager.getConnection(configMap.get("url").toString(), configMap.get("username").toString(), configMap.get("password").toString());
				}else{
					conn = DriverManager.getConnection(path, user, pass);
				}
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
