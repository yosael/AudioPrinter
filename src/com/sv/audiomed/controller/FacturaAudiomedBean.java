package com.sv.audiomed.controller;

import java.util.ArrayList;
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
	
	private FacturaAudiomed facturaAudiomed= new FacturaAudiomed();
	private FacturaAudiomedDAO facturaAudiomedDAO;
	//private DetalleFacturaAudiomedDAO detalleFacturaAudiomedDAO;
	private List<DetalleFacturaAudiomed> detalles = new ArrayList<DetalleFacturaAudiomed>();
	private DetalleFacturaAudiomed detalle = new DetalleFacturaAudiomed();
	private int idFactura=0;
	
	
	@PostConstruct
	public void init()
	{
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		//detalleFacturaAudiomedDAO = new DetalleFacturaAudiomedDAO();
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
			//detalleFacturaAudiomedDAO.agregar(detalle);
			detalle.setIdFactura(facturaAudiomed.getIdFactura());
		}
		
		return "vistaFacturaAudiomed";
	}
	
	
	public void cargarDetalle()
	{
		detalles.add(detalle);
		detalle = new DetalleFacturaAudiomed();
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
	
	
	
	

}
