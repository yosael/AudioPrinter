package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.CreditoFiscalAudiomedDAO;
import com.sv.audiomed.model.CreditoFiscalAudiomed;

@ManagedBean
@SessionScoped
public class BuscarCreditoFiscalAudiomed implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private CreditoFiscalAudiomedDAO facturaDAO;
	private List<CreditoFiscalAudiomed> facturas;
	private int idFacturaSelected;
	private Date fechaInicio;
	private Date fechaFin;
	
	
	public BuscarCreditoFiscalAudiomed()
	{
		idFacturaSelected=0;
	}
	
	/*@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalAudiomedDAO();
		facturas= new ArrayList<CreditoFiscalAudiomed>();
		buscarFacturas();
	}*/
	
	public void iniciar()
	{
		facturaDAO = new CreditoFiscalAudiomedDAO();
		facturas= new ArrayList<CreditoFiscalAudiomed>();
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
		return "generarCreditoFiscalAudiomed.xhtml?faces-redirect=true";
	}
	
	public String seleccionarFactura(int idFactura)
	{
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaCreditoFiscalAudiomed?faces-redirect=true";
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
