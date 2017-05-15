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

@WebServlet("/creditoFiscalAudiomedServlet")
public class CreditoFiscalAudiomedServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	ReporteJasperUtilDAO reporte;
	ReporteFacturaAudiomedDAO reporteFacturaAudiomedDAO;
	Connection cn;
	
	//Nuevo 
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;
	private JasperViewer jasperViewer;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		reporte = new ReporteJasperUtilDAO();
		cn = Conexion.conectar();
		reporteFacturaAudiomedDAO = new ReporteFacturaAudiomedDAO();
		
		int idFactura=Integer.parseInt(req.getParameter("idFactura").toString());
		byte[] pdfBytes;	
		
		
		try {
			
			
			jasperReport = (JasperReport)JRLoader.loadObjectFromFile("C:\\Users\\Hp\\JaspersoftWorkspace\\CreditoFiscalAudiomedFormat\\CreditoFiscalAudiomedFormat.jasper");
			Map parameters = new HashMap();
			parameters.put("id_factura", idFactura);
			
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,cn);
			
			/*jasperViewer = new JasperViewer(jasperPrint);
			jasperViewer.setVisible(true);*/

			//Aqui se llena el reporte (se ejecuta la consulta)
			//JasperPrint print = new JasperPrint();
			//print = JasperFillManager.fillReport(jasperReport, parameters, cn);
			 pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		
			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition", "inline;filename=" + "CreditoFiscalAudiomed" + ".pdf");
			resp.getOutputStream().write(pdfBytes);
			resp.flushBuffer();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	

}
