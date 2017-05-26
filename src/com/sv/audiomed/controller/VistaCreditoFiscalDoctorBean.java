package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sv.audiomed.dao.CreditoFiscalDoctorDAO;
import com.sv.audiomed.model.CreditoFiscalDoctor;
import com.sv.audiomed.model.DetalleCreditoFiscalDoctor;

@ManagedBean
@ViewScoped
public class VistaCreditoFiscalDoctorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private CreditoFiscalDoctor factura;
	private List<DetalleCreditoFiscalDoctor> detalles;
	private CreditoFiscalDoctorDAO facturaDAO;
	
	
	@ManagedProperty(value="#{buscarCreditoFiscalDoctorBean}")
	private BuscarCreditoFiscalDoctorBean buscarCreditoFiscalDoctorBean;
	
	public VistaCreditoFiscalDoctorBean()
	{
		factura = new CreditoFiscalDoctor();
		detalles =  new ArrayList<DetalleCreditoFiscalDoctor>();
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalDoctorDAO();
		//idFactura = buscarCreditoFiscalDoctor.getIdFacturaSelected();
		cargarFactura();
	}
	
	
	public void cargarFactura()
	{
		
		try {
			
			idFactura = buscarCreditoFiscalDoctorBean.getIdFacturaSelected();
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


	public CreditoFiscalDoctor getFactura() {
		return factura;
	}


	public void setFactura(CreditoFiscalDoctor factura) {
		this.factura = factura;
	}


	public List<DetalleCreditoFiscalDoctor> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetalleCreditoFiscalDoctor> detalles) {
		this.detalles = detalles;
	}


	public BuscarCreditoFiscalDoctorBean getBuscarCreditoFiscalDoctorBean() {
		return buscarCreditoFiscalDoctorBean;
	}


	public void setBuscarCreditoFiscalDoctorBean(BuscarCreditoFiscalDoctorBean buscarCreditoFiscalDoctorBean) {
		this.buscarCreditoFiscalDoctorBean = buscarCreditoFiscalDoctorBean;
	}


	
	
	

}
