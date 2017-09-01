package com.sv.audiomed.controller;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import sun.java2d.cmm.Profile;

@ManagedBean(name="sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idUsuario;
	
	
	@PostConstruct
	public void init()
	{
		this.autenticar();
	}
	
	public void autenticar()
	{
		
		idUsuario = (Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		
		try {
			
			if(idUsuario==null)
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?faces-redirect=true");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	

}
