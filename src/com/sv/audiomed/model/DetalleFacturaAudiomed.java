package com.sv.audiomed.model;

public class DetalleFacturaAudiomed {
	
	private int idDetalle;
	private int idFactura;
	private int cantidad;
	private String descripciones;
	private Float precioUnitario;
	private Float ventasNoSujetas;
	private Float ventasExentas;
	private Float ventasGravadas;
	
	
	
	
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
	public Float getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public Float getVentasNoSujetas() {
		return ventasNoSujetas;
	}
	public void setVentasNoSujetas(Float ventasNoSujetas) {
		this.ventasNoSujetas = ventasNoSujetas;
	}
	public Float getVentasExentas() {
		return ventasExentas;
	}
	public void setVentasExentas(Float ventasExentas) {
		this.ventasExentas = ventasExentas;
	}
	public Float getVentasGravadas() {
		return ventasGravadas;
	}
	public void setVentasGravadas(Float ventasGravadas) {
		this.ventasGravadas = ventasGravadas;
	}
	
	
	

}
