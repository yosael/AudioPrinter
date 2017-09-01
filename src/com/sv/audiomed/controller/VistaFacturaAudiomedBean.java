package com.sv.audiomed.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.sv.audiomed.dao.Conexion;
import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.dao.ReporteFacturaAudiomedDAO;
import com.sv.audiomed.dao.ReporteJasperUtilDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

@ManagedBean(name = "vistaFacturaAudiomedBean")
@ViewScoped
public class VistaFacturaAudiomedBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura;
	private FacturaAudiomed facturaAudiomed;
	private List<DetalleFacturaAudiomed> detalles;
	private FacturaAudiomedDAO facturaDAO;
	private ReporteFacturaAudiomedDAO reporteFacturaAudiomedDAO;
	private String vista;
	
	@ManagedProperty(value="#{buscarFacturaAudiomedBean}")
	private BuscarFacturaAudiomedBean buscarFacturaAudiomedBean;
	
	public VistaFacturaAudiomedBean()
	{
		facturaAudiomed = new FacturaAudiomed();
		detalles = new ArrayList<DetalleFacturaAudiomed>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaAudiomedDAO();
		
		//idFactura = buscarFacturaAudiomedBean.getIdFacturaSelected();
		reporteFacturaAudiomedDAO = new ReporteFacturaAudiomedDAO();
		
		System.out.println("ID Factura capturado"+idFactura);
		//cargarFactura();
		
	}
	
	public void cargarPorId()
	{
		System.out.println("CargoID");
		cargarFactura();
	}
	
	
	
	public void eventoPrueba()
	{
		FacesContext facesContext = FacesContext. getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map params = externalContext.getRequestParameterMap();
		Integer categorySelected = new Integer((String) params.get("idFactura" ));
	}
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			//idFactura = buscarFacturaAudiomedBean.getIdFacturaSelected();
			/*FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String,String> param = facesContext.getExternalContext().getRequestParameterMap();
			System.out.println("CONTENIDO PARAMETRO"+param.get("idFactura"));
			idFactura = new Integer(param.get("idFactura").toString());*/
			
			
			System.out.println("ID FACTURA"+idFactura);
			
			facturaAudiomed = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			System.out.println("LETRAS MONTO: "+facturaAudiomed.getLetrasMonto());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void imprimirFactura()
	{
		//reporteJasperDAO.generarReporteFacturaAudiomed(idFactura);imprimirReporteFacturaAudiomed
		//reporteJasperDAO.imprimirReporteFacturaAudiomed(idFactura);
		reporteFacturaAudiomedDAO.crearReporte(idFactura);
		
	}
	
	public String irAlServlet()
	{
		return "facturaAudiomedServlet?idFactura="+idFactura;
	}
	
	public int getParamFactura()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> param = facesContext.getExternalContext().getRequestParameterMap();
		
		System.out.println("CONTENIDO PARAMETRO"+param.get("idFactura"));
		
		idFactura = new Integer(param.get("idFactura").toString());
		
		return idFactura;
	}
	
	
	public void imprimirViewer()
	{
		Connection cn;
		cn = Conexion.conectar();
		//Nuevo 
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		JasperViewer jasperViewer;
		try {
			
			jasperReport = (JasperReport)JRLoader.loadObjectFromFile("C:\\Users\\Hp\\JaspersoftWorkspace\\FacturaAudiomed\\FacturaAudiomedFormat.jasper");
			Map parameters = new HashMap();
			parameters.put("id_factura", idFactura);
			
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,cn);
			
			jasperViewer = new JasperViewer(jasperPrint);
			jasperViewer.setVisible(true);
			
			
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
			
			String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/FacturaAudiomed/FacturaAudiomedFormat.jasper");
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
	
	

	public FacturaAudiomed getFacturaAudiomed() {
		return facturaAudiomed;
	}

	public void setFacturaAudiomed(FacturaAudiomed facturaAudiomed) {
		this.facturaAudiomed = facturaAudiomed;
	}

	public List<DetalleFacturaAudiomed> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFacturaAudiomed> detalles) {
		this.detalles = detalles;
	}


	public BuscarFacturaAudiomedBean getBuscarFacturaAudiomedBean() {
		return buscarFacturaAudiomedBean;
	}


	public void setBuscarFacturaAudiomedBean(BuscarFacturaAudiomedBean buscarFacturaAudiomedBean) {
		this.buscarFacturaAudiomedBean = buscarFacturaAudiomedBean;
	}


	public String getVista() {
		return vista;
	}


	public void setVista(String vista) {
		this.vista = vista;
	}
	
	
	
	
	

}
