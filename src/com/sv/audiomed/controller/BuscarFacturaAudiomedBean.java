package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.FacturaAudiomed;

@ManagedBean(name = "buscarFacturaAudiomedBean")
@SessionScoped
public class BuscarFacturaAudiomedBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	String busqueda="";
	FacturaAudiomedDAO facturaAudiomedDAO;
	List<FacturaAudiomed> facturas;
	private int idFacturaSelected;
	
	
	public BuscarFacturaAudiomedBean()
	{
		
	}
	
	/*@PostConstruct
	public void init()
	{
		
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		facturas = new ArrayList<FacturaAudiomed>();
		buscarFactura();
		
	}*/
	
	public void iniciar()
	{
		
		System.out.println("Entro a iniciar Factura Audiomed");
		
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		facturas = new ArrayList<FacturaAudiomed>();
		buscarFactura();
	}
	
	public void buscarFactura()
	{
		try {
			
			facturas = facturaAudiomedDAO.buscarTodos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String seleccionarFactura(int idFactura)
	{
		
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaFacturaAudiomed?faces-redirect=true";
	}
	
	public String generarNueva(int idFactura)
	{
		this.idFacturaSelected=idFactura;
		return "facturaAudiomed.xhtml?faces-redirect=true";
	}

	
	public List<FacturaAudiomed> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaAudiomed> facturas) {
		this.facturas = facturas;
	}

	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}

	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}
	
	
	
	
	
}
