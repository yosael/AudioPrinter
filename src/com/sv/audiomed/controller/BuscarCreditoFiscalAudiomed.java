package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.CreditoFiscalAudiomedDAO;
import com.sv.audiomed.model.CreditoFiscalAudiomed;

@ManagedBean
@SessionScoped
public class BuscarCreditoFiscalAudiomed implements Serializable{


	private static final long serialVersionUID = 1L;
	
	CreditoFiscalAudiomedDAO facturaDAO;
	List<CreditoFiscalAudiomed> facturas;
	private int idFacturaSelected=0;
	
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalAudiomedDAO();
		facturas= new ArrayList<CreditoFiscalAudiomed>();
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
		return "vistaCreditoFiscalAudiomed?faces-redirect=true";
	}

	public List<CreditoFiscalAudiomed> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<CreditoFiscalAudiomed> facturas) {
		this.facturas = facturas;
	}

	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}

	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}
	
	
	
	

}
