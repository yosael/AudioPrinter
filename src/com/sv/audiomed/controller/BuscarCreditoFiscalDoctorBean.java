package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.CreditoFiscalDoctorDAO;
import com.sv.audiomed.model.CreditoFiscalDoctor;

@ManagedBean
@SessionScoped
public class BuscarCreditoFiscalDoctorBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	CreditoFiscalDoctorDAO facturaDAO;
	List<CreditoFiscalDoctor> facturas;
	private int idFacturaSelected=0;
	private Date fechaInicio;
	private Date fechaFin;
	
	public BuscarCreditoFiscalDoctorBean()
	{
		
	}
	
	
	/*@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalDoctorDAO();
		facturas= new ArrayList<CreditoFiscalDoctor>();
		buscarFacturas();
	}*/
	
	public void iniciar()
	{
		facturaDAO = new CreditoFiscalDoctorDAO();
		facturas= new ArrayList<CreditoFiscalDoctor>();
		//buscarFacturas();
		setRangoMes();
		buscarPorFechas();
		
	}
	
	public void buscarFacturas()
	{
		try {
			
			facturas = facturaDAO.buscarTodos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String generarNueva(int idFactura)
	{
		this.idFacturaSelected=idFactura;
		
		return "generarCreditoFiscalDoctor?faces-redirect=true";
	}
	
	public String seleccionarFactura(int idFactura)
	{
		
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaCreditoFiscalDoctor?faces-redirect=true";
	}
	
	
	public void buscarPorFechas()
	{
		
		try {
			
			facturas = facturaDAO.buscarPorFechas(fechaInicio, fechaFin);
			
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocurrio un error inesperado",null));
			e.printStackTrace();
			
		}
	}
	
	
	public void setRangoMes() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		setFechaInicio(truncDate(cal.getTime(), false));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		setFechaFin(truncDate(cal.getTime(), true));
	}
	
	protected Date truncDate(Date dateTrunc, boolean endOfDay) {
		if(dateTrunc == null)
			return null;
		Calendar cal = new GregorianCalendar();
		cal.setTime(dateTrunc);
		if(!endOfDay) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
		}
		return cal.getTime();
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


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
	

}
