package com.sv.audiomed.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean(name = "facturacionBean")
@SessionScoped
public class FacturacionBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String formatoSelected;
	
	
	
	public String seleccionarFormato()
	{
		return formatoSelected;
	}
	

	
	
	//Metodos getter and setter
	
	public String getFormatoSelected() {
		return formatoSelected;
	}


	public void setFormatoSelected(String formatoSelected) {
		this.formatoSelected = formatoSelected;
	}

	
	

}
