package br.com.sandubas.util.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.report.ReportLoader;
import br.com.sandubas.util.jsf.FacesUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings("deprecation")
public class GeradorRelatorio {

	private Map<String, Object> parametros;
	private String nomeInicialRelatorio;
	private String nomeFinalRelatorio;
	private StreamedContent file = null;
	private File tempFile = null;

	public void baixarPDF(String nomeArquivoJasper, List<?> objetos, String nomeRelatorio) throws NegocioException {
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(objetos);
		File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
		JasperReport jasperReport = null;
		JasperPrint printer = null;
		File pdfFile = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			printer = JasperFillManager.fillReport(jasperReport, getParametros(),
					(objetos == null ? new JREmptyDataSource() : jrds));
			setNomeInicialRelatorio(Long.toString(new Date().getTime())
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
			pdfFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
			setNomeFinalRelatorio(nomeRelatorio);
			if (pdfFile.exists()) {
				try {
					pdfFile.delete();
				} catch (Exception e) {
					throw new NegocioException(
							FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroExportacaoPDF"), false);
				}
			}
			JRPdfExporter jrpdfexporter = new JRPdfExporter();
			jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
			jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
			jrpdfexporter.exportReport();
		} catch (JRException e) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "\n" + e.getMessage(), false);
		} catch (Exception erro) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "Erro" + erro.getMessage(), false);
		} finally {
			getParametros().clear();
		}
	}

	public byte[] getContentBytesPDF(String nomeArquivoJasper, List<?> objetos, String nomeRelatorio)
			throws NegocioException {
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(objetos);
		File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
		JasperReport jasperReport = null;
		JasperPrint printer = null;
		File pdfFile = null;
		byte[] b = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			printer = JasperFillManager.fillReport(jasperReport, getParametros(),
					(objetos == null ? new JREmptyDataSource() : jrds));
			setNomeInicialRelatorio(Long.toString(new Date().getTime())
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
			pdfFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
			setNomeFinalRelatorio(nomeRelatorio);
			JRPdfExporter jrpdfexporter = new JRPdfExporter();
			jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
			jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
			jrpdfexporter.exportReport();

			FileInputStream stream = new FileInputStream(pdfFile);

			if (pdfFile.exists())
				pdfFile.delete();

			b = IOUtils.toByteArray(stream);
		} catch (JRException e) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "\n" + e.getMessage(), false);
		} catch (Exception erro) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "\n" + erro.getMessage() + "Erro" + erro.getMessage(), false);
		} finally {
			getParametros().clear();
		}
		return b;
	}

	public void imprimirPDF(String nomeArquivoJasper, List<?> objetos, String nomeRelatorio) throws NegocioException {
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(objetos);
		File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
		JasperReport jasperReport = null;
		JasperPrint printer = null;
		File pdfFile = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			printer = JasperFillManager.fillReport(jasperReport, getParametros(),
					(objetos == null ? new JREmptyDataSource() : jrds));
			setNomeInicialRelatorio(Long.toString(new Date().getTime())
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
			pdfFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
			setNomeFinalRelatorio(nomeRelatorio);
			JRPdfExporter jrpdfexporter = new JRPdfExporter();
			jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
			jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
			jrpdfexporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
			jrpdfexporter.exportReport();

			FileInputStream stream = new FileInputStream(pdfFile);
			byte[] b = IOUtils.toByteArray(stream);

			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			res.setContentType("application/pdf");
			res.setHeader("Content-disposition", "inline;filename=crc.pdf");
			res.getOutputStream().write(b);
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			stream.close();
			if (pdfFile.exists())
				pdfFile.delete();
		} catch (JRException e) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "\n" + e.getMessage(), false);
		} catch (Exception erro) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "\n" + erro.getMessage() + "Erro" + erro.getMessage(), false);
		} finally {
			getParametros().clear();
		}
	}

	public void imprimirDOC(String nomeArquivoJasper, List<?> objetos, String nomeRelatorio) throws NegocioException {
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(objetos);
		File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
		JasperReport jasperReport = null;
		JasperPrint printer = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			printer = JasperFillManager.fillReport(jasperReport, getParametros(),
					(objetos == null ? new JREmptyDataSource() : jrds));
			setNomeInicialRelatorio(Long.toString(new Date().getTime())
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioRTF"));
			File docFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
			setNomeFinalRelatorio(nomeRelatorio);
			if (docFile.exists()) {
				try {
					docFile.delete();
				} catch (Exception e) {
					throw new NegocioException(
							FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroExportacaoDOC"), false);
				}
			}
			JRRtfExporter jrRtfExporter = new JRRtfExporter();
			jrRtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
			jrRtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE, docFile);
			jrRtfExporter.exportReport();
		} catch (JRException e) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio"),
					false);
		} catch (Exception erro) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "Erro" + erro.getMessage(), false);
		} finally {
			getParametros().clear();
		}

	}

	@SuppressWarnings("unused")
	public void imprimirXLS(String nomeArquivoJasper, List<?> objetos, String nomeRelatorio) throws NegocioException {
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(objetos);
		File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
		JasperReport jasperReport = null;
		JasperPrint printer = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			printer = JasperFillManager.fillReport(jasperReport, getParametros(),
					(objetos == null ? new JREmptyDataSource() : jrds));
			setNomeInicialRelatorio(Long.toString(new Date().getTime())
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioXLS"));
			File excelFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
			setNomeFinalRelatorio(nomeRelatorio);
			if (excelFile.exists()) {
				try {
					excelFile.delete();
				} catch (Exception e) {
					throw new NegocioException(
							FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroExportacaoXLS"), false);
				}
			}
			JRXlsExporter jrxlsExporter = new JRXlsExporter();
			jrxlsExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					this.gerarRelatorioJasperPrintObjeto(getParametros(), objetos, nomeArquivoJasper)); 
			jrxlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE, excelFile);
			jrxlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrxlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			jrxlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			jrxlsExporter.exportReport();
		} catch (JRException e) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio"),
					false);
		} catch (Exception erro) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("geradorRelarorioErroGeraRelatorio")
					+ "Erro" + erro.getMessage(), false);
		} finally {
			getParametros().clear();
		}
	}

	public void adicionaParametroRelatorio(String chave, Object valor) {
		getParametros().put(chave, valor);
	}

	public StreamedContent getFilePDF() {
		tempFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
		try {
			file = new DefaultStreamedContent(new FileInputStream(tempFile), "application/pdf",
					getNomeFinalRelatorio() + FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
		} catch (FileNotFoundException e) {
			FacesUtil.addDynamicMessage(MessageFormat.format(
					FacesUtil.propertiesLoader().getProperty("geradorRelatorioArquivoNaoEncontrado"),
					getNomeFinalRelatorio()), false);
		}
		return file;
	}

	public StreamedContent getFileRTF() {
		tempFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
		try {
			file = new DefaultStreamedContent(new FileInputStream(tempFile), "application/octet-stream",
					getNomeFinalRelatorio() + FacesUtil.propertiesLoader().getProperty("geradorRelatorioRTF"));
		} catch (FileNotFoundException e) {
			FacesUtil.addDynamicMessage(MessageFormat.format(
					FacesUtil.propertiesLoader().getProperty("geradorRelatorioArquivoNaoEncontrado"),
					getNomeFinalRelatorio()), false);
		}
		return file;
	}

	public StreamedContent getFileXLS() {
		tempFile = new File(ReportLoader.class.getResource("").getPath() + getNomeInicialRelatorio());
		try {
			file = new DefaultStreamedContent(new FileInputStream(tempFile), "application/vnd.ms-excel",
					getNomeFinalRelatorio() + FacesUtil.propertiesLoader().getProperty("geradorRelatorioXLS"));
		} catch (FileNotFoundException e) {
			FacesUtil.addDynamicMessage(MessageFormat.format(
					FacesUtil.propertiesLoader().getProperty("geradorRelatorioArquivoNaoEncontrado"),
					getNomeFinalRelatorio()), false);
		}
		return file;
	}

	public StreamedContent getFilePDFContrato() {
		tempFile = new File(ReportLoader.class.getResource("").getPath() + "Contrato"
				+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
		try {
			file = new DefaultStreamedContent(new FileInputStream(tempFile), "application/pdf",
					"Contrato" + FacesUtil.propertiesLoader().getProperty("geradorRelatorioPDF"));
		} catch (FileNotFoundException e) {
			FacesUtil.addDynamicMessage(MessageFormat.format(
					FacesUtil.propertiesLoader().getProperty("geradorRelatorioArquivoNaoEncontrado"),
					getNomeFinalRelatorio()), false);
		} finally {
			tempFile = null;
		}
		return file;
	}

	public void deletaArquivo() {
		if (tempFile != null) {
			try {
				file.getStream().close();
			} catch (Exception e) {
				FacesUtil.addDynamicMessage(
						FacesUtil.propertiesLoader().getProperty("geradorRelatorioArquivoNaoFechado"), false);
			} finally {
				tempFile.delete();
				tempFile = null;
			}
		}
	}

	public JasperPrint gerarRelatorioJasperPrintObjeto(Map<String, Object> parametros, List<?> listaObjetos,
			String nomeArquivoJasper) throws Exception {
		try {
			JRDataSource jr = new JRBeanArrayDataSource((listaObjetos != null ? listaObjetos.toArray() : null));
			File arquivoIReport = new File(ReportLoader.class.getResource("").getPath() + nomeArquivoJasper
					+ FacesUtil.propertiesLoader().getProperty("geradorRelatorioJASPER"));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros,
					(listaObjetos != null ? jr : new JREmptyDataSource()));
			jr = null;
			arquivoIReport = null;
			jasperReport = null;
			return print;
		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Object> getParametros() {
		if (parametros == null) {
			parametros = new HashMap<String, Object>();
		}
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public String getNomeInicialRelatorio() {
		return nomeInicialRelatorio;
	}

	public void setNomeInicialRelatorio(String nomeInicialRelatorio) {
		this.nomeInicialRelatorio = nomeInicialRelatorio;
	}

	public String getNomeFinalRelatorio() {
		return nomeFinalRelatorio;
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setNomeFinalRelatorio(String nomeFinalRelatorio) {
		this.nomeFinalRelatorio = nomeFinalRelatorio;
	}
}
