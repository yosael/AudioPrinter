package com.sv.audiomed.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sv.audiomed.dao.LoginDAO;

@ManagedBean(name="loginBean")
@javax.faces.bean.SessionScoped
public class LoginBean {
	
	private String username;
	private String pass;
	
	private LoginDAO loginDAO;
	
	@PostConstruct
	public void init()
	{
		loginDAO = new LoginDAO();
	}
	
	
	public String validarUsuario()
	{
		System.out.println("Entro a validar usuario");
		try {
			
			Integer idUsuario=null;
			
			idUsuario = loginDAO.validarUsuario(username, pass);
			
			if(idUsuario!=null)
			{
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario", idUsuario);
				
				System.out.println("usuario valido");
				
				return "seleccionarFormato.xhtml";
			}
			else
			{
				System.out.println("usuario noo valido");
				//FacesContext.getCurrentInstance().addMessage(arg0, arg1);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Credenciales incorrectas" ,null);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "";
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "";
	}
	
	public void cerrarSession()
	{
		try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.invalidateSession(); 		 			
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
			
		} catch (Exception e) {

		}
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

}
