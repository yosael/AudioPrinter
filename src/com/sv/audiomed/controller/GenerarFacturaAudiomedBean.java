package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;
import com.sv.audiomed.util.LetrasConverter;
import com.sv.audiomed.util.Util;

@ManagedBean(name="generarFacturaAudiomedBean")
@RequestScoped
public class GenerarFacturaAudiomedBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int idFactura;
	private FacturaAudiomed factura;
	private List<DetalleFacturaAudiomed> detalles;
	private DetalleFacturaAudiomed detalle;
	private FacturaAudiomedDAO facturaDAO;
	private String tipoConcepto;

	@ManagedProperty(value="#{vistaFacturaAudiomedBean}")
	private VistaFacturaAudiomedBean vistaFacturaAudiomedBean;
	
	
	public GenerarFacturaAudiomedBean()
	{
		factura = new FacturaAudiomed();
		detalles = new ArrayList<DetalleFacturaAudiomed>();
	}


	@PostConstruct
	public void init()
	{
		idFactura = vistaFacturaAudiomedBean.getIdFactura();
		facturaDAO = new FacturaAudiomedDAO();
		
		cargarFactura();
		
	}
	
	public void cargarFactura()
	{
		
		try {
			
			this.factura = facturaDAO.buscarFacturaPorId(idFactura);
			factura.setCodigoFactura("");
			this.detalles = facturaDAO.buscarDetallesFactura(idFactura);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String agregarFactura()
	{

		if(!validarFacturaAudiomed())
			return "";
		
		
		try {
			
			idFactura= facturaDAO.agregarFacturaDetalle(factura, detalles);
			
			if(idFactura==0) 
				return "";
			else
				return "buscarFacturaAudiomed.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	
	public boolean validarFacturaAudiomed()
	{
		
		
		
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
	
	public void quitarConceptoAplicado(DetalleFacturaAudiomed detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			factura.setSumaNoSujetas(factura.getSumaNoSujetas()-monto);
			factura.setVentasNoSujetas(factura.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =Util.moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			factura.setSumaVentasExentas(factura.getSumaVentasExentas()-monto);
			factura.setVentasExentas(factura.getSumaVentasExentas());
			
		}
		else
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			factura.setSumaVentasGravadas(factura.getSumaVentasGravadas()-monto);
			
		}
	}
	
	public void quitarDetalle(DetalleFacturaAudiomed detalle)
	{
		quitarConceptoAplicado(detalle);
		actualizarTotales();
		convertirNumerosALetras();
		detalles.remove(detalle);
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=factura.getSumaVentasGravadas()+factura.getVentasExentas()+factura.getVentasNoSujetas();
		subtotal = Util.moneyDecimal(subtotal);
		System.out.println("Subtotal"+subtotal);
		factura.setSubtotal(subtotal);
		
		double ivaRetenido=factura.getSumaVentasGravadas()*(0.13f);
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		System.out.println("IVA RETENIDO"+ivaRetenido);
		factura.setIvaRetenido(ivaRetenido);
		
		factura.setVentaTotal(0d);
		factura.setVentaTotal(Util.moneyDecimal(subtotal+ivaRetenido));
	}
	
	
	public void convertirNumerosALetras()
	{
		LetrasConverter convertidor = new LetrasConverter();
		String numeroLetras = convertidor.convertir(factura.getVentaTotal());
		factura.setLetrasMonto(numeroLetras);
	}
	
	
	public VistaFacturaAudiomedBean getVistaFacturaAudiomedBean() {
		return vistaFacturaAudiomedBean;
	}


	public void setVistaFacturaAudiomedBean(VistaFacturaAudiomedBean vistaFacturaAudiomedBean) {
		this.vistaFacturaAudiomedBean = vistaFacturaAudiomedBean;
	}


	public int getIdFactura() {
		return idFactura;
	}


	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}


	public FacturaAudiomed getFactura() {
		return factura;
	}


	public void setFactura(FacturaAudiomed factura) {
		this.factura = factura;
	}


	public List<DetalleFacturaAudiomed> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetalleFacturaAudiomed> detalles) {
		this.detalles = detalles;
	}


	public DetalleFacturaAudiomed getDetalle() {
		return detalle;
	}


	public void setDetalle(DetalleFacturaAudiomed detalle) {
		this.detalle = detalle;
	}


	public String getTipoConcepto() {
		return tipoConcepto;
	}


	public void setTipoConcepto(String tipoConcepto) {
		this.tipoConcepto = tipoConcepto;
	}
	
	
	
	
	

}
