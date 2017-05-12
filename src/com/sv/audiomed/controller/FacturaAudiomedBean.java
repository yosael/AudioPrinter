package com.sv.audiomed.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sv.audiomed.dao.DetalleFacturaAudiomedDAO;
import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;

@ManagedBean(name = "facturaAudiomedBean")
@ViewScoped
public class FacturaAudiomedBean {
	
	private FacturaAudiomed facturaAudiomed;
	private FacturaAudiomedDAO facturaAudiomedDAO;
	//private DetalleFacturaAudiomedDAO detalleFacturaAudiomedDAO;
	private List<DetalleFacturaAudiomed> detalles = new ArrayList<DetalleFacturaAudiomed>();
	private DetalleFacturaAudiomed detalle;
	private int idFactura=0;
	private String tipoConcepto="";
	
	
	
	@PostConstruct
	public void init()
	{
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		inicializarFactura();
		inicializarDetalle();
	}
	
	public void inicializarDetalle()
	{
		detalle = new DetalleFacturaAudiomed();
		detalle.setCantidad(1);
		detalle.setVentasNoSujetas(0f);
		detalle.setVentasExentas(0f);
		detalle.setVentasGravadas(0f);
		
	}
	
	public void inicializarFactura()
	{
		
		facturaAudiomed = new FacturaAudiomed();
		facturaAudiomed.setFecha(new Date());
		facturaAudiomed.setSumaNoSujetas(0f);
		facturaAudiomed.setSumaVentasExentas(0f);
		facturaAudiomed.setSumaVentasGravadas(0f);
		facturaAudiomed.setVentasExentas(0f);
		facturaAudiomed.setVentasNoSujetas(0f);
		facturaAudiomed.setSubtotal(0f);
		facturaAudiomed.setIvaRetenido(0f);
		facturaAudiomed.setVentaTotal(0f);
	}
	
	
	public String agregarFactura()
	{
		//facturaAudiomedDAO.agregar(facturaAudiomed);
		//agregarDetalleFactura();
		System.out.println("Factura Nombre cliente"+facturaAudiomed.getNombreCliente());
		System.out.println("Suma no sujetas "+facturaAudiomed.getSumaNoSujetas());
		try {
			
			idFactura= facturaAudiomedDAO.agregarFacturaDetalle(facturaAudiomed, detalles);
			System.out.println("ID FACTURA "+idFactura);
			
			return "vistaFacturaAudiomed.xhtml";
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String agregarDetalleFactura()
	{
		for(DetalleFacturaAudiomed detalle: detalles)
		{
			
			detalle.setIdFactura(facturaAudiomed.getIdFactura());
		}
		
		return "vistaFacturaAudiomed";
	}
	
	public void aplicarConcepto()
	{
		float monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(tipoConcepto.equals("NoSujetas"))
		{
			detalle.setVentasNoSujetas(monto);
			facturaAudiomed.setSumaNoSujetas(facturaAudiomed.getSumaNoSujetas()+monto);
			facturaAudiomed.setVentasNoSujetas(facturaAudiomed.getVentasNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			detalle.setVentasExentas(monto);
			facturaAudiomed.setSumaVentasExentas(facturaAudiomed.getSumaVentasExentas()+monto);
			facturaAudiomed.setVentasExentas(facturaAudiomed.getVentasExentas()+facturaAudiomed.getSumaVentasExentas());
			
		}
		else
		{
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(facturaAudiomed.getSumaVentasGravadas()+monto);
			
		}
		
		
	}
	
	public void actualizarTotales()
	{
		
		float subtotal=facturaAudiomed.getSumaVentasGravadas()+facturaAudiomed.getVentasExentas()+facturaAudiomed.getVentasNoSujetas();
		facturaAudiomed.setSubtotal(facturaAudiomed.getSubtotal()+subtotal);
		
		float ivaRetenido=facturaAudiomed.getSumaVentasGravadas()*(0.13f);
		facturaAudiomed.setIvaRetenido(facturaAudiomed.getIvaRetenido()+ivaRetenido);
		
		facturaAudiomed.setVentaTotal(subtotal+ivaRetenido);
	}
	
	
	public void cargarDetalle()
	{
		
		aplicarConcepto();
		actualizarTotales();
		detalles.add(detalle);
		inicializarDetalle();
		
		System.out.println("Tamanio de lista detalle "+ detalles.size() );
	}
	
	
	
	

	
	
	
	//Getters and Setters
	
	
	public FacturaAudiomed getFacturaAudiomed() {
		return facturaAudiomed;
	}

	public void setFacturaAudiomed(FacturaAudiomed facturaAudiomed) {
		this.facturaAudiomed = facturaAudiomed;
	}

	public List<DetalleFacturaAudiomed> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFacturaAudiomed> detalles) {
		this.detalles = detalles;
	}

	public DetalleFacturaAudiomed getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleFacturaAudiomed detalle) {
		this.detalle = detalle;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public String getTipoConcepto() {
		return tipoConcepto;
	}

	public void setTipoConcepto(String tipoConcepto) {
		this.tipoConcepto = tipoConcepto;
	}
	
	
	
	

}
