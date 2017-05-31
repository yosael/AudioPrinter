package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	private Date fechaInicio;
	private Date fechaFin;
	
	public BuscarCreditoFiscalSvTradeBean()
	{
		
	}
	
	
	public void iniciar()
	{
		facturaDAO = new CreditoFiscalSvTradeDAO();
		facturas= new ArrayList<CreditoFiscalSvTrade>();
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
	
	public String seleccionarFactura(int idFactura)
	{
		
		this.idFacturaSelected=idFactura;
		System.out.println("Nueva factura: "+idFacturaSelected);
		return "vistaCreditoFiscalSvTrade?faces-redirect=true";
	}
	
	
	public void buscarPorFechas()
	{
		System.out.println("Entro a buscar por fechas");
		try {
			
			facturas = facturaDAO.buscarPorFechas(fechaInicio, fechaFin);
			System.out.println("TAM "+facturas.size() );
			
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
