package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.FacturaDoctorDAO;
import com.sv.audiomed.model.FacturaDoctor;

@ManagedBean(name = "buscarFacturaDoctorBean")
@SessionScoped
public class BuscarFacturaDoctorBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private FacturaDoctorDAO facturaDoctorDAO;
	private List<FacturaDoctor> facturas;
	private int idFacturaSelected;
	private Date fechaInicio;
	private Date fechaFin;
	
	public BuscarFacturaDoctorBean()
	{
		idFacturaSelected=0;
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
		//buscarFactura();
		setRangoMes();
		buscarPorFechas();
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
	
	public String generarNueva(int idFactura)
	{
		this.idFacturaSelected=idFactura;
		
		return "generarFacturaDoctor?faces-redirect=true";
	}

	
	public void buscarPorFechas()
	{
		//System.out.println("Entro a buscar por fechas");
		try {
			
			facturas = facturaDoctorDAO.buscarPorFechas(fechaInicio, fechaFin);
			
		} catch (Exception e) {
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
