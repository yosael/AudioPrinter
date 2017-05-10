package com.sv.audiomed.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class Conexion {
	
	protected static Connection cx; 	
	

	public static Connection conectar(){
		if(cx != null){
			return cx;
		}
		
		InputStream inputStream = Conexion.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		
		try{
			properties.load(inputStream);
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			System.out.println(driver);
			System.out.println(url);
			System.out.println(user);
			System.out.println(password);
			
			Class.forName(driver);
			
			cx = DriverManager.getConnection(url, user, password);
		}catch(Exception e){
			e.printStackTrace();
		}				
		return cx;
	}
	
	public static void desconectar(){
		if(cx == null){
			return;
		}
		
		try{
			cx.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
