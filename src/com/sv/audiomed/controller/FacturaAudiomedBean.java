package com.sv.audiomed.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.Conexion;
import com.sv.audiomed.dao.DetalleFacturaAudiomedDAO;
import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;
import com.sv.audiomed.util.LetrasConverter;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

@ManagedBean(name = "facturaAudiomedBean")
@ViewScoped
public class FacturaAudiomedBean {
	
	private FacturaAudiomed facturaAudiomed;
	private FacturaAudiomedDAO facturaAudiomedDAO;
	//private DetalleFacturaAudiomedDAO detalleFacturaAudiomedDAO;
	private List<DetalleFacturaAudiomed> detalles = new ArrayList<DetalleFacturaAudiomed>();
	private DetalleFacturaAudiomed detalle;
	private int idFactura=0;
	private String tipoConcepto="";
	
	
	
	@PostConstruct
	public void init()
	{
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		inicializarFactura();
		inicializarDetalle();
	}
	
	public void inicializarDetalle()
	{
		detalle = new DetalleFacturaAudiomed();
		detalle.setCantidad(1);
		detalle.setVentasNoSujetas(0d);
		detalle.setVentasExentas(0d);
		detalle.setVentasGravadas(0d);
		
	}
	
	public void inicializarFactura()
	{
		
		facturaAudiomed = new FacturaAudiomed();
		facturaAudiomed.setFecha(new Date());
		facturaAudiomed.setSumaNoSujetas(0d);
		facturaAudiomed.setSumaVentasExentas(0d);
		facturaAudiomed.setSumaVentasGravadas(0d);
		facturaAudiomed.setVentasExentas(0d);
		facturaAudiomed.setVentasNoSujetas(0d);
		facturaAudiomed.setSubtotal(0d);
		facturaAudiomed.setIvaRetenido(0d);
		facturaAudiomed.setVentaTotal(0d);
	}
	
	
	public String agregarFactura()
	{

		if(!validarFacturaAudiomed())
			return "";
		
		
		try {
			
			idFactura= facturaAudiomedDAO.agregarFacturaDetalle(facturaAudiomed, detalles);
			System.out.println("ID FACTURA2 "+idFactura);
			
			if(idFactura==0)
				return "";
			else
				return "buscarFacturaAudiomed.xhtml?faces-redirect=true";//return "vistaFacturaAudiomed.xhtml?faces-redirect=true&idFactura="+idFactura+"";
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String agregarDetalleFactura()
	{
		for(DetalleFacturaAudiomed detalle: detalles)
		{
			
			detalle.setIdFactura(facturaAudiomed.getIdFactura());
		}
		
		return "vistaFacturaAudiomed";
	}
	
	public void aplicarConcepto()
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(tipoConcepto.equals("NoSujetas"))
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaAudiomed.setSumaNoSujetas(facturaAudiomed.getSumaNoSujetas()+monto);
			facturaAudiomed.setVentasNoSujetas(facturaAudiomed.getSumaNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaAudiomed.setSumaVentasExentas(facturaAudiomed.getSumaVentasExentas()+monto);
			facturaAudiomed.setVentasExentas(facturaAudiomed.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(facturaAudiomed.getSumaVentasGravadas()+monto);
			
		}
		
		
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=facturaAudiomed.getSumaVentasGravadas()+facturaAudiomed.getVentasExentas()+facturaAudiomed.getVentasNoSujetas();
		subtotal = moneyDecimal(subtotal);
		System.out.println("Subtotal"+subtotal);
		facturaAudiomed.setSubtotal(subtotal);
		
		double ivaRetenido=facturaAudiomed.getSumaVentasGravadas()*(0.13f);
		ivaRetenido = moneyDecimal(ivaRetenido);
		System.out.println("IVA RETENIDO"+ivaRetenido);
		facturaAudiomed.setIvaRetenido(ivaRetenido);
		
		facturaAudiomed.setVentaTotal(0d);
		facturaAudiomed.setVentaTotal(moneyDecimal(subtotal+ivaRetenido));
	}
	
	public void quitarConceptoAplicado(DetalleFacturaAudiomed detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaAudiomed.setSumaNoSujetas(facturaAudiomed.getSumaNoSujetas()-monto);
			facturaAudiomed.setVentasNoSujetas(facturaAudiomed.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaAudiomed.setSumaVentasExentas(facturaAudiomed.getSumaVentasExentas()-monto);
			facturaAudiomed.setVentasExentas(facturaAudiomed.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(facturaAudiomed.getSumaVentasGravadas()-monto);
			
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
		convertirNumerosALetras();
		detalles.add(detalle);
		inicializarDetalle();
		
		System.out.println("Tamanio de lista detalle "+ detalles.size() );
	}
	
	
	public boolean validarFacturaAudiomed()
	{
		
		System.out.println("entro a validar");
		System.out.println(facturaAudiomed.getCodigoFactura());
		if(facturaAudiomed.getCodigoFactura()==null || facturaAudiomed.getCodigoFactura().equals(""))
		{
			System.out.println("Entro a factura valida");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el numero de factura" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaAudiomed.getNombreCliente()==null || facturaAudiomed.getNombreCliente().equals(""))
		{ 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el nombre del cliente" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaAudiomed.getDireccionCliente()==null || facturaAudiomed.getDireccionCliente().equals(""))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese la direccion" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaAudiomed.getFecha()==null)
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
	
	public void quitarDetalle(DetalleFacturaAudiomed detalle)
	{
		quitarConceptoAplicado(detalle);
		actualizarTotales();
		convertirNumerosALetras();
		detalles.remove(detalle);
	}
	
	
	public void convertirNumerosALetras()
	{
		LetrasConverter convertidor = new LetrasConverter();
		String numeroLetras = convertidor.convertir(facturaAudiomed.getVentaTotal());
		facturaAudiomed.setLetrasMonto(numeroLetras);
	}
	
	
	
	
	
	
	//Getters and Setters
	
	
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

	public DetalleFacturaAudiomed getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleFacturaAudiomed detalle) {
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
