package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sv.audiomed.model.FacturaAudiomed;

public class FacturaAudiomedDAO {
	
	
	private Connection cx;
	private Conexion cn;
	
	public FacturaAudiomedDAO()
	{
		cn = new Conexion();
		cx = cn.conectar();
	}
	
	
	public void agregar(FacturaAudiomed factura)
	{
		
		try {
			
			String sql ="Insert into FacturaAudiomed values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setString(1, factura.getCodigoFactura());
			preparedStatement.setString(2, factura.getNombreCliente());
			preparedStatement.setString(3, factura.getDireccionCliente());
			Date sqlDate = new Date(factura.getFecha().getTime());
			
			preparedStatement.setDate(4, sqlDate);
			preparedStatement.setString(5,factura.getDocCliente());
			preparedStatement.setFloat(6, factura.getSumaNoSujetas());
			preparedStatement.setFloat(7, factura.getSumaVentasExentas());
			preparedStatement.setFloat(8, factura.getSumaVentasGravadas());
			preparedStatement.setFloat(9,factura.getVentasExentas());
			preparedStatement.setFloat(10, factura.getVentasNoSujetas());
			preparedStatement.setFloat(11, factura.getSubtotal());
			preparedStatement.setFloat(12, factura.getIvaRetenido());
			preparedStatement.setFloat(13, factura.getVentaTotal());
			preparedStatement.setString(14, factura.getLetrasMonto());
			
			preparedStatement.execute();
			preparedStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
		
	}
	
	public List<FacturaAudiomed> buscarTodos()
	{
		List<FacturaAudiomed> lista = new ArrayList<FacturaAudiomed>();
		
		try {
			
			String sql="SELECT * FROM Factura_audiomed";
			Statement st = cx.createStatement();
			ResultSet result = st.executeQuery(sql);
			
			while(result.next())
			{
				FacturaAudiomed factura = new FacturaAudiomed();
				factura.setIdFactura(result.getInt(0));
				factura.setCodigoFactura(result.getString(1));
				
				lista.add(factura);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

}
