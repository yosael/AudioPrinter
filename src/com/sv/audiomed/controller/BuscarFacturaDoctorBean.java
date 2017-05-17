package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PostLoad;

import com.sv.audiomed.dao.FacturaDoctorDAO;
import com.sv.audiomed.model.FacturaDoctor;

@ManagedBean(name = "buscarFacturaDoctorBean")
@SessionScoped
public class BuscarFacturaDoctorBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	String busqueda="";
	FacturaDoctorDAO facturaDoctorDAO;
	List<FacturaDoctor> facturas;
	private int idFacturaSelected=0;
	
	public BuscarFacturaDoctorBean()
	{
		
	}
	
	
	
	/*@PostConstruct
	public void init()
	{
		
		facturaDoctorDAO = new FacturaDoctorDAO();
		facturas = new ArrayList<FacturaDoctor>();
		buscarFactura();
		
	}*/
	
	public void iniciar()
	{
		System.out.println("Entro a iniciar");
		facturaDoctorDAO = new FacturaDoctorDAO();
		facturas = new ArrayList<FacturaDoctor>();
		buscarFactura();
	}
	
	public void buscarFactura()
	{
		System.out.println("Entro a buscar");
		try {
			
			facturas = facturaDoctorDAO.buscarTodos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String seleccionarFactura(int idFactura)
	{
		
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaFacturaDoctor?faces-redirect=true";
	}

	
	public List<FacturaDoctor> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaDoctor> facturas) {
		this.facturas = facturas;
	}

	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}

	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}
	
	
	
	
	
}
