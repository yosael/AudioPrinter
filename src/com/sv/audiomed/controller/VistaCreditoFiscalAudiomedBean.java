package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sv.audiomed.dao.CreditoFiscalAudiomedDAO;
import com.sv.audiomed.model.CreditoFiscalAudiomed;
import com.sv.audiomed.model.DetalleCreditoFiscalAudiomed;

@ManagedBean
@ViewScoped
public class VistaCreditoFiscalAudiomedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private CreditoFiscalAudiomed factura;
	private List<DetalleCreditoFiscalAudiomed> detalles;
	private CreditoFiscalAudiomedDAO facturaDAO;
	
	
	@ManagedProperty(value="#{buscarCreditoFiscalAudiomed}")
	private BuscarCreditoFiscalAudiomed buscarCreditoFiscalAudiomed;
	
	public VistaCreditoFiscalAudiomedBean()
	{
		factura = new CreditoFiscalAudiomed();
		detalles =  new ArrayList<DetalleCreditoFiscalAudiomed>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalAudiomedDAO();
		//idFactura = buscarCreditoFiscalAudiomed.getIdFacturaSelected();
		//cargarFactura();
	}
	
	public void cargarPorId()
	{
		cargarFactura();
	}
	
	
	public void cargarFactura()
	{
		
		try {
			
			//idFactura = buscarCreditoFiscalAudiomed.getIdFacturaSelected();
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


	public CreditoFiscalAudiomed getFactura() {
		return factura;
	}


	public void setFactura(CreditoFiscalAudiomed factura) {
		this.factura = factura;
	}


	public List<DetalleCreditoFiscalAudiomed> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetalleCreditoFiscalAudiomed> detalles) {
		this.detalles = detalles;
	}


	public BuscarCreditoFiscalAudiomed getBuscarCreditoFiscalAudiomed() {
		return buscarCreditoFiscalAudiomed;
	}


	public void setBuscarCreditoFiscalAudiomed(BuscarCreditoFiscalAudiomed buscarCreditoFiscalAudiomed) {
		this.buscarCreditoFiscalAudiomed = buscarCreditoFiscalAudiomed;
	}
	
	
	

}
