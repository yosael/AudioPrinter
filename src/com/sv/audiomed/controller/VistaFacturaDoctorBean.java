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

import com.sv.audiomed.dao.FacturaDoctorDAO;
import com.sv.audiomed.model.DetalleFacturaDoctor;
import com.sv.audiomed.model.FacturaDoctor;

@ManagedBean(name = "vistaFacturaDoctorBean")
@ViewScoped
public class VistaFacturaDoctorBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura=0;
	private FacturaDoctor facturaDoctor;
	private List<DetalleFacturaDoctor> detalles;
	private FacturaDoctorDAO facturaDAO;


	
	@ManagedProperty(value="#{buscarFacturaDoctorBean}")
	private BuscarFacturaDoctorBean buscarFacturaDoctorBean;
	
	public VistaFacturaDoctorBean()
	{
		facturaDoctor = new FacturaDoctor();
		detalles = new ArrayList<DetalleFacturaDoctor>();
		
	}
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaDoctorDAO();
		idFactura = buscarFacturaDoctorBean.getIdFacturaSelected();
		cargarFactura();
		
	}
	
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			idFactura = buscarFacturaDoctorBean.getIdFacturaSelected();
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
