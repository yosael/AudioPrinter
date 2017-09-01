package com.sv.audiomed.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.sv.audiomed.dao.Conexion;
import com.sv.audiomed.dao.CreditoFiscalAudiomedDAO;
import com.sv.audiomed.model.CreditoFiscalAudiomed;
import com.sv.audiomed.model.DetalleCreditoFiscalAudiomed;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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
	
	
	
	public void imprimir()
	{
		
		System.out.println("Entro a imprimit");
		Connection cn = Conexion.conectar();
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		byte[] pdfBytes;
		
		
		try {
			
			String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/CreditoFiscalAudiomed/CreditoFiscalAudiomedFormat.jasper");
			System.out.println("Archivo "+archivo);
			
			jasperReport = (JasperReport)JRLoader.loadObjectFromFile(archivo);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id_factura", idFactura);
			
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,cn);
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdfBytes.length);
			
			ServletOutputStream out = response.getOutputStream();
			out.write(pdfBytes,0,pdfBytes.length);
			out.flush();
			out.close();
			
			FacesContext.getCurrentInstance().responseComplete();
			
			
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
