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
import com.sv.audiomed.dao.ReporteJasperUtilDAO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@WebServlet("/facturaAudiomedServlet")
public class FacturaAudiomedServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	ReporteJasperUtilDAO reporte;
	Connection cn;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		reporte = new ReporteJasperUtilDAO();
		cn = Conexion.conectar();
		
		int idFactura=Integer.parseInt(req.getParameter("idFactura").toString());
		byte[] pdfBytes;	
		
		
		try {
			
			
			//Prefiero usar el .jrxml a la aplicacion que el .jasper por que es mas facil de versionar
			//String sourceFileName = rutaFisica + "MiReporte.jrxml";
			//String sourceFileName ="/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			String sourceFileName ="C:/EclipseNeon/workspace/AudioPrinter/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			
			File theFile = new File(sourceFileName);

			System.out.println("ARCHIVO: "+theFile);
			
			//JasperDesign jasperDesign = JRXmlLoader.load(theFile);//Se carga el archivo
			System.out.println("ARCHIVO 2: "+theFile);	


			Map parameters = new HashMap();//Parametros que usa el jasperreports
			parameters.put("id_factura", idFactura);
			
			//Se compila el archivo a .jasper
			//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);//sourceFileName
			JasperReport jasperReport = JasperCompileManager.compileReport("reportes/FacturaAudiomedFormat.jrxml");

			//Aqui se llena el reporte (se ejecuta la consulta)
			JasperPrint print = new JasperPrint();
			print = JasperFillManager.fillReport(jasperReport, parameters, cn);
			 pdfBytes = JasperExportManager.exportReportToPdf(print);
		
			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition", "inline;filename=" + "FacturaAudiomed" + ".pdf");
			resp.getOutputStream().write(pdfBytes);
			resp.flushBuffer();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	

}
