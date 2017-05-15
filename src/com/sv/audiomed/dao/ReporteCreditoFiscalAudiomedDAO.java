package com.sv.audiomed.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteCreditoFiscalAudiomedDAO {
	
	Connection cn;
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;
	private JasperViewer jasperViewer;
	
	
	
	public ReporteCreditoFiscalAudiomedDAO()
	{
		cn = Conexion.conectar();
	}
	
	public void crearReporte(int idFactura)
	{
		
		try {
			
			jasperReport = (JasperReport)JRLoader.loadObjectFromFile("C:\\Users\\Hp\\JaspersoftWorkspace\\CreditoFiscalAudiomedFormat\\CreditoFiscalAudiomedFormat.jasper");     
			
			Map parameters = new HashMap();
			parameters.put("id_factura", idFactura);
			
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,cn);
			
			jasperViewer = new JasperViewer(jasperPrint);
			jasperViewer.setVisible(true);
			
		} catch (Exception e) {

		}
		
	}
	
	public void generarPDF()
	{
		
	}
	
	public void exportarPdf()
	{
		try
		{
			JasperExportManager.exportReportToPdfFile(jasperPrint,"");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
