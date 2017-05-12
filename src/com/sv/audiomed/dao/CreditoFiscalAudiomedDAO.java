package com.sv.audiomed.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			
			
			String sql ="insert into creditofiscal_audiomed(codigo_factura,nombre_cliente,direccion_cliente,fecha,doc_cliente,registro,nota_num_emision,giro,fecha_nota_emision,condicion_pago,suma_nosujetas,suma_ventas_exentas,suma_ventas_gravadas,ventas_exentas,ventas_nosujetas,subtotal,iva_retenido,venta_total,monto_letras) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			
			Date sqlDate2 = new Date(factura.getFechaNotaEmision().getTime());
			preparedStatement.setDate(9, sqlDate2);
			
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
					
					String sqlDetalle ="Insert into detalle_factura_audiomed(id_factura,cantidad,descripciones,precio_unitario,ventas_nosujetas,ventas_exentas,ventas_gravadas) values(?,?,?,?,?,?,?)";
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

}
