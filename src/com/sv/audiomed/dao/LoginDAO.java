package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
	
	private Connection cx;
	
	public LoginDAO()
	{
		cx = ConexionAudioSoft.conectar();
	}
	
	public Integer validarUsuario(String username,String pass) throws SQLException,Exception
	{
		
		
		String sql="SELECT id FROM usuario where nombre_usuario=? and pass=?";
		Integer idUsuario = null;
		
		try {
			
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, pass);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				idUsuario = rs.getInt("id");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return idUsuario;
	}

}
