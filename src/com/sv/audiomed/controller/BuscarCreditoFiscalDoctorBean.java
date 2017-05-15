package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.CreditoFiscalDoctorDAO;
import com.sv.audiomed.model.CreditoFiscalDoctor;

@ManagedBean
@SessionScoped
public class BuscarCreditoFiscalDoctorBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	CreditoFiscalDoctorDAO facturaDAO;
	List<CreditoFiscalDoctor> facturas;
	private int idFacturaSelected=0;
	
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalDoctorDAO();
		facturas= new ArrayList<CreditoFiscalDoctor>();
		buscarFacturas();
	}
	
	public void buscarFacturas()
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
		return "vistaCreditoFiscalDoctor?faces-redirect=true";
	}

	public List<CreditoFiscalDoctor> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<CreditoFiscalDoctor> facturas) {
		this.facturas = facturas;
	}

	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}

	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}
	
	
	
	

}
