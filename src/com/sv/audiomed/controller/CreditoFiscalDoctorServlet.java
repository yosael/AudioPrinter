package com.sv.audiomed.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sv.audiomed.dao.Conexion;
import com.sv.audiomed.dao.ReporteFacturaAudiomedDAO;
import com.sv.audiomed.dao.ReporteJasperUtilDAO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

@WebServlet("/creditoFiscalDoctorServlet")
public class CreditoFiscalDoctorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	Connection cn;
	
	//Nuevo 
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;
	private JasperViewer jasperViewer;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		cn = Conexion.conectar();
		
		int idFactura=Integer.parseInt(req.getParameter("idFactura").toString());
		byte[] pdfBytes;	
		
		
		try {
			
			
			jasperReport = (JasperReport)JRLoader.loadObjectFromFile("C:\\Users\\Hp\\JaspersoftWorkspace\\CreditoFiscalDoctorFormat\\CreditoFiscalDoctorFormat.jasper");
			Map parameters = new HashMap();
			parameters.put("id_factura", idFactura);
			
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,cn);
			
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		
			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition", "inline;filename=" + "CreditoFiscalDoctor" + ".pdf");
			resp.getOutputStream().write(pdfBytes);
			resp.flushBuffer();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	

}
