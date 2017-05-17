package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sv.audiomed.model.DetalleFacturaDoctor;
import com.sv.audiomed.model.FacturaDoctor;

public class FacturaDoctorDAO {
	
	
	private Connection cx;
	
	public FacturaDoctorDAO()
	{
		cx = Conexion.conectar();
	}
	
	
	public void agregar(FacturaDoctor factura)
	{
		
		try {
			
			String sql ="Insert into factura_doctor values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setString(1, factura.getCodigoFactura());
			preparedStatement.setString(2, factura.getNombreCliente());
			preparedStatement.setString(3, factura.getDireccionCliente());
			Date sqlDate = new Date(factura.getFecha().getTime());
			
			preparedStatement.setDate(4, sqlDate);
			preparedStatement.setString(5,factura.getDocCliente()!=null?factura.getDocCliente():" ");
			preparedStatement.setString(6, factura.getVtaCtaDe()!=null?factura.getVtaCtaDe():" ");
			preparedStatement.setDouble(7, factura.getSumaNoSujetas());
			preparedStatement.setDouble(8, factura.getSumaVentasExentas());
			preparedStatement.setDouble(9, factura.getSumaVentasGravadas());
			preparedStatement.setDouble(10,factura.getVentasExentas());
			preparedStatement.setDouble(11, factura.getVentasNoSujetas());
			preparedStatement.setDouble(12, factura.getSubtotal());
			preparedStatement.setDouble(13, factura.getIvaRetenido());
			preparedStatement.setDouble(14, factura.getVentaTotal());
			preparedStatement.setString(15, factura.getLetrasMonto());
			
			preparedStatement.execute();
			preparedStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
		
	}
	
	public int agregarFacturaDetalle(FacturaDoctor factura,List<DetalleFacturaDoctor> detalles) throws SQLException
	{
		int idFactura=0;
		System.out.println("Entro a registro factura DAO");
		try {
				
			cx.setAutoCommit(false);
			ResultSet rsIdFactura;
			
			
			String sql ="insert into factura_doctor(codigo_factura,nombre_cliente,direccion_cliente,fecha,doc_cliente,vta_cta_de,suma_nosujetas,suma_ventas_exentas,suma_ventas_gravadas,ventas_exentas,ventas_nosujetas,subtotal,iva_retenido,venta_total,monto_letras) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, factura.getCodigoFactura());
			preparedStatement.setString(2, factura.getNombreCliente());
			preparedStatement.setString(3, factura.getDireccionCliente());
			
			Date sqlDate = new Date(factura.getFecha().getTime());
			preparedStatement.setDate(4, sqlDate);
			
			preparedStatement.setString(5,factura.getDocCliente()!=null?factura.getDocCliente():null);
			preparedStatement.setString(6, factura.getVtaCtaDe()!=null?factura.getVtaCtaDe():null);
			preparedStatement.setDouble(7, factura.getSumaNoSujetas()!=null?factura.getSumaNoSujetas():0f);
			preparedStatement.setDouble(8, factura.getSumaVentasExentas()!=null?factura.getSumaVentasExentas():0f);
			preparedStatement.setDouble(9, factura.getSumaVentasGravadas()!=null?factura.getSumaVentasGravadas():0f);
			preparedStatement.setDouble(10,factura.getVentasExentas()!=null?factura.getVentasExentas():0f);
			preparedStatement.setDouble(11, factura.getVentasNoSujetas()!=null?factura.getVentasNoSujetas():0f);
			preparedStatement.setDouble(12, factura.getSubtotal()!=null?factura.getSubtotal():0f);
			preparedStatement.setDouble(13, factura.getIvaRetenido()!=null?factura.getIvaRetenido():0f);
			preparedStatement.setDouble(14, factura.getVentaTotal()!=null?factura.getVentaTotal():0f);
			preparedStatement.setString(15, factura.getLetrasMonto()!=null?factura.getLetrasMonto():null);
			
			preparedStatement.executeUpdate();
			
			rsIdFactura = preparedStatement.getGeneratedKeys();
			while(rsIdFactura.next())
				idFactura = rsIdFactura.getInt(1);
			
			System.out.println("ID FACTURA DAO "+idFactura);
			
			preparedStatement.close();
			
			
			//Si da error extraer el idFactura
			
			if(detalles.size()>0 && idFactura>0)
			{
				System.out.println("Entro al detalle");
				for(DetalleFacturaDoctor detalle:detalles)
				{
					
					String sqlDetalle ="Insert into detalle_factura_doctor(id_factura,cantidad,descripciones,precio_unitario,ventas_nosujetas,ventas_exentas,ventas_gravadas) values(?,?,?,?,?,?,?)";
					PreparedStatement preparedStatementDetalle = cx.prepareStatement(sqlDetalle);
					preparedStatementDetalle.setInt(1, idFactura);
					preparedStatementDetalle.setInt(2,detalle.getCantidad());
					preparedStatementDetalle.setString(3, detalle.getDescripciones());
					preparedStatementDetalle.setDouble(4, detalle.getPrecioUnitario());
					preparedStatementDetalle.setDouble(5,detalle.getVentasNoSujetas()!=null?detalle.getVentasNoSujetas():0f);
					preparedStatementDetalle.setDouble(6, detalle.getVentasExentas()!=null?detalle.getVentasExentas():0f);
					preparedStatementDetalle.setDouble(7, detalle.getVentasGravadas()!=null?detalle.getVentasGravadas():0f);
					
					preparedStatementDetalle.executeUpdate();
					preparedStatementDetalle.close();
					
				}
			}
			else
			{
				cx.rollback();
				return 0;
			}
			
			
			cx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			cx.rollback();
			return 0;
			
		}
		
		return idFactura;
		
	}
	
	public List<FacturaDoctor> buscarTodos()
	{
		List<FacturaDoctor> lista = new ArrayList<FacturaDoctor>();
		
		try {
			
			String sql="SELECT * FROM factura_doctor order by id_factura desc";
			Statement st = cx.createStatement();
			ResultSet result = st.executeQuery(sql);
			
			while(result.next())
			{
				FacturaDoctor factura = new FacturaDoctor();
				factura.setIdFactura(result.getInt("id_factura"));
				factura.setCodigoFactura(result.getString("codigo_factura"));
				factura.setNombreCliente(result.getString("nombre_cliente"));
				factura.setDireccionCliente(result.getString("direccion_cliente"));
				factura.setFecha(result.getDate("fecha"));
				factura.setDocCliente(result.getString("doc_cliente"));
				factura.setVtaCtaDe(result.getString("vta_cta_de"));
				factura.setSumaNoSujetas(result.getDouble("suma_nosujetas"));
				factura.setSumaVentasExentas(result.getDouble("suma_ventas_exentas"));
				factura.setSumaVentasGravadas(result.getDouble("suma_ventas_gravadas"));
				factura.setVentasExentas(result.getDouble("ventas_exentas"));
				factura.setVentasNoSujetas(result.getDouble("ventas_nosujetas"));
				factura.setSubtotal(result.getDouble("subtotal"));
				factura.setIvaRetenido(result.getDouble("iva_retenido"));
				factura.setVentaTotal(result.getDouble("venta_total"));
				factura.setLetrasMonto(result.getString("monto_letras"));
			
				
				lista.add(factura);
				System.out.println("Busco factura DAO");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public FacturaDoctor buscarFacturaPorId(int idFactura)
	{
		
		FacturaDoctor factura = new FacturaDoctor();
		
		try {
			
			String sql="select * from factura_doctor where id_factura=?";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setInt(1, idFactura);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				factura.setIdFactura(rs.getInt("id_factura"));
				factura.setCodigoFactura(rs.getString("codigo_factura"));
				factura.setNombreCliente(rs.getString("nombre_cliente"));
				factura.setDireccionCliente(rs.getString("direccion_cliente"));
				factura.setFecha(rs.getDate("fecha"));
				factura.setDocCliente(rs.getString("doc_cliente"));
				factura.setVtaCtaDe(rs.getString("vta_cta_de"));
				factura.setSumaNoSujetas(rs.getDouble("suma_nosujetas"));
				factura.setSumaVentasExentas(rs.getDouble("suma_ventas_exentas"));
				factura.setSumaVentasGravadas(rs.getDouble("suma_ventas_gravadas"));
				factura.setVentasExentas(rs.getDouble("ventas_exentas"));
				factura.setVentasNoSujetas(rs.getDouble("ventas_nosujetas"));
				factura.setSubtotal(rs.getDouble("subtotal"));
				factura.setIvaRetenido(rs.getDouble("iva_retenido"));
				factura.setVentaTotal(rs.getDouble("venta_total"));
				factura.setLetrasMonto(rs.getString("monto_letras"));
				
			}
			
			rs.close();
			preparedStatement.close();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
		
		return factura;
	}
	
	
	public List<DetalleFacturaDoctor> buscarDetallesFactura(int idFactura)
	{
		List<DetalleFacturaDoctor> detalles = new ArrayList<DetalleFacturaDoctor>();
		
		try {
			
			String sql="select * from detalle_factura_doctor where id_factura=?";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setInt(1, idFactura);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				
				DetalleFacturaDoctor detalle = new DetalleFacturaDoctor();
				detalle.setIdDetalle(rs.getInt("id_detalle"));
				detalle.setIdFactura(rs.getInt("id_factura"));
				detalle.setCantidad(rs.getInt("cantidad"));
				detalle.setDescripciones(rs.getString("descripciones"));
				detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
				detalle.setVentasNoSujetas(rs.getDouble("ventas_nosujetas"));
				detalle.setVentasExentas(rs.getDouble("ventas_exentas"));
				detalle.setVentasGravadas(rs.getDouble("ventas_gravadas"));
				
				detalles.add(detalle);
				
			}
			
			rs.close();
			preparedStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
		return detalles;
		
	}

}
