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
import com.sv.audiomed.dao.FacturaDoctorDAO;
import com.sv.audiomed.model.DetalleFacturaDoctor;
import com.sv.audiomed.model.FacturaDoctor;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean(name = "vistaFacturaDoctorBean")
@ViewScoped
public class VistaFacturaDoctorBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura;
	private FacturaDoctor facturaDoctor;
	private List<DetalleFacturaDoctor> detalles;
	private FacturaDoctorDAO facturaDAO;


	
	@ManagedProperty(value="#{buscarFacturaDoctorBean}")
	private BuscarFacturaDoctorBean buscarFacturaDoctorBean;
	
	public VistaFacturaDoctorBean()
	{
		idFactura=0;
		facturaDoctor = new FacturaDoctor();
		detalles = new ArrayList<DetalleFacturaDoctor>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaDoctorDAO();
		idFactura = buscarFacturaDoctorBean.getIdFacturaSelected();
		//cargarFactura();
		
	}
	
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			//idFactura = buscarFacturaDoctorBean.getIdFacturaSelected();
			/*FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String,String> param = facesContext.getExternalContext().getRequestParameterMap();
			System.out.println("CONTENIDO PARAMETRO"+param.get("idFactura"));
			idFactura = new Integer(param.get("idFactura").toString());*/
			
			
			System.out.println("ID FACTURA"+idFactura);
			
			facturaDoctor = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			System.out.println("LETRAS MONTO: "+facturaDoctor.getLetrasMonto());
			
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
			
			String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/FacturaDoctor/FacturaDoctorN.jasper");
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
	
	

	public FacturaDoctor getFacturaDoctor() {
		return facturaDoctor;
	}

	public void setFacturaDoctor(FacturaDoctor FacturaDoctor) {
		this.facturaDoctor = FacturaDoctor;
	}

	public List<DetalleFacturaDoctor> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFacturaDoctor> detalles) {
		this.detalles = detalles;
	}


	public BuscarFacturaDoctorBean getBuscarFacturaDoctorBean() {
		return buscarFacturaDoctorBean;
	}


	public void setBuscarFacturaDoctorBean(BuscarFacturaDoctorBean buscarFacturaDoctorBean) {
		this.buscarFacturaDoctorBean = buscarFacturaDoctorBean;
	}
	
	
	
	
	

}
