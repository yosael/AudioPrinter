package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.FacturaSvTradeDAO;
import com.sv.audiomed.model.FacturaAudiomed;
import com.sv.audiomed.model.FacturaSvTrade;

@ManagedBean(name = "buscarFacturaSvTradeBean")
@SessionScoped
public class BuscarFacturaSvTradeBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	String busqueda="";
	FacturaSvTradeDAO facturaDAO;
	List<FacturaSvTrade> facturas;
	private int idFacturaSelected=0;
	
	
	public BuscarFacturaSvTradeBean()
	{
		
	}
	
	/*@PostConstruct
	public void init()
	{
		
		facturaDAO = new facturaDAO();
		facturas = new ArrayList<FacturaAudiomed>();
		buscarFactura();
		
	}*/
	
	public void iniciar()
	{
		
		System.out.println("Entro a iniciar Factura SV Trade");
		
		facturaDAO = new FacturaSvTradeDAO();
		facturas = new ArrayList<FacturaSvTrade>();
		buscarFactura();
	}
	
	public void buscarFactura()
	{
		try {
			
			facturas = facturaDAO.buscarTodos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String seleccionarFactura(int idFactura)
	{
		
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaFacturaSvTrade?faces-redirect=true";
	}

	
	public List<FacturaSvTrade> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaSvTrade> facturas) {
		this.facturas = facturas;
	}

	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}

	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}
	
	
	
	
	
}
