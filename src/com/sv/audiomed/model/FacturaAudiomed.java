package com.sv.audiomed.model;

import java.util.Date;

public class FacturaAudiomed {
	
	private int idFactura;
	private String codigoFactura;
	private String nombreCliente;
	private String direccionCliente;
	private Date fecha;
	private String docCliente;
	private Float sumaNoSujetas;
	private Float sumaVentasExentas;
	private Float sumaVentasGravadas;
	private Float ventasExentas;
	private Float ventasNoSujetas;
	private Float subtotal;
	private Float ivaRetenido;
	private Float ventaTotal;
	private String letrasMonto;
	private String vtaActa;
	
	
	
	
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public String getCodigoFactura() {
		return codigoFactura;
	}
	public void setCodigoFactura(String codigoFactura) {
		this.codigoFactura = codigoFactura;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getDireccionCliente() {
		return direccionCliente;
	}
	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDocCliente() {
		return docCliente;
	}
	public void setDocCliente(String docCliente) {
		this.docCliente = docCliente;
	}
	public Float getSumaNoSujetas() {
		return sumaNoSujetas;
	}
	public void setSumaNoSujetas(Float sumaNoSujetas) {
		this.sumaNoSujetas = sumaNoSujetas;
	}
	public Float getSumaVentasExentas() {
		return sumaVentasExentas;
	}
	public void setSumaVentasExentas(Float sumaVentasExentas) {
		this.sumaVentasExentas = sumaVentasExentas;
	}
	public Float getSumaVentasGravadas() {
		return sumaVentasGravadas;
	}
	public void setSumaVentasGravadas(Float sumaVentasGravadas) {
		this.sumaVentasGravadas = sumaVentasGravadas;
	}
	public Float getVentasExentas() {
		return ventasExentas;
	}
	public void setVentasExentas(Float ventasExentas) {
		this.ventasExentas = ventasExentas;
	}
	public Float getVentasNoSujetas() {
		return ventasNoSujetas;
	}
	public void setVentasNoSujetas(Float ventasNoSujetas) {
		this.ventasNoSujetas = ventasNoSujetas;
	}
	public Float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}
	public Float getIvaRetenido() {
		return ivaRetenido;
	}
	public void setIvaRetenido(Float ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}
	public Float getVentaTotal() {
		return ventaTotal;
	}
	public void setVentaTotal(Float ventaTotal) {
		this.ventaTotal = ventaTotal;
	}
	public String getLetrasMonto() {
		return letrasMonto;
	}
	public void setLetrasMonto(String letrasMonto) {
		this.letrasMonto = letrasMonto;
	}
	
	
	public String getVtaActa() {
		return vtaActa;
	}
	public void setVtaActa(String vtaActa) {
		this.vtaActa = vtaActa;
	}
	
	
	

}
