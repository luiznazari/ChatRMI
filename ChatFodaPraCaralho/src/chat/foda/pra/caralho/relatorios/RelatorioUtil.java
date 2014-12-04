package chat.foda.pra.caralho.relatorios;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author Alessandro Beleboni Belini
 */
public class RelatorioUtil {
	
	/**
	 * Visualiza o relatório a partir de um arquivo .jasper, passando os parametros e a conexão.
	 * 
	 * @param jasper
	 * @param params
	 * @param conn
	 */
	public static void viewReport(String jasper, Map<String, Object> params, Connection conn) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, params, conn);
			JasperViewer.viewReport(jasperPrint, false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Compila e depois Visualiza o relatório a partir de um arquivo .jrxml, passando os parametros e a conexão.
	 * 
	 * @param jrxml
	 * @param params
	 * @param conn
	 */
	public static void compileViewReport(String jrxml, Map<String, Object> params, Connection conn) {
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(jrxml);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
			JasperViewer.viewReport(jasperPrint, false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Compila o arquivo .jrxml em um .jasper.
	 * 
	 * @param jrxmls
	 * @param destJasper
	 */
	public static void compileToFile(String jrxml, String destJasper) {
		try {
			JasperCompileManager.compileReportToFile(jrxml, destJasper);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gera um arquivo PDF.
	 * 
	 * @param jasper
	 * @param params
	 * @param conn
	 * @param name
	 */
	public static void gerarPDF(String jasper, Map<String, Object> params, Connection conn, String name) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, params, conn);
			JasperExportManager.exportReportToPdfFile(jasperPrint, name + ".pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Compila e visualiza o relatório a partir de uma lista do DAO, passando os parametros.
	 * 
	 * @param jrxml
	 * @param dados
	 * @param params
	 */
	public static void compileViewReport(String jrxml, List<?> dados, Map<String, Object> params) {
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(jrxml);
			JRDataSource dataSource = new JRBeanCollectionDataSource(dados);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			JasperViewer.viewReport(jasperPrint, false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}