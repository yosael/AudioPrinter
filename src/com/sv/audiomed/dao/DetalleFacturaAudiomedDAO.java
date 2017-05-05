package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
			preparedStatement.setFloat(4, detalle.getPrecioUnitario());
			preparedStatement.setFloat(5,detalle.getVentasNoSujetas());
			preparedStatement.setFloat(6, detalle.getVentasExentas());
			preparedStatement.setFloat(7, detalle.getVentasGravadas());
			
			preparedStatement.execute();
			
			preparedStatement.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
	}

}
