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
import com.sv.audiomed.dao.FacturaSvTradeDAO;
import com.sv.audiomed.model.DetalleFacturaSvTrade;
import com.sv.audiomed.model.FacturaSvTrade;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean(name = "vistaFacturaSvTradeBean")
@ViewScoped
public class VistaFacturaSvTradeBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private FacturaSvTrade factura;
	private List<DetalleFacturaSvTrade> detalles;
	private FacturaSvTradeDAO facturaDAO;


	
	@ManagedProperty(value="#{buscarFacturaSvTradeBean}")
	private BuscarFacturaSvTradeBean buscarFacturaSvTradeBean;
	
	public VistaFacturaSvTradeBean()
	{
		factura = new FacturaSvTrade();
		detalles = new ArrayList<DetalleFacturaSvTrade>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaSvTradeDAO();
		//cargarFactura();
		
	}
	
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			//idFactura = buscarFacturaSvTradeBean.getIdFacturaSelected();
			factura = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cargarPorId()
	{
		cargarFactura();
	}
	
	
	public String irAlServlet()
	{
		return "FacturaDoctorServlet?idFactura="+idFactura;
	}
	
	public int getParamFactura()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> param = facesContext.getExternalContext().getRequestParameterMap();
		
		System.out.println("CONTENIDO PARAMETRO"+param.get("idFactura"));
		
		idFactura = new Integer(param.get("idFactura").toString());
		
		return idFactura;
	}
	
	
	
	public void imprimir()
	{
		
		System.out.println("Entro a imprimit");
		Connection cn = Conexion.conectar();
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		byte[] pdfBytes;
		
		
		try {
			
			String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/FacturaSvTrade/FacturaSvTradeFormat.jasper");
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


	public FacturaSvTrade getFactura() {
		return factura;
	}


	public void setFactura(FacturaSvTrade factura) {
		this.factura = factura;
	}


	public List<DetalleFacturaSvTrade> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetalleFacturaSvTrade> detalles) {
		this.detalles = detalles;
	}


	public BuscarFacturaSvTradeBean getBuscarFacturaSvTradeBean() {
		return buscarFacturaSvTradeBean;
	}


	public void setBuscarFacturaSvTradeBean(BuscarFacturaSvTradeBean buscarFacturaSvTradeBean) {
		this.buscarFacturaSvTradeBean = buscarFacturaSvTradeBean;
	}
	
	

	

	
	
	
	
	

}
