package com.sv.audiomed.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sv.audiomed.dao.FacturaAudiomedDAO;
import com.sv.audiomed.model.DetalleFacturaAudiomed;
import com.sv.audiomed.model.FacturaAudiomed;

@ManagedBean(name = "facturacionBean")
@SessionScoped
public class FacturacionBean {
	
	private String formatoSelected;
	
	
	
	public String seleccionarFormato()
	{
		formatoSelected+="?faces-redirect=true";
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
