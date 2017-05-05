package com.sv.audiomed.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.DetalleFacturaAudiomedDAO;
import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;

@ManagedBean(name = "facturaAudiomedBean")
@SessionScoped
public class FacturaAudiomedBean {
	
	private FacturaAudiomed facturaAudiomed= new FacturaAudiomed();
	private FacturaAudiomedDAO facturaAudiomedDAO = new FacturaAudiomedDAO();
	private DetalleFacturaAudiomedDAO detalleFacturaAudiomedDAO = new DetalleFacturaAudiomedDAO();
	private List<DetalleFacturaAudiomed> detalles = new ArrayList<DetalleFacturaAudiomed>();
	private DetalleFacturaAudiomed detalle = new DetalleFacturaAudiomed();
	
	public void agregarFactura()
	{
		facturaAudiomedDAO.agregar(facturaAudiomed);
		agregarDetalleFactura();
	}
	
	public void agregarDetalleFactura()
	{
		for(DetalleFacturaAudiomed detalle: detalles)
		{
			detalleFacturaAudiomedDAO.agregar(detalle);
		}
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
	
	
	
	

}
