package com.sv.audiomed.model;

import java.util.Date;

public class FacturaDoctor {
	
	private int idFactura;
	private String codigoFactura;
	private String nombreCliente;
	private String direccionCliente;
	private Date fecha;
	private String docCliente;
	private String vtaCtaDe;
	private Double sumaNoSujetas;
	private Double sumaVentasExentas;
	private Double sumaVentasGravadas;
	private Double ventasExentas;
	private Double ventasNoSujetas;
	private Double subtotal;
	private Double ivaRetenido;
	private Double ventaTotal;
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
	
	
	public String getVtaCtaDe() {
		return vtaCtaDe;
	}
	public void setVtaCtaDe(String vtaCtaDe) {
		this.vtaCtaDe = vtaCtaDe;
	}
	
	
	
	public Double getSumaNoSujetas() {
		return sumaNoSujetas;
	}
	public void setSumaNoSujetas(Double sumaNoSujetas) {
		this.sumaNoSujetas = sumaNoSujetas;
	}
	public Double getSumaVentasExentas() {
		return sumaVentasExentas;
	}
	public void setSumaVentasExentas(Double sumaVentasExentas) {
		this.sumaVentasExentas = sumaVentasExentas;
	}
	public Double getSumaVentasGravadas() {
		return sumaVentasGravadas;
	}
	public void setSumaVentasGravadas(Double sumaVentasGravadas) {
		this.sumaVentasGravadas = sumaVentasGravadas;
	}
	public Double getVentasExentas() {
		return ventasExentas;
	}
	public void setVentasExentas(Double ventasExentas) {
		this.ventasExentas = ventasExentas;
	}
	public Double getVentasNoSujetas() {
		return ventasNoSujetas;
	}
	public void setVentasNoSujetas(Double ventasNoSujetas) {
		this.ventasNoSujetas = ventasNoSujetas;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Double getIvaRetenido() {
		return ivaRetenido;
	}
	public void setIvaRetenido(Double ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}
	public Double getVentaTotal() {
		return ventaTotal;
	}
	public void setVentaTotal(Double ventaTotal) {
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
