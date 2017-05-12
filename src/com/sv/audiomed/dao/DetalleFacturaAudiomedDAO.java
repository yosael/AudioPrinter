package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sv.audiomed.model.DetalleFacturaAudiomed;

public class DetalleFacturaAudiomedDAO {
	
	Connection cx;
	Conexion cn;
	
	public DetalleFacturaAudiomedDAO()
	{
		cn = new Conexion();
		cx = cn.conectar();
	}
	
	public void agregar(DetalleFacturaAudiomed detalle)
	{
		try {
			
			String sql ="Insert into detalle_factura_audiomed values(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setInt(1, detalle.getIdFactura());
			preparedStatement.setInt(2,detalle.getCantidad());
			preparedStatement.setString(3, detalle.getDescripciones());
			preparedStatement.setDouble(4, detalle.getPrecioUnitario());
			preparedStatement.setDouble(5,detalle.getVentasNoSujetas());
			preparedStatement.setDouble(6, detalle.getVentasExentas());
			preparedStatement.setDouble(7, detalle.getVentasGravadas());
			
			preparedStatement.execute();
			
			preparedStatement.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
	}
	
	public void agregarDetalles(List<DetalleFacturaAudiomed> lista,int idFactura) throws SQLException
	{
		
		
		try {
			
			cx.setAutoCommit(false);
			
			for(DetalleFacturaAudiomed detalle:lista)
			{
				
				String sql ="Insert into detalle_factura_audiomed values(?,?,?,?,?,?,?)";
				PreparedStatement preparedStatement = cx.prepareStatement(sql);
				preparedStatement.setInt(1, idFactura);
				preparedStatement.setInt(2,detalle.getCantidad());
				preparedStatement.setString(3, detalle.getDescripciones());
				preparedStatement.setDouble(4, detalle.getPrecioUnitario());
				preparedStatement.setDouble(5,detalle.getVentasNoSujetas());
				preparedStatement.setDouble(6, detalle.getVentasExentas());
				preparedStatement.setDouble(7, detalle.getVentasGravadas());
				
				preparedStatement.executeUpdate();
				
			}
			
			cx.commit();
			
			
		} catch (Exception e) {

			cx.rollback();
		}
		
	}

}
