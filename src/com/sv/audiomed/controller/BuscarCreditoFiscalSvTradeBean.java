package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.CreditoFiscalSvTradeDAO;
import com.sv.audiomed.model.CreditoFiscalSvTrade;

@ManagedBean
@SessionScoped
public class BuscarCreditoFiscalSvTradeBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	CreditoFiscalSvTradeDAO facturaDAO;
	List<CreditoFiscalSvTrade> facturas;
	private int idFacturaSelected=0;
	
	public BuscarCreditoFiscalSvTradeBean()
	{
		
	}
	
	
	public void iniciar()
	{
		facturaDAO = new CreditoFiscalSvTradeDAO();
		facturas= new ArrayList<CreditoFiscalSvTrade>();
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
		return "vistaCreditoFiscalSvTrade?faces-redirect=true";
	}


	public List<CreditoFiscalSvTrade> getFacturas() {
		return facturas;
	}


	public void setFacturas(List<CreditoFiscalSvTrade> facturas) {
		this.facturas = facturas;
	}


	public int getIdFacturaSelected() {
		return idFacturaSelected;
	}


	public void setIdFacturaSelected(int idFacturaSelected) {
		this.idFacturaSelected = idFacturaSelected;
	}


	
	
	

}
