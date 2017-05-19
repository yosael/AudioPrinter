package com.sv.audiomed.model;

public class DetalleCreditoFiscalSvTrade {
	
	private int idDetalle;
	private int idFactura;
	private int cantidad;
	private String descripciones;
	private Double precioUnitario;
	private Double ventasNoSujetas;
	private Double ventasExentas;
	private Double ventasGravadas;
	
	
	
	public int getIdDetalle() {
		return idDetalle;
	}
	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripciones() {
		return descripciones;
	}
	public void setDescripciones(String descripciones) {
		this.descripciones = descripciones;
	}
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public Double getVentasNoSujetas() {
		return ventasNoSujetas;
	}
	public void setVentasNoSujetas(Double ventasNoSujetas) {
		this.ventasNoSujetas = ventasNoSujetas;
	}
	public Double getVentasExentas() {
		return ventasExentas;
	}
	public void setVentasExentas(Double ventasExentas) {
		this.ventasExentas = ventasExentas;
	}
	public Double getVentasGravadas() {
		return ventasGravadas;
	}
	public void setVentasGravadas(Double ventasGravadas) {
		this.ventasGravadas = ventasGravadas;
	}
	
	
	

}
