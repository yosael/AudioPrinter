package com.sv.audiomed.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.FacturaDoctorDAO;
import com.sv.audiomed.model.DetalleFacturaDoctor;
import com.sv.audiomed.model.FacturaDoctor;
import com.sv.audiomed.util.LetrasConverter;
import com.sv.audiomed.util.Util;

@ManagedBean(name = "generarFacturaDoctorBean")
@ViewScoped
public class GenerarFacturaDoctorBean {
	
	private FacturaDoctor facturaDoctor;
	private FacturaDoctorDAO facturaDoctorDAO;
	private List<DetalleFacturaDoctor> detalles;
	private DetalleFacturaDoctor detalle;
	private int idFactura;
	private String tipoConcepto;
	private boolean aplicarIvaRetenido;
	
	
	@ManagedProperty(value="#{buscarFacturaDoctorBean}")
	private BuscarFacturaDoctorBean buscarFacturaDoctorBean;
	
	public GenerarFacturaDoctorBean() {
		
		detalles = new ArrayList<DetalleFacturaDoctor>();
		idFactura=0;
		tipoConcepto="";
		aplicarIvaRetenido=false;
	}
	
	@PostConstruct
	public void init()
	{
		facturaDoctorDAO = new FacturaDoctorDAO();
		inicializarFactura();
		inicializarDetalle();
		cargarFactura();
	}
	
	public void inicializarDetalle()
	{
		detalle = new DetalleFacturaDoctor();
		detalle.setCantidad(1);
		detalle.setVentasNoSujetas(0d);
		detalle.setVentasExentas(0d);
		detalle.setVentasGravadas(0d);
		
	}
	
	public void inicializarFactura()
	{
		
		facturaDoctor = new FacturaDoctor();
		facturaDoctor.setFecha(new Date());
		facturaDoctor.setSumaNoSujetas(0d);
		facturaDoctor.setSumaVentasExentas(0d);
		facturaDoctor.setSumaVentasGravadas(0d);
		facturaDoctor.setVentasExentas(0d);
		facturaDoctor.setVentasNoSujetas(0d);
		facturaDoctor.setSubtotal(0d);
		facturaDoctor.setIvaRetenido(0d);
		facturaDoctor.setVentaTotal(0d);
	}
	
	public void cargarPorId()
	{
		inicializarFactura();
		inicializarDetalle();
		cargarFactura();
	}
	
	
	public void cargarFactura()
	{
		
		try {
			
			//idFactura = buscarFacturaDoctorBean.getIdFacturaSelected();
			
			if(idFactura>0)
			{
				facturaDoctor = facturaDoctorDAO.buscarFacturaPorId(idFactura);
				facturaDoctor.setCodigoFactura("");
				facturaDoctor.setFecha(new Date());
				detalles = facturaDoctorDAO.buscarDetallesFactura(idFactura);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String agregarFactura()
	{

		if(!validarFacturaDoctor())
			return "";
		
		try {
			
			idFactura= facturaDoctorDAO.agregarFacturaDetalle(facturaDoctor, detalles);
			
			if(idFactura==0)
				return "";
			else
				return "buscarFacturaDoctor.xhtml?faces-redirect=true";//return "vistaFacturaDoctor.xhtml?faces-redirect=true&idFactura="+idFactura+"";
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String agregarDetalleFactura()
	{
		for(DetalleFacturaDoctor detalle: detalles)
		{
			
			detalle.setIdFactura(facturaDoctor.getIdFactura());
		}
		
		return "vistaFacturaDoctor";
	}
	
	public void aplicarConcepto()
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(tipoConcepto.equals("NoSujetas"))
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaDoctor.setSumaNoSujetas(facturaDoctor.getSumaNoSujetas()+monto);
			facturaDoctor.setVentasNoSujetas(facturaDoctor.getSumaNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaDoctor.setSumaVentasExentas(facturaDoctor.getSumaVentasExentas()+monto);
			facturaDoctor.setVentasExentas(facturaDoctor.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaDoctor.setSumaVentasGravadas(facturaDoctor.getSumaVentasGravadas()+monto);
			
		}
		
		
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=facturaDoctor.getSumaVentasGravadas()+facturaDoctor.getVentasExentas()+facturaDoctor.getVentasNoSujetas();
		double total=0d;
		
		subtotal = Util.moneyDecimal(subtotal);
		facturaDoctor.setSubtotal(subtotal);
		
		total=subtotal+facturaDoctor.getVentasExentas()+facturaDoctor.getVentasNoSujetas();//Revisar
		
		facturaDoctor.setVentaTotal(Util.moneyDecimal(total));
		
		if(aplicarIvaRetenido==true)
		{
			aplicarIvaRetenido();
		}
	}
	
	public void quitarConceptoAplicado(DetalleFacturaDoctor detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaDoctor.setSumaNoSujetas(facturaDoctor.getSumaNoSujetas()-monto);
			facturaDoctor.setVentasNoSujetas(facturaDoctor.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaDoctor.setSumaVentasExentas(facturaDoctor.getSumaVentasExentas()-monto);
			facturaDoctor.setVentasExentas(facturaDoctor.getSumaVentasExentas());
			
		}
		else
		{
			monto =moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaDoctor.setSumaVentasGravadas(facturaDoctor.getSumaVentasGravadas()-monto);
			
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
	
	
	public boolean validarFacturaDoctor()
	{
		
		System.out.println("entro a validar");
		System.out.println(facturaDoctor.getCodigoFactura());
		if(facturaDoctor.getCodigoFactura()==null || facturaDoctor.getCodigoFactura().equals(""))
		{
			System.out.println("Entro a factura valida");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el numero de factura" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaDoctor.getNombreCliente()==null || facturaDoctor.getNombreCliente().equals(""))
		{ 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese el nombre del cliente" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaDoctor.getDireccionCliente()==null || facturaDoctor.getDireccionCliente().equals(""))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Ingrese la direccion" ,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}
		
		if(facturaDoctor.getFecha()==null)
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
	
	public void quitarDetalle(DetalleFacturaDoctor detalle)
	{
		quitarConceptoAplicado(detalle);
		actualizarTotales();
		convertirNumerosALetras();
		detalles.remove(detalle);
	}
	
	
	public void convertirNumerosALetras()
	{
		LetrasConverter convertidor = new LetrasConverter();
		String numeroLetras = convertidor.convertir(facturaDoctor.getVentaTotal());
		facturaDoctor.setLetrasMonto(numeroLetras);
	}
	
	
	public void aplicarIvaRetenido()
	{
		double ivaRetenido=facturaDoctor.getSumaVentasGravadas()/1.13*0.01;
		double total=facturaDoctor.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total-ivaRetenido);
		
		facturaDoctor.setIvaRetenido(ivaRetenido);
		facturaDoctor.setVentaTotal(total);
	}
	
	public void quitarIvaRetenido()
	{
		double ivaRetenido=facturaDoctor.getSumaVentasGravadas()/1.13*0.01;
		double total=facturaDoctor.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total+ivaRetenido);
		
		facturaDoctor.setIvaRetenido(0d);
		facturaDoctor.setVentaTotal(total);
	}
	
	public void verificarAplicacionIvaRetenido()
	{
		
		System.out.println("Estado de booleano "+aplicarIvaRetenido);
		
		if(aplicarIvaRetenido==true)
		{
			aplicarIvaRetenido();
			
		}
		else if(aplicarIvaRetenido==false)
		{
			quitarIvaRetenido();
		}
		
		convertirNumerosALetras();
	}
	
	
	
	//Getters and Setters
	
	
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

	public DetalleFacturaDoctor getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleFacturaDoctor detalle) {
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

	public BuscarFacturaDoctorBean getBuscarFacturaDoctorBean() {
		return buscarFacturaDoctorBean;
	}

	public void setBuscarFacturaDoctorBean(BuscarFacturaDoctorBean buscarFacturaDoctorBean) {
		this.buscarFacturaDoctorBean = buscarFacturaDoctorBean;
	}

	public boolean isAplicarIvaRetenido() {
		return aplicarIvaRetenido;
	}

	public void setAplicarIvaRetenido(boolean aplicarIvaRetenido) {
		this.aplicarIvaRetenido = aplicarIvaRetenido;
	}
	
	
	
	

}
