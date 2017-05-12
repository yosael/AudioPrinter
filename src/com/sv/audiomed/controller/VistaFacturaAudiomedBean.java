package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.dao.ReporteFacturaAudiomedDAO;
import com.sv.audiomed.dao.ReporteJasperUtilDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;

@ManagedBean(name = "vistaFacturaAudiomedBean")
@ViewScoped
public class VistaFacturaAudiomedBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private FacturaAudiomed facturaAudiomed;
	private List<DetalleFacturaAudiomed> detalles;
	private FacturaAudiomedDAO facturaDAO;
	private ReporteJasperUtilDAO reporteJasperDAO;
	private ReporteFacturaAudiomedDAO reporteFacturaAudiomedDAO;
	
	@ManagedProperty(value="#{buscarFacturaAudiomedBean}")
	private BuscarFacturaAudiomedBean buscarFacturaAudiomedBean;
	
	public VistaFacturaAudiomedBean()
	{
		facturaAudiomed = new FacturaAudiomed();
		detalles = new ArrayList<DetalleFacturaAudiomed>();
		reporteFacturaAudiomedDAO = new ReporteFacturaAudiomedDAO();
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaAudiomedDAO();
		reporteJasperDAO = new  ReporteJasperUtilDAO();
		idFactura = buscarFacturaAudiomedBean.getIdFacturaSelected();
		
		cargarFactura();
	}
	
	public void eventoPrueba()
	{
		/*FacesContext facesContext = FacesContext. getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map params = externalContext.getRequestParameterMap();
		Integer categorySelected = new Integer((String) params.get("id" ));*/
	}
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			idFactura = buscarFacturaAudiomedBean.getIdFacturaSelected();
			/*FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String,String> param = facesContext.getExternalContext().getRequestParameterMap();
			System.out.println("CONTENIDO PARAMETRO"+param.get("idFactura"));
			idFactura = new Integer(param.get("idFactura").toString());*/
			
			
			System.out.println("ID FACTURA"+idFactura);
			
			facturaAudiomed = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			
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
	
	
	
	
	

}
