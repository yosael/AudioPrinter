package com.sv.audiomed.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.CreditoFiscalDoctorDAO;
import com.sv.audiomed.model.CreditoFiscalDoctor;
import com.sv.audiomed.model.DetalleCreditoFiscalDoctor;
import com.sv.audiomed.util.LetrasConverter;
import com.sv.audiomed.util.Util;

@ManagedBean
@ViewScoped
public class GenerarCreditoFiscalDoctorBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private CreditoFiscalDoctorDAO facturaDAO;
	private CreditoFiscalDoctor factura;
	private DetalleCreditoFiscalDoctor detalle;
	private List<DetalleCreditoFiscalDoctor> detalles;
	private int idFactura;
	private String tipoConcepto;
	private boolean aplicarIvaRetenido;
	
	@ManagedProperty(value="#{buscarCreditoFiscalDoctorBean}")
	private BuscarCreditoFiscalDoctorBean buscarCreditoFiscalDoctorBean;
	
	public GenerarCreditoFiscalDoctorBean()
	{
		detalles = new ArrayList<DetalleCreditoFiscalDoctor>();
		aplicarIvaRetenido=false;
		
	}
	
	@PostConstruct
	public void init()
	{
		facturaDAO = new CreditoFiscalDoctorDAO();
		/*inicializarFactura();
		inicializarDetalle();
		cargarFactura();*/
	}
	
	public void inicializarDetalle()
	{
		detalle = new DetalleCreditoFiscalDoctor();
		detalle.setCantidad(1);
		detalle.setVentasNoSujetas(0d);
		detalle.setVentasExentas(0d);
		detalle.setVentasGravadas(0d);
		
		
	}
	
	public void inicializarFactura()
	{
		
		factura = new CreditoFiscalDoctor();
		factura.setFecha(new Date());
		factura.setSumaNoSujetas(0d);
		factura.setSumaVentasExentas(0d);
		factura.setSumaVentasGravadas(0d);
		factura.setVentasExentas(0d);
		factura.setVentasNoSujetas(0d);
		factura.setPorcentIva(0d);
		factura.setSubtotal(0d);
		factura.setIvaRetenido(0d);
		factura.setVentaTotal(0d);
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
			
			//idFactura = buscarCreditoFiscalDoctorBean.getIdFacturaSelected();
			factura = facturaDAO.buscarFacturaPorId(idFactura);
			factura.setCodigoFactura("");
			factura.setFecha(new Date());
			detalles = facturaDAO.buscarDetallesFactura(idFactura);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
				return "buscarCreditoFiscalDoctor.xhtml?faces-redirect=true";//return "vistafactura.xhtml?faces-redirect=true&idFactura="+idFactura+"";
			
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public void aplicarConcepto()
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(tipoConcepto.equals("NoSujetas"))
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			factura.setSumaNoSujetas(moneyDecimal(factura.getSumaNoSujetas()+monto));
			factura.setVentasNoSujetas(factura.getSumaNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			factura.setSumaVentasExentas(moneyDecimal(factura.getSumaVentasExentas()+monto));
			factura.setVentasExentas(factura.getSumaVentasExentas());
			
		}
		else
		{
			monto = moneyDecimal(monto);
			monto = recudirIvaAdetalle(monto);// Se aplican precios sin iva
			
			detalle.setPrecioUnitario(recudirIvaAdetalle(detalle.getPrecioUnitario()));
			
			detalle.setVentasGravadas(monto);
			factura.setSumaVentasGravadas(moneyDecimal(factura.getSumaVentasGravadas()+monto));
			
		}
		
		
	}
	
	public Double recudirIvaAdetalle(Double monto)
	{
		Double sinIva=moneyDecimal(monto/1.13);
		return sinIva;
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
	
	public void quitarConceptoAplicado(DetalleCreditoFiscalDoctor detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			factura.setSumaNoSujetas(moneyDecimal(factura.getSumaNoSujetas()-monto));
			factura.setVentasNoSujetas(factura.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			factura.setSumaVentasExentas(moneyDecimal(factura.getSumaVentasExentas()-monto));
			factura.setVentasExentas(factura.getSumaVentasExentas());
			
		}
		else
		{
			monto = moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			factura.setSumaVentasGravadas(moneyDecimal(factura.getSumaVentasGravadas()-monto));
			
		}
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=factura.getSumaVentasGravadas()+factura.getVentasExentas()+factura.getVentasNoSujetas();
		double total=0d;
		double iva=0d;
		
		subtotal = moneyDecimal(subtotal);
		factura.setSubtotal(subtotal);
		
		iva=moneyDecimal(factura.getSumaVentasGravadas()*Util.porcentIvaActual());
		
		factura.setPorcentIva(iva);
		
		total=moneyDecimal(subtotal+factura.getVentasExentas()+factura.getVentasNoSujetas()+iva);
		factura.setVentaTotal(total);
		
		if(aplicarIvaRetenido==true)
		{
			aplicarIvaRetenido();
		}
	}
	
	public void quitarDetalle(DetalleCreditoFiscalDoctor detalle)
	{
		quitarConceptoAplicado(detalle);
		actualizarTotales();
		convertirNumerosALetras();
		detalles.remove(detalle);
	}
	
	public void convertirNumerosALetras()
	{
		LetrasConverter convertidor = new LetrasConverter();
		String numeroLetras = convertidor.convertir(factura.getVentaTotal());
		factura.setLetrasMonto(numeroLetras);
	}
	
	public Double moneyDecimal(Double num) {
		return new Long(Math.round(num*100))/100.0;
	}
	
	
	public void aplicarIvaRetenido()
	{
		double ivaRetenido=factura.getSumaVentasGravadas()/1.13*0.01;
		double total=factura.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total-ivaRetenido);
		
		factura.setIvaRetenido(ivaRetenido);
		factura.setVentaTotal(total);
	}
	
	public void quitarIveRetenido()
	{
		double ivaRetenido=factura.getSumaVentasGravadas()/1.13*0.01;
		double total=factura.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total+ivaRetenido);
		
		factura.setIvaRetenido(0d);
		factura.setVentaTotal(total);
	}
	
	public void verificarAplicacionIveRetenido()
	{
		
		System.out.println("Estado de booleano "+aplicarIvaRetenido);
		
		if(aplicarIvaRetenido==true)
		{
			aplicarIvaRetenido();
			
		}
		else if(aplicarIvaRetenido==false)
		{
			quitarIveRetenido();
		}
		
		convertirNumerosALetras();
	}
	
	
	
	
	//Getters and setters
	

	public CreditoFiscalDoctor getFactura() {
		return factura;
	}

	public void setFactura(CreditoFiscalDoctor factura) {
		this.factura = factura;
	}

	public DetalleCreditoFiscalDoctor getDetalle() {
		return detalle;
	}

	public void setDetalle(DetalleCreditoFiscalDoctor detalle) {
		this.detalle = detalle;
	}

	public List<DetalleCreditoFiscalDoctor> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCreditoFiscalDoctor> detalles) {
		this.detalles = detalles;
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

	public boolean isAplicarIvaRetenido() {
		return aplicarIvaRetenido;
	}

	public void setAplicarIvaRetenido(boolean aplicarIvaRetenido) {
		this.aplicarIvaRetenido = aplicarIvaRetenido;
	}

	public BuscarCreditoFiscalDoctorBean getBuscarCreditoFiscalDoctorBean() {
		return buscarCreditoFiscalDoctorBean;
	}

	public void setBuscarCreditoFiscalDoctorBean(BuscarCreditoFiscalDoctorBean buscarCreditoFiscalDoctorBean) {
		this.buscarCreditoFiscalDoctorBean = buscarCreditoFiscalDoctorBean;
	}
	

	
	
	
}
