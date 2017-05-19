package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sv.audiomed.dao.CreditoFiscalSvTradeDAO;
import com.sv.audiomed.model.CreditoFiscalSvTrade;
import com.sv.audiomed.model.DetalleCreditoFiscalSvTrade;

@ManagedBean
@ViewScoped
public class VistaCreditoFiscalSvTradeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private CreditoFiscalSvTrade factura;
	private List<DetalleCreditoFiscalSvTrade> detalles;
	private CreditoFiscalSvTradeDAO facturaDAO;
	
	
	@ManagedProperty(value="#{buscarCreditoFiscalSvTradeBean}")
	private BuscarCreditoFiscalSvTradeBean buscarCreditoFiscalSvTradeBean;
	
	public VistaCreditoFiscalSvTradeBean()
	{
		factura = new CreditoFiscalSvTrade();
		detalles =  new ArrayList<DetalleCreditoFiscalSvTrade>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalSvTradeDAO();
		cargarFactura();
	}
	
	
	public void cargarFactura()
	{
		
		try {
			
			idFactura = buscarCreditoFiscalSvTradeBean.getIdFacturaSelected();
			factura = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public int getIdFactura() {
		return idFactura;
	}


	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}


	public CreditoFiscalSvTrade getFactura() {
		return factura;
	}


	public void setFactura(CreditoFiscalSvTrade factura) {
		this.factura = factura;
	}


	public List<DetalleCreditoFiscalSvTrade> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetalleCreditoFiscalSvTrade> detalles) {
		this.detalles = detalles;
	}


	public BuscarCreditoFiscalSvTradeBean getBuscarCreditoFiscalSvTradeBean() {
		return buscarCreditoFiscalSvTradeBean;
	}


	public void setBuscarCreditoFiscalSvTradeBean(BuscarCreditoFiscalSvTradeBean buscarCreditoFiscalSvTradeBean) {
		this.buscarCreditoFiscalSvTradeBean = buscarCreditoFiscalSvTradeBean;
	}


	
	
	

}
