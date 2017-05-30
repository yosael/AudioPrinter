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


import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;
import com.sv.audiomed.util.LetrasConverter;
import com.sv.audiomed.util.Util;


@ManagedBean(name = "facturaAudiomedBean")
@ViewScoped
public class FacturaAudiomedBean {
	
	private FacturaAudiomed facturaAudiomed;
	private FacturaAudiomedDAO facturaAudiomedDAO;
	private List<DetalleFacturaAudiomed> detalles;
	private DetalleFacturaAudiomed detalle;
	private int idFactura;
	private String tipoConcepto;
	private boolean aplicarIvaRetenido;
	
	@ManagedProperty(value="#{buscarFacturaAudiomedBean}")
	private BuscarFacturaAudiomedBean buscarFacturaAudiomedBean;
	
	
	public FacturaAudiomedBean() {
		
		idFactura=0;
		aplicarIvaRetenido = false;
		tipoConcepto="";
		detalles = new ArrayList<DetalleFacturaAudiomed>();
		
	}
	
	@PostConstruct
	public void init()
	{
		facturaAudiomedDAO = new FacturaAudiomedDAO();
		inicializarFactura();
		inicializarDetalle();
		//cargarFactura();
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
	
	public void cargarFactura()
	{
		
		try {
			
			idFactura= buscarFacturaAudiomedBean.getIdFacturaSelected();
			
			if(idFactura>0)
			{
				this.facturaAudiomed = facturaAudiomedDAO.buscarFacturaPorId(idFactura);
				facturaAudiomed.setCodigoFactura("");
				facturaAudiomed.setFecha(new Date());
				this.detalles = facturaAudiomedDAO.buscarDetallesFactura(idFactura);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
			monto =Util.moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaAudiomed.setSumaNoSujetas(Util.moneyDecimal(facturaAudiomed.getSumaNoSujetas()+monto));
			facturaAudiomed.setVentasNoSujetas(facturaAudiomed.getSumaNoSujetas());
		}
		else if(tipoConcepto.equals("Exentas"))
		{
			monto =Util.moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaAudiomed.setSumaVentasExentas(Util.moneyDecimal(facturaAudiomed.getSumaVentasExentas()+monto));
			facturaAudiomed.setVentasExentas(facturaAudiomed.getSumaVentasExentas());
			
		}
		else if(tipoConcepto.equals("Gravadas"))
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(Util.moneyDecimal(facturaAudiomed.getSumaVentasGravadas()+monto));
			
		}
		/*else
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(facturaAudiomed.getSumaVentasGravadas()+monto);
		}*/
		
		
	}
	
	public void actualizarTotales()
	{
		
		double subtotal=facturaAudiomed.getSumaVentasGravadas()+facturaAudiomed.getVentasExentas()+facturaAudiomed.getVentasNoSujetas();
		double total=0d;
		
		subtotal = Util.moneyDecimal(subtotal);
		facturaAudiomed.setSubtotal(subtotal);
		
		total=subtotal+facturaAudiomed.getVentasExentas()+facturaAudiomed.getVentasNoSujetas();//Revisar
		
		facturaAudiomed.setVentaTotal(Util.moneyDecimal(total));
		
		if(aplicarIvaRetenido==true)
		{
			aplicarIvaRetenido();
		}

	}
	
	public void quitarConceptoAplicado(DetalleFacturaAudiomed detalle)
	{
		double monto =0f;
		monto=detalle.getCantidad()*detalle.getPrecioUnitario();
		
		if(detalle.getVentasNoSujetas()>0)
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasNoSujetas(monto);
			facturaAudiomed.setSumaNoSujetas(Util.moneyDecimal(facturaAudiomed.getSumaNoSujetas()-monto));
			facturaAudiomed.setVentasNoSujetas(facturaAudiomed.getSumaNoSujetas());
		}
		else if(detalle.getVentasExentas()>0)
		{
			monto =Util.moneyDecimal(monto);
			
			detalle.setVentasExentas(monto);
			facturaAudiomed.setSumaVentasExentas(Util.moneyDecimal(facturaAudiomed.getSumaVentasExentas()-monto));
			facturaAudiomed.setVentasExentas(facturaAudiomed.getSumaVentasExentas());
			
		}
		else
		{
			monto =Util.moneyDecimal(monto);
			detalle.setVentasGravadas(monto);
			facturaAudiomed.setSumaVentasGravadas(facturaAudiomed.getSumaVentasGravadas()-monto);
			
		}
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
	
	public void aplicarIvaRetenido()
	{
		double ivaRetenido=facturaAudiomed.getSumaVentasGravadas()/1.13*0.01;
		double total=facturaAudiomed.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total-ivaRetenido);
		
		facturaAudiomed.setIvaRetenido(ivaRetenido);
		facturaAudiomed.setVentaTotal(total);
	}
	
	public void quitarIvaRetenido()
	{
		double ivaRetenido=facturaAudiomed.getSumaVentasGravadas()/1.13*0.01;
		double total=facturaAudiomed.getVentaTotal();
		
		ivaRetenido = Util.moneyDecimal(ivaRetenido);
		total= Util.moneyDecimal(total+ivaRetenido);
		
		facturaAudiomed.setIvaRetenido(0d);
		facturaAudiomed.setVentaTotal(total);
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

	public BuscarFacturaAudiomedBean getBuscarFacturaAudiomedBean() {
		return buscarFacturaAudiomedBean;
	}

	public void setBuscarFacturaAudiomedBean(BuscarFacturaAudiomedBean buscarFacturaAudiomedBean) {
		this.buscarFacturaAudiomedBean = buscarFacturaAudiomedBean;
	}

	public boolean isAplicarIvaRetenido() {
		return aplicarIvaRetenido;
	}

	public void setAplicarIvaRetenido(boolean aplicarIvaRetenido) {
		this.aplicarIvaRetenido = aplicarIvaRetenido;
	}



	
	
	
	
	
	
	
	
	

}
