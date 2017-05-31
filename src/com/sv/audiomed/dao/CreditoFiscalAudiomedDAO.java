package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sv.audiomed.model.CreditoFiscalAudiomed;
import com.sv.audiomed.model.DetalleCreditoFiscalAudiomed;

public class CreditoFiscalAudiomedDAO {
	
private Connection cx;
	
	public CreditoFiscalAudiomedDAO()
	{
		cx = Conexion.conectar();
	}
	
	public int agregarFacturaDetalle(CreditoFiscalAudiomed factura,List<DetalleCreditoFiscalAudiomed> detalles) throws SQLException
	{
		int idFactura=0;
		System.out.println("Entro a registro factura DAO");
		try {
				
			cx.setAutoCommit(false);
			ResultSet rsIdFactura;
			
			
			String sql ="insert into creditofiscal_audiomed (codigo_factura,nombre_cliente,direccion_cliente,fecha,doc_cliente,registro,nota_num_emision,giro,fecha_nota_emision,condicion_pago,suma_nosujetas,suma_ventas_exentas,suma_ventas_gravadas,ventas_exentas,ventas_nosujetas,subtotal,iva_retenido,venta_total,monto_letras,porcent_iva,direccionp2) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = cx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, factura.getCodigoFactura());
			preparedStatement.setString(2, factura.getNombreCliente());
			preparedStatement.setString(3, factura.getDireccionCliente());
			
			Date sqlDate = new Date(factura.getFecha().getTime());
			preparedStatement.setDate(4, sqlDate);
			
			preparedStatement.setString(5,factura.getDocCliente()!=null?factura.getDocCliente():" ");
			preparedStatement.setString(6, factura.getRegistro()!=null?factura.getRegistro():" ");
			preparedStatement.setString(7, factura.getNotaNumEmision()!=null?factura.getNotaNumEmision():" ");
			preparedStatement.setString(8, factura.getGiro()!=null?factura.getGiro():" ");
			
			if(factura.getFechaNotaEmision()!=null)
			{
				Date sqlDate2 = new Date(factura.getFechaNotaEmision().getTime());
				preparedStatement.setDate(9, sqlDate2);
			}
			else
			{
				preparedStatement.setDate(9, null);
			}
			
			preparedStatement.setString(10, factura.getCondicionPago()!=null?factura.getCondicionPago():" ");
			
			preparedStatement.setDouble(11, factura.getSumaNoSujetas()!=null?factura.getSumaNoSujetas():0d);
			preparedStatement.setDouble(12, factura.getSumaVentasExentas()!=null?factura.getSumaVentasExentas():0d);
			preparedStatement.setDouble(13, factura.getSumaVentasGravadas()!=null?factura.getSumaVentasGravadas():0d);
			preparedStatement.setDouble(14,factura.getVentasExentas()!=null?factura.getVentasExentas():0d);
			preparedStatement.setDouble(15, factura.getVentasNoSujetas()!=null?factura.getVentasNoSujetas():0d);
			preparedStatement.setDouble(16, factura.getSubtotal()!=null?factura.getSubtotal():0d);
			preparedStatement.setDouble(17, factura.getIvaRetenido()!=null?factura.getIvaRetenido():0d);
			preparedStatement.setDouble(18, factura.getVentaTotal()!=null?factura.getVentaTotal():0d);
			preparedStatement.setString(19, factura.getLetrasMonto()!=null?factura.getLetrasMonto():" ");
			preparedStatement.setDouble(20, factura.getPorcentIva()!=null?factura.getPorcentIva():0d);
			preparedStatement.setString(21, factura.getDireccionp2()!=null?factura.getDireccionp2():" ");
			
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
				for(DetalleCreditoFiscalAudiomed detalle:detalles)
				{
					
					String sqlDetalle ="insert into detalle_creditofiscal_audiomed(id_factura,cantidad,descripciones,precio_unitario,ventas_nosujetas,ventas_exentas,ventas_gravadas) values(?,?,?,?,?,?,?)";
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
	
	
	public List<CreditoFiscalAudiomed> buscarTodos()
	{
		List<CreditoFiscalAudiomed> lista = new ArrayList<CreditoFiscalAudiomed>();
		
		try {
			
			String sql="SELECT * FROM creditofiscal_audiomed order by id_factura desc";
			Statement st = cx.createStatement();
			ResultSet result = st.executeQuery(sql);
			
			while(result.next())
			{
				CreditoFiscalAudiomed factura = new CreditoFiscalAudiomed();
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
				factura.setDireccionp2(result.getString("direccionp2"));
			
				
				lista.add(factura);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public CreditoFiscalAudiomed buscarFacturaPorId(int idFactura) throws SQLException 
	{
		
		CreditoFiscalAudiomed factura = new CreditoFiscalAudiomed();
		
		try {
			
			String sql="select * from creditofiscal_audiomed where id_factura=?";
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
				factura.setDireccionp2(rs.getString("direccionp2"));
				
			}
			
			rs.close();
			preparedStatement.close();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
		
		return factura;
	}
	
	
	public List<DetalleCreditoFiscalAudiomed> buscarDetallesFactura(int idFactura) throws SQLException
	{
		List<DetalleCreditoFiscalAudiomed> detalles = new ArrayList<DetalleCreditoFiscalAudiomed>();
		
		try {
			
			String sql="select * from detalle_creditofiscal_audiomed where id_factura=?";
			PreparedStatement preparedStatement = cx.prepareStatement(sql);
			preparedStatement.setInt(1, idFactura);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				
				DetalleCreditoFiscalAudiomed detalle = new DetalleCreditoFiscalAudiomed();
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
	
	public List<CreditoFiscalAudiomed> buscarPorFechas(java.util.Date fechaInicio,java.util.Date fechaFin) throws SQLException
	{
		List<CreditoFiscalAudiomed> facturas = new ArrayList<CreditoFiscalAudiomed>();
		String query = "Select * from creditofiscal_audiomed where fecha>=? and fecha<=? order by id_factura desc ";
		
		try {
			
				
			PreparedStatement preparedStatement = cx.prepareStatement(query);
			
			Date sqlDate1 = new Date(fechaInicio.getTime());
			Date sqlDate2 = new Date(fechaFin.getTime());
			
			preparedStatement.setDate(1, sqlDate1);
			preparedStatement.setDate(2, sqlDate2);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				
				CreditoFiscalAudiomed factura = new CreditoFiscalAudiomed();
				
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
				factura.setDireccionp2(rs.getString("direccionp2"));
				
				facturas.add(factura);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return facturas;
	}

}
