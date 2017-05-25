package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sv.audiomed.model.CreditoFiscalDoctor;
import com.sv.audiomed.model.DetalleCreditoFiscalDoctor;

public class CreditoFiscalDoctorDAO {
	
private Connection cx;
	
	public CreditoFiscalDoctorDAO()
	{
		cx = Conexion.conectar();
	}
	
	public int agregarFacturaDetalle(CreditoFiscalDoctor factura,List<DetalleCreditoFiscalDoctor> detalles) throws SQLException
	{
		int idFactura=0;
		System.out.println("Entro a registro factura DAO");
		try {
				
			cx.setAutoCommit(false);
			ResultSet rsIdFactura;
			
			
			String sql ="insert into creditofiscal_doctor (codigo_factura,nombre_cliente,direccion_cliente,fecha,doc_cliente,registro,nota_num_emision,giro,fecha_nota_emision,condicion_pago,suma_nosujetas,suma_ventas_exentas,suma_ventas_gravadas,ventas_exentas,ventas_nosujetas,subtotal,iva_retenido,venta_total,monto_letras,porcent_iva) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, factura.getCodigoFactura());
			preparedStatement.setString(2, factura.getNombreCliente());
			preparedStatement.setString(3, factura.getDireccionCliente());
			
			Date sqlDate = new Date(factura.getFecha().getTime());
			preparedStatement.setDate(4, sqlDate);
			
			preparedStatement.setString(5,factura.getDocCliente()!=null?factura.getDocCliente():null);
			preparedStatement.setString(6, factura.getRegistro()!=null?factura.getRegistro():null);
			preparedStatement.setString(7, factura.getNotaNumEmision()!=null?factura.getNotaNumEmision():null);
			preparedStatement.setString(8, factura.getGiro()!=null?factura.getGiro():null);
			
			if(factura.getFechaNotaEmision()!=null)
			{
				Date sqlDate2 = new Date(factura.getFechaNotaEmision().getTime());
				preparedStatement.setDate(9, sqlDate2);
			}
			else
			{
				preparedStatement.setDate(9, null);
			}
			
			preparedStatement.setString(10, factura.getCondicionPago()!=null?factura.getCondicionPago():null);
			
			preparedStatement.setDouble(11, factura.getSumaNoSujetas()!=null?factura.getSumaNoSujetas():0d);
			preparedStatement.setDouble(12, factura.getSumaVentasExentas()!=null?factura.getSumaVentasExentas():0d);
			preparedStatement.setDouble(13, factura.getSumaVentasGravadas()!=null?factura.getSumaVentasGravadas():0d);
			preparedStatement.setDouble(14,factura.getVentasExentas()!=null?factura.getVentasExentas():0d);
			preparedStatement.setDouble(15, factura.getVentasNoSujetas()!=null?factura.getVentasNoSujetas():0d);
			preparedStatement.setDouble(16, factura.getSubtotal()!=null?factura.getSubtotal():0d);
			preparedStatement.setDouble(17, factura.getIvaRetenido()!=null?factura.getIvaRetenido():0d);
			preparedStatement.setDouble(18, factura.getVentaTotal()!=null?factura.getVentaTotal():0d);
			preparedStatement.setString(19, factura.getLetrasMonto()!=null?factura.getLetrasMonto():null);
			preparedStatement.setDouble(20, factura.getPorcentIva()!=null?factura.getPorcentIva():0d);
			
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
				for(DetalleCreditoFiscalDoctor detalle:detalles)
				{
					
					String sqlDetalle ="insert into detalle_creditofiscal_doctor(id_factura,cantidad,descripciones,precio_unitario,ventas_nosujetas,ventas_exentas,ventas_gravadas) values(?,?,?,?,?,?,?)";
					PreparedStatement preparedStatementDetalle = cx.prepareStatement(sqlDetalle);
					preparedStatementDetalle.setInt(1, idFactura);
					preparedStatementDetalle.setInt(2,detalle.getCantidad());
					preparedStatementDetalle.setString(3, detalle.getDescripciones());
					preparedStatementDetalle.setDouble(4, detalle.getPrecioUnitario());
					preparedStatementDetalle.setDouble(5,detalle.getVentasNoSujetas()!=null?detalle.getVentasNoSujetas():0d);
					preparedStatementDetalle.setDouble(6, detalle.getVentasExentas()!=null?detalle.getVentasExentas():0d);
					preparedStatementDetalle.setDouble(7, detalle.getVentasGravadas()!=null?detalle.getVentasGravadas():0d);
					
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
	
	
	public List<CreditoFiscalDoctor> buscarTodos()
	{
		List<CreditoFiscalDoctor> lista = new ArrayList<CreditoFiscalDoctor>();
		
		try {
			
			String sql="SELECT * FROM creditofiscal_doctor order by id_factura desc";
			Statement st = cx.createStatement();
			ResultSet result = st.executeQuery(sql);
			
			while(result.next())
			{
				CreditoFiscalDoctor factura = new CreditoFiscalDoctor();
				factura.setIdFactura(result.getInt("id_factura"));
				factura.setCodigoFactura(result.getString("codigo_factura"));
				factura.setNombreCliente(result.getString("nombre_cliente"));
				factura.setDireccionCliente(result.getString("direccion_cliente"));
				factura.setFecha(result.getDate("fecha"));
				factura.setDocCliente(result.getString("doc_cliente"));
				factura.setRegistro(result.getString("registro"));
				factura.setNotaNumEmision(result.getString("nota_num_emision"));
				factura.setGiro(result.getString("giro"));
				factura.setFechaNotaEmision(result.getDate("fecha_nota_emision"));
				factura.setCondicionPago(result.getString("condicion_pago"));
				
				factura.setSumaNoSujetas(result.getDouble("suma_nosujetas"));
				factura.setSumaVentasExentas(result.getDouble("suma_ventas_exentas"));
				factura.setSumaVentasGravadas(result.getDouble("suma_ventas_gravadas"));
				factura.setVentasExentas(result.getDouble("ventas_exentas"));
				factura.setVentasNoSujetas(result.getDouble("ventas_nosujetas"));
				factura.setSubtotal(result.getDouble("subtotal"));
				factura.setIvaRetenido(result.getDouble("iva_retenido"));
				factura.setVentaTotal(result.getDouble("venta_total"));
				factura.setLetrasMonto(result.getString("monto_letras"));
				factura.setPorcentIva(result.getDouble("porcent_iva"));
			
				
				lista.add(factura);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public CreditoFiscalDoctor buscarFacturaPorId(int idFactura)
	{
		
		CreditoFiscalDoctor factura = new CreditoFiscalDoctor();
		
		try {
			
			String sql="select * from creditofiscal_doctor where id_factura=?";
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
				
				factura.setRegistro(rs.getString("registro"));
				factura.setNotaNumEmision(rs.getString("nota_num_emision"));
				factura.setGiro(rs.getString("giro"));
				factura.setFechaNotaEmision(rs.getDate("fecha_nota_emision"));
				factura.setCondicionPago(rs.getString("condicion_pago"));
				
				factura.setSumaNoSujetas(rs.getDouble("suma_nosujetas"));
				factura.setSumaVentasExentas(rs.getDouble("suma_ventas_exentas"));
				factura.setSumaVentasGravadas(rs.getDouble("suma_ventas_gravadas"));
				factura.setVentasExentas(rs.getDouble("ventas_exentas"));
				factura.setVentasNoSujetas(rs.getDouble("ventas_nosujetas"));
				factura.setSubtotal(rs.getDouble("subtotal"));
				factura.setIvaRetenido(rs.getDouble("iva_retenido"));
				factura.setVentaTotal(rs.getDouble("venta_total"));
				factura.setLetrasMonto(rs.getString("monto_letras"));
				factura.setPorcentIva(rs.getDouble("porcent_iva"));
				
				
			}
			
			rs.close();
			preparedStatement.close();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
		
		return factura;
	}
	
	
	public List<DetalleCreditoFiscalDoctor> buscarDetallesFactura(int idFactura)
	{
		List<DetalleCreditoFiscalDoctor> detalles = new ArrayList<DetalleCreditoFiscalDoctor>();
		
		try {
			
			String sql="select * from detalle_creditofiscal_doctor where id_factura=?";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setInt(1, idFactura);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				
				DetalleCreditoFiscalDoctor detalle = new DetalleCreditoFiscalDoctor();
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
