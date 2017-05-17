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

import com.sv.audiomed.dao.FacturaSvTradeDAO;
import com.sv.audiomed.model.DetalleFacturaSvTrade;
import com.sv.audiomed.model.FacturaSvTrade;

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
		cargarFactura();
		
	}
	
	
	//Obtener id de factura por url
	public void cargarFactura()
	{
		
		try {
			
			idFactura = buscarFacturaSvTradeBean.getIdFacturaSelected();
			
			factura = facturaDAO.buscarFacturaPorId(idFactura);
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			
			
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
