package com.sv.audiomed.dao;

import java.util.List;

import com.sv.audiomed.model.FacturaAudiomed;

public class Test {

	public static void main(String[] args) {
		FacturaAudiomedDAO facturaAudiomedDAO = new FacturaAudiomedDAO();
		FacturaAudiomed factura = new FacturaAudiomed();
		
		//per.setNombres("Mito");
		//per.setApellidos("Code");
		//dao.agregar(per);
		
		List<FacturaAudiomed> lista = facturaAudiomedDAO.buscarTodos();
		//lista.forEach(x -> System.out.println(x.getId() + "-" + x.getNombres() + "-" + x.getApellidos()));
		
		for(FacturaAudiomed x : lista){
			System.out.println(x.getIdFactura() + "-" + x.getCodigoFactura());
		}
	}
}
