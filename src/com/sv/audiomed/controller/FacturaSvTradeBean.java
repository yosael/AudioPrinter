package com.sv.audiomed.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.FacturaSvTradeDAO;
import com.sv.audiomed.model.DetalleFacturaSvTrade;
import com.sv.audiomed.model.FacturaSvTrade;

@ManagedBean(name = "facturaSvTradeBean")
@ViewScoped
public class FacturaSvTradeBean {
	
	private FacturaSvTrade factura;
	private FacturaSvTradeDAO facturaDAO;
	//private DetallefacturaDAO detallefacturaDAO;
	private List<DetalleFacturaSvTrade> detalles = new ArrayList<DetalleFacturaSvTrade>();
	private DetalleFacturaSvTrade detalle;
	private int idFactura=0;
	private String tipoConcepto="";
	
	
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new FacturaSvTradeDAO();
		inicializarFactura();
		inicializarDetalle();
	}
	
	public void inicializarDetalle()
	{
		detalle = new DetalleFacturaSvTrade();
		detalle.setCantidad(1);
		detalle.setVentasNoSujetas(0d);
		detalle.setVentasExentas(0d);
		detalle.setVentasGravadas(0d);
		
	}
	
	public void inicializarFactura()
	{
		
		factura = new FacturaSvTrade();
		factura.setFecha(new Date());
		factura.setSumaNoSujetas(0d);
		factura.setSumaVentasExentas(0d);
		factura.setSumaVentasGravadas(0d);
		factura.setVentasExentas(0d);
		factura.setVentasNoSujetas(0d);
		factura.setSubtotal(0d);
		factura.setIvaRetenido(0d);
		factura.setVentaTotal(0d);
	}
	
	
	public String agregarFactura()
	{

		if(!validarfactura())
			return "";
		
		
		try {
			
			idFactura= facturaDAO.agregarFacturaDetalle(factura, detalles);
			System.out.println("ID FACTURA2 "+idFactura);
			
			if(idFactura==0)
				return "";
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info", "Factura Ingresada"));
				
				return "buscarFacturaSvTrade.xhtml?faces-redirect=true";//return "vistafactura.xhtml?faces-redirect=true&idFactura="+idFactura+"";
			}
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String agregarDetalleFactura()
	{
		for(DetalleFacturaSvTrade detalle: detalles)
		{
			
			detalle.setIdFactura(factura.getIdFactura());
		}
		
		return "vistafactura";
	}
	
	public void aplicarConcepto()
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(tipoConcepto.equals("NoSujetas"))
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			factura.setSumaNoSujetas(factura.getSumaNoSujetas()+monto);
			factura.setVentasNoSujetas(factura.getSumaNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			factura.setSumaVentasExentas(factura.getSumaVentasExentas()+monto);
			factura.setVentasExentas(factura.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			factura.setSumaVentasGravadas(factura.getSumaVentasGravadas()+monto);
			
		}
		
		
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=factura.getSumaVentasGravadas()+factura.getVentasExentas()+factura.getVentasNoSujetas();
		subtotal = moneyDecimal(subtotal);
		System.out.println("Subtotal"+subtotal);
		factura.setSubtotal(subtotal);
		
		double ivaRetenido=factura.getSumaVentasGravadas()*(0.13f);
		ivaRetenido = moneyDecimal(ivaRetenido);
		System.out.println("IVA RETENIDO"+ivaRetenido);
		factura.setIvaRetenido(ivaRetenido);
		
		factura.setVentaTotal(0d);
		factura.setVentaTotal(subtotal+ivaRetenido);
	}
	
	public void quitarConceptoAplicado(DetalleFacturaSvTrade detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			factura.setSumaNoSujetas(factura.getSumaNoSujetas()-monto);
			factura.setVentasNoSujetas(factura.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			factura.setSumaVentasExentas(factura.getSumaVentasExentas()-monto);
			factura.setVentasExentas(factura.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			factura.setSumaVentasGravadas(factura.getSumaVentasGravadas()-monto);
			
		}
	}
	
	public Double moneyDecimal(Double num) {
		return new Long(Math.round(num*100))/100.0;
	}
	
	
	public void cargarDetalle()
	{
		
		if(!validarDetalle())
			return;
		
		aplicarConcepto();
		actualizarTotales();
		detalles.add(detalle);
		inicializarDetalle();
		
		System.out.println("Tamanio de lista detalle "+ detalles.size() );
	}
	
	
	public boolean validarfactura()
	{
		
		System.out.println("entro a validar");
		System.out.println(factura.getCodigoFactura());
		if(factura.getCodigoFactura()==null || factura.getCodigoFactura().equals(""))
		{
			System.out.println("Entro a factura valida");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el numero de factura" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(factura.getNombreCliente()==null || factura.getNombreCliente().equals(""))
		{ 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el nombre del cliente" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(factura.getDireccionCliente()==null || factura.getDireccionCliente().equals(""))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese la direccion" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(factura.getFecha()==null)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese la fecha" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(detalles.size()<=0)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Agregar detalle" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		return true;
	}
	
	public boolean validarDetalle()
	{
		
		if(detalle.getDescripciones()==null || detalle.getDescripciones().equals(""))
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese la descripcion",null));
			return false;
		}
		
		if(detalle.getPrecioUnitario()==null || detalle.getPrecioUnitario()<=0)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el precio",null));
			
			return false;
		}
		
		if(tipoConcepto==null || tipoConcepto.equals(""))
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccionar tipo de venta",null));
			return false;
		}
		
		
		
		return true;
	}
	
	public void quitarDetalle(DetalleFacturaSvTrade detalle)
	{
		quitarConceptoAplicado(detalle);
		actualizarTotales();
		detalles.remove(detalle);
	}
	
	
	
	//Getters and Setters
	
	
	public FacturaSvTrade getfactura() {
		return factura;
	}

	public void setfactura(FacturaSvTrade factura) {
		this.factura = factura;
	}

	public List<DetalleFacturaSvTrade> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFacturaSvTrade> detalles) {
		this.detalles = detalles;
	}

	public DetalleFacturaSvTrade getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleFacturaSvTrade detalle) {
		this.detalle = detalle;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public String getTipoConcepto() {
		return tipoConcepto;
	}

	public void setTipoConcepto(String tipoConcepto) {
		this.tipoConcepto = tipoConcepto;
	}
	
	
	
	

}
