package com.sv.audiomed.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteJasperUtilDAO {
	
	Connection cn;
	
	public ReporteJasperUtilDAO()
	{
		cn = Conexion.conectar();
	}
	
	public byte[] generarReporteFacturaAudiomed(int idFactura)
	{
		byte[] pdfBytes;
		
		
		try {
			
			
			//Prefiero usar el .jrxml a la aplicacion que el .jasper por que es mas facil de versionar
			//String sourceFileName = rutaFisica + "MiReporte.jrxml";
			
			//String sourceFileName ="/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			String sourceFileName ="C:/EclipseNeon/workspace/AudioPrinter/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			
			File theFile = new File(sourceFileName);
			
			
			System.out.println("ARCHIVO: "+theFile);
			
			JasperDesign jasperDesign = JRXmlLoader.load(theFile);//Se carga el archivo
			System.out.println("ARCHIVO 2: "+theFile);	
			//Si el reporte va a tener un query fijo, puedes omitir este paso
			/*JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("SELECT * FROM miTabla WHERE X = Y");
			jasperDesign.setQuery(newQuery);*/

			Map parameters = new HashMap();//Parametros que usa el jasperreports
			//Este parametro sirve para meter una funcion que el reporte va a ejecutar para encontrar la ruta fisica de sus imagenes
			/*parameters.put("REPORT_FILE_RESOLVER", new FileResolver() {
			                public File resolveFile(String fileName) {
			                    return new File(getServletContext().getRealPath("") + "\\mis_imagenes\\"+fileName); 
			                }
			            });*/
			parameters.put("id_factura", idFactura);
			
			//Se compila el archivo a .jasper
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			//Aqui se llena el reporte (se ejecuta la consulta)
			JasperPrint print = new JasperPrint();
			print = JasperFillManager.fillReport(jasperReport, parameters, cn);
			 pdfBytes = JasperExportManager.exportReportToPdf(print);
			

			return pdfBytes;
			/*response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo + ".pdf");
			response.getOutputStream().write(pdfBytes);
			response.flushBuffer();*/
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			
		}
	
		return null;
		
		
	 
	}
	
	
	public void imprimirReporteFacturaAudiomed(int idFactura)
	{
		byte[] pdfBytes;
		
		
		try {
			
			
			//Prefiero usar el .jrxml a la aplicacion que el .jasper por que es mas facil de versionar
			//String sourceFileName = rutaFisica + "MiReporte.jrxml";
			
			//String sourceFileName ="/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			//String sourceFileName ="C:/EclipseNeon/workspace/AudioPrinter/AudioPrinter/WebContent/reportes/FacturaAudiomedFormat.jrxml";
			String sourceFileName ="C:\\EclipseNeon\\workspace\\AudioPrinter\\AudioPrinter\\WebContent\\reportes\\FacturaAudiomedFormat.jasper";
			//String sourceFileName ="C:\\Users\\Hp\\JaspersoftWorkspace\\FacturaAudiomed\\FacturaAudiomedFormat.jrxml";
			//C:\Users\Hp\JaspersoftWorkspace\FacturaAudiomed\FacturaAudiomedFormat.jrxml
			//File theFile = new File(sourceFileName);
			
			
			System.out.println("ARCHIVO: "+sourceFileName);
			
			JasperDesign jasperDesign = JRXmlLoader.load(sourceFileName);//Se carga el archivo
			//System.out.println("ARCHIVO 2: "+theFile);	
			//Si el reporte va a tener un query fijo, puedes omitir este paso
			/*JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("SELECT * FROM miTabla WHERE X = Y");
			jasperDesign.setQuery(newQuery);*/

			Map parameters = new HashMap();//Parametros que usa el jasperreports
			//Este parametro sirve para meter una funcion que el reporte va a ejecutar para encontrar la ruta fisica de sus imagenes
			/*parameters.put("REPORT_FILE_RESOLVER", new FileResolver() {
			                public File resolveFile(String fileName) {
			                    return new File(getServletContext().getRealPath("") + "\\mis_imagenes\\"+fileName); 
			                }
			            });*/
			parameters.put("id_factura", idFactura);
			
			//Se compila el archivo a .jasper
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			//Aqui se llena el reporte (se ejecuta la consulta)
			JasperPrint print = new JasperPrint();
			print = JasperFillManager.fillReport(jasperReport, parameters, cn);
			 //pdfBytes = JasperExportManager.exportReportToPdf(print);
			
			JasperViewer jasperViewer = new JasperViewer(print);
			jasperViewer.setVisible(true);

			
			/*response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=" + "PruebaFactura01" + ".pdf");
			response.getOutputStream().write(pdfBytes);
			response.flushBuffer();*/
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			
		}
	
	
	}
}
