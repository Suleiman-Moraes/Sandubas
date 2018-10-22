package br.com.sandubas.util.jsf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.spacer.Spacer;

import br.com.sandubas.exception.PersonException;
import br.com.sandubas.message.MessagesLoader;
import br.com.sandubas.util.Assert;
import br.com.sandubas.util.StringUtil;

public class FacesUtil {

	private static final Logger log = Logger.getLogger(FacesUtil.class.getName());

	public static void addSuccessMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	public static void addWarningMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}

	public static void addDynamicMessage(String message, boolean type) {
		if (type) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}
	}

	public static void addListDynamicMessage(String message, boolean typeException, List<PersonException> exceptions) {
		if (message != null) {
			FacesUtil.addDynamicMessage(message, typeException);
		} else {
			for (PersonException exeption : exceptions) {
				if (exeption.isTypeException()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							exeption.getMsgException(), exeption.getMsgException()));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							exeption.getMsgException(), exeption.getMsgException()));
				}
			}
		}
	}

	public static String getURLBase() {
		return getServletRequest().getRequestURL().toString().split("sandubas")[0];
	}

	public static void removeSessionMapValue(String key) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
	}

	public static Object getRequestMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
	}

	public static void setRequestMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, value);
	}

	public static Object getSessionMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
	}

	public static void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);

	}

	public static HttpServletRequest getServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public static Properties propertiesLoader() {
		Properties prop = new Properties();
		if (FacesContext.getCurrentInstance().getViewRoot() != null) {
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			String atualDir = MessagesLoader.class.getResource("").getPath();
			try {
				File file = new File(atualDir + "sistema_" + locale.getLanguage() + ".properties");
				if (!file.exists()) {
					file = new File(atualDir + "sistema_pt.properties");
				}
				FileInputStream fileInputStream = new FileInputStream(file);
				prop.load(fileInputStream);
				fileInputStream.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return prop;
	}

	public static Properties propertiesLoaderURL() {
		Properties prop = new Properties();
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		String atualDir = MessagesLoader.class.getResource("").getPath();
		try {
			File file = new File(atualDir + "url_" + locale.getLanguage() + ".properties");
			if (!file.exists()) {
				file = new File(atualDir + "url_pt.properties");
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			prop.load(fileInputStream);
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return prop;
	}

	public static void checkURL(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(2000);
		log.info(String.format("Fetching %s ...", url));
		try {
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				log.info(String.format("Site is up, content length = %s", conn.getHeaderField("content-length")));
			} else {
				log.info(String.format("Site is up, but returns non-ok status = %d", responseCode));
			}
		} catch (java.net.UnknownHostException e) {
			log.warning("Site is down");
		}
	}

	public static String getApplicationUri() {
		try {
			FacesContext ctxt = FacesContext.getCurrentInstance();
			ExternalContext ext = ctxt.getExternalContext();
			URI uri = new URI(ext.getRequestScheme(), null, ext.getRequestServerName(), ext.getRequestServerPort(),
					ext.getRequestContextPath(), null, null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	public static String getUrlAplicacao(int parametro) {
		switch (verificaAmbienteAplicacao()) {
		case 1:
			return getUrlAplicacaoLocalHost(parametro);
		case 2:
			return getUrlAplicacaoDominioTeste(parametro);
		case 3:
			return getUrlAplicacaoDominioHomologacao(parametro);
		case 4:
			return getUrlAplicacaoIPTeste(parametro);
		case 5:
			return getUrlAplicacaoIPHomologacao(parametro);
		case 6:
			return getUrlAplicacaoIPTesteRemoto(parametro);
		case 7:
			return getUrlAplicacaoProducao(parametro);
		}
		return null;
	}

	public static int verificaAmbienteAplicacao() {
		if (getApplicationUri().contains("http://localhost:8080/CadastroUnico")) {
			return 1;
		} else if (getApplicationUri().contains("http://teste.agr.go:8080/CadastroUnico")) {
			return 2;
		} else if (getApplicationUri().contains("http://homologacao.agr.go:8080/CadastroUnico")) {
			return 3;
		} else if (getApplicationUri().contains("http://10.243.1.25:8080/CadastroUnico")) {
			return 4;
		} else if (getApplicationUri().contains("http://10.243.1.27:8080/CadastroUnico")) {
			return 5;
		} else if (getApplicationUri().contains("http://www.teste.agr.go.gov.br:8080/CadastroUnico")) {
			return 6;
		} else {
			return 7;
		}
	}

	public static String getUrlAplicacaoProducao(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoProducao");
		}
		return null;
	}

	public static String getUrlAplicacaoDominioTeste(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoDominioTeste");
		}
		return null;
	}

	public static String getUrlAplicacaoLocalHost(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificado");
		}
		return null;
	}

	public static String getUrlAplicacaoDominioHomologacao(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoDominioHomologacao");
		}
		return null;
	}

	public static String getUrlAplicacaoIPTeste(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoIPTeste");
		}
		return null;
	}

	public static String getUrlAplicacaoIPTesteRemoto(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoIPTesteRemoto");
		}
		return null;
	}

	public static String getUrlAplicacaoIPHomologacao(int parametro) {
		switch (parametro) {
		case 1:
			return FacesUtil.propertiesLoaderURL().getProperty("transporteConsultarCertificadoIPHomologacao");
		}
		return null;
	}

	public static void successMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	public static void errorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	public static void warningMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}

	public static void message(String summary, String detail, Severity severity) {
		summary = summary == null ? "" : summary;
		detail = detail == null ? "" : detail;
		if (severity != null) {
			FacesMessage message = new FacesMessage(severity, summary, detail);
			facesContext().addMessage(null, message);
		}
	}

	public static void message(String text, Severity severity) {
		message(text, text, severity);
	}

	public static void info(String message) {
		message(message, FacesMessage.SEVERITY_INFO);
	}

	public static void warning(String message) {
		message(message, FacesMessage.SEVERITY_WARN);
	}

	public static void error(String message) {
		message(message, FacesMessage.SEVERITY_ERROR);
	}

	public static void fatal(String message) {
		message(message, FacesMessage.SEVERITY_FATAL);
	}

	/**
	 * Classe interna responsável por agrupar os métodos relacionados a
	 * apresentação de mensagens.
	 * 
	 * @author thiago-amm
	 */
	public static final class message {

		public static void info(String message) {
			message(message, FacesMessage.SEVERITY_INFO);
		}

		public static void warning(String message) {
			message(message, FacesMessage.SEVERITY_WARN);
		}

		public static void error(String message) {
			message(message, FacesMessage.SEVERITY_ERROR);
		}

		public static void fatal(String message) {
			message(message, FacesMessage.SEVERITY_FATAL);
		}

	}

	public static PrimeFaces getPrimeFaces() {
		return PrimeFaces.current();
	}

	public static PrimeFaces primeFaces() {
		return getPrimeFaces();
	}

	public static void execute(String script) {
		primeFaces().executeScript(script);
	}

	public static void update(String name) {
		primeFaces().ajax().update(name);
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static FacesContext facesContext() {
		return getFacesContext();
	}

	public static ExternalContext getExternalContext() {
		return facesContext().getExternalContext();
	}

	public static ExternalContext externalContext() {
		return getExternalContext();
	}

	public static Properties getProperties() {
		return propertiesLoader();
	}

	public static Properties properties() {
		return getProperties();
	}

	public static String getProperty(String key) {
		return properties().getProperty(key);
	}

	public static String property(String key) {
		return getProperty(key);
	}

	public static void setProperty(String key, String value) {
		getProperties().setProperty(key, value);
	}

	public static void property(String key, String value) {
		setProperty(key, value);
	}

	public static Properties routes() {
		return propertiesLoaderURL();
	}

	public static String url(String key) {
		return routes().getProperty(key);
	}

	public static void url(String key, String value) {
		routes().setProperty(key, value);
	}

	public static final class routes {

		public static String route(String key) {
			return url(key);
		}

		public static void route(String key, String value) {
			url(key, value);
		}

	}

	public static void redirect(String url) {
		try {
			externalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void forward(String url) {
		try {
			externalContext().dispatch(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void invalidateSession() {
		externalContext().invalidateSession();
	}

	public static HttpSession getSession(boolean create) {
		HttpSession session = (HttpSession) externalContext().getSession(create);
		return session;
	}

	public static HttpSession getSession() {
		return getSession(false);
	}

	public static HttpSession session(boolean create) {
		return getSession(create);
	}

	public static HttpSession session() {
		return session(false);
	}

	public static Map<String, String> params() {
		return externalContext().getRequestParameterMap();
	}

	public static ServletContext getServletContext() {
		return session().getServletContext();
	}

	public static ServletContext getContext(String name) {
		return getServletContext().getContext(name);
	}

	public static ServletContext context(String name) {
		return getContext(name);
	}

	public static ServletContext servletContext() {
		return getServletContext();
	}

	public static String getContextName() {
		return externalContext().getContextName();
	}

	public static String contextName() {
		return externalContext().getContextName();
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) externalContext().getRequest();
	}

	public static HttpServletRequest request() {
		return getRequest();
	}

	public static void openDialog(String url, Map<String, Object> options, Map<String, List<String>> params) {
		url = url == null ? "" : url;
		if (!url.isEmpty()) {
			if (options == null) {
				options = new HashMap<>();
				options.put("modal", true);
				options.put("resizable", false);
				options.put("closable", true);
				options.put("draggable", false);
				options.put("contentWidth", 945);
				options.put("contentHeight", 580);
			}
			primeFaces().dialog().openDynamic(url, options, params);
		}
	}

	public static void openDialog(String url, Map<String, Object> options) {
		openDialog(url, null, null);
	}

	public static void openDialog(String url) {
		openDialog(url, null);
	}

	public static void dialog(String url) {
		openDialog(url);
	}

	public static void dialog(String url, Map<String, Object> options) {
		openDialog(url, options);
	}

	public static void dialog(String url, Map<String, Object> options, Map<String, List<String>> params) {
		openDialog(url, options, params);
	}

	public static void dialog(String url, boolean modal, boolean resizable, boolean closable, boolean draggable,
			int width, int height) {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", modal);
		options.put("resizable", resizable);
		options.put("closable", closable);
		options.put("draggable", draggable);
		options.put("contentWidth", width);
		options.put("contentHeight", height);
		dialog(url, options);
	}

	public static void closeDialog(Object data) {
		primeFaces().dialog().closeDynamic(data);
	}

	public static void closeDialog() {
		closeDialog(null);
	}

	/**
	 * Classe interna responsável por agrupar métodos associados a exibição de
	 * janelas.
	 * 
	 * @author thiago-amm
	 */
	public static final class dialog {

		public static void close(Object data) {
			closeDialog(data);
		}

	}

	public static void modal(String url, boolean resizable, boolean closable, boolean draggable, int width,
			int height) {
		dialog(url, true, resizable, closable, draggable, width, height);
	}

	public static void modal(String url, int width, int height) {
		modal(url, false, false, false, width, height);
	}

	public static void modal(String url, int width, int height, boolean closable) {
		modal(url, false, closable, false, width, height);
	}

	public static void modal(String url, boolean closable) {
		modal(url, false, closable, false, 700, 500);
	}

	public static void modal(String url) {
		modal(url, false, false, false, 700, 500);
	}

	public static String getRemoteUser() {
		return externalContext().getRemoteUser();
	}

	public static String remoteUser() {
		return getRemoteUser();
	}

	public static String getContextURL() {
		try {
			String scheme = externalContext().getRequestScheme();
			String host = externalContext().getRequestServerName();
			int port = externalContext().getRequestServerPort();
			String path = externalContext().getRequestContextPath();
			URI uri = new URI(scheme, null, host, port, path, null, null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	/**
	 * @param expr
	 * @return
	 * @author thiago
	 */
	public static UIComponent findComponent(String expr) {
		UIComponent component = null;
		if (StringUtil.isNotNullOrEmpty(expr)) {
			component = FacesContext.getCurrentInstance().getViewRoot().findComponent(expr);
		}
		return component;
	}

	public static UIComponent component(String expr) {
		return findComponent(expr);
	}

	public static <T> T component(String id, Class<T> clazz) {
		return clazz.cast(component(id));
	}

	public static Panel panel(String id) {
		return (Panel) component(id);
	}

	public static InputMask getInputMask(String id) {
		return component(id, InputMask.class);
	}

	public static InputMask inputMask(String id) {
		return getInputMask(id);
	}

	public static InputText getInputText(String id) {
		return component(id, InputText.class);
	}

	public static InputText inputText(String id) {
		return getInputText(id);
	}
	
	public static HtmlInputText getHtmlInputText(String id) {
		return component(id, HtmlInputText.class);
	}
	
	public static HtmlInputText htmlInputText(String id) {
		return getHtmlInputText(id);
	}

	public static HtmlSelectOneMenu getHtmlSelectOneMenu(String id) {
		return component(id, HtmlSelectOneMenu.class);
	}

	public static HtmlSelectOneMenu htmlSelectOneMenu(String id) {
		return getHtmlSelectOneMenu(id);
	}

	public static SelectOneMenu getSelectOneMenu(String id) {
		return component(id, SelectOneMenu.class);
	}

	public static SelectOneMenu selectOneMenu(String id) {
		return getSelectOneMenu(id);
	}

	public static Calendar getCalendar(String id) {
		return component(id, Calendar.class);
	}

	public static Calendar calendar(String id) {
		return getCalendar(id);
	}

	public static Spacer getSpacer(String id) {
		return component(id, Spacer.class);
	}

	public static Spacer spacer(String id) {
		return getSpacer(id);
	}

	public static CommandButton commandButton(String id) {
		return component(id, CommandButton.class);
	}

	public static SelectOneMenu selectOneMenuPrimefaces(String id) {
		return component(id, SelectOneMenu.class);
	}

	public static <T> SelectOneMenu selectOneMenu(List<T> list) {
		SelectOneMenu selectOneMenu = null;
		if (Assert.isNotNull(list)) {
			selectOneMenu = new SelectOneMenu();
			UISelectItems uiSelectItems = new UISelectItems();
			List<SelectItem> selectItems = new ArrayList<>();
			for (T i : list) {
				SelectItem selectItem = new SelectItem();
				selectItem.setLabel(i.toString());
				selectItem.setValue(i);
				selectItems.add(selectItem);
			}
			uiSelectItems.setValue(selectItems);
			selectOneMenu.getChildren().add(uiSelectItems);
		}
		return selectOneMenu;
	}

	/**
	 * @param list
	 * @param labelMethod
	 * @param valueMethod
	 * @return
	 * @author thiago
	 */
	public static <T> SelectOneMenu selectOneMenu(List<T> list, String labelMethod, String valueMethod) {
		SelectOneMenu selectOneMenu = null;
		if (Assert.noneIsNull(list, labelMethod, valueMethod)) {
			selectOneMenu = new SelectOneMenu();
			UISelectItems uiSelectItems = new UISelectItems();
			List<SelectItem> selectItems = new ArrayList<>();
			try {
				for (T i : list) {
					SelectItem selectItem = new SelectItem();
					Method label = i.getClass().getMethod(labelMethod, (Class<?>[]) null);
					Method value = i.getClass().getMethod(valueMethod, (Class<?>[]) null);
					selectItem.setLabel(label.invoke(i, (Object[]) null).toString());
					selectItem.setValue(value.invoke(i, (Object[]) null).toString());
					selectItems.add(selectItem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			uiSelectItems.setValue(selectItems);
			selectOneMenu.getChildren().add(uiSelectItems);
		}
		return selectOneMenu;
	}

	public static <T> SelectOneMenu selectOneMenu(Object[] array, String labelMethod, String valueMethod) {
		if (array != null) {
			return selectOneMenu(Arrays.asList(array), labelMethod, valueMethod);
		}
		return null;
	}

	public static <T> HtmlSelectOneMenu htmlSelectOneMenu(List<T> list) {
		HtmlSelectOneMenu htmlSelectOneMenu = null;
		if (Assert.isNotNull(list)) {
			htmlSelectOneMenu = new SelectOneMenu();
			UISelectItems uiSelectItems = new UISelectItems();
			List<SelectItem> selectItems = new ArrayList<>();
			for (T i : list) {
				SelectItem selectItem = new SelectItem();
				selectItem.setLabel(i.toString());
				selectItem.setValue(i);
				selectItems.add(selectItem);
			}
			uiSelectItems.setValue(selectItems);
			htmlSelectOneMenu.getChildren().add(uiSelectItems);
		}
		return htmlSelectOneMenu;
	}

	public static <T> HtmlSelectOneMenu htmlSelectOneMenu(List<T> list, String labelMethod, String valueMethod) {
		HtmlSelectOneMenu htmlSelectOneMenu = null;
		if (Assert.noneIsNull(list, labelMethod, valueMethod)) {
			htmlSelectOneMenu = new SelectOneMenu();
			UISelectItems uiSelectItems = new UISelectItems();
			List<SelectItem> selectItems = new ArrayList<>();
			try {
				for (T i : list) {
					SelectItem selectItem = new SelectItem();
					Method label = i.getClass().getMethod(labelMethod, (Class<?>[]) null);
					Method value = i.getClass().getMethod(valueMethod, (Class<?>[]) null);
					selectItem.setLabel(label.invoke(i, (Object[]) null).toString());
					selectItem.setValue(value.invoke(i, (Object[]) null).toString());
					selectItems.add(selectItem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			uiSelectItems.setValue(selectItems);
			htmlSelectOneMenu.getChildren().add(uiSelectItems);
		}
		return htmlSelectOneMenu;
	}

	public static void download(String destFileName, String nomeArquivoDownload) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivoDownload + "\"");

		// Abre o arquivo gerado
		File relatorio = new File(destFileName);
		response.getOutputStream().write(getFileBytes(relatorio));
		facesContext.responseComplete();
	}

	public static void download(ByteArrayOutputStream baos, String nomeArquivoDownload) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivoDownload + "\"");

		// Abre o arquivo gerado
		response.getOutputStream().write(baos.toByteArray());
		facesContext.responseComplete();
	}

	public static void download(File file, String nomeArquivoDownload) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivoDownload + "\"");

		// Abre o arquivo gerado
		response.getOutputStream().write(getFileBytes(file));
		facesContext.responseComplete();
	}

	private static byte[] getFileBytes(File file) {
		byte[] bytes = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
		} catch (IOException ex) {
			System.out.println("Não foi possível encontrar o arquivo: " + file.getAbsolutePath());
			ex.printStackTrace();
		}
		return bytes;
	}

	public static <T> HtmlSelectOneMenu htmlSelectOneMenu(Object[] array, String labelMethod, String valueMethod) {
		if (array != null) {
			return htmlSelectOneMenu(Arrays.asList(array), labelMethod, valueMethod);
		}
		return null;
	}

	public static DataTable dataTable(String id) {
		return (DataTable) component(id);
	}

	public static void focus(String id) {
		if (Assert.isNotEmpty(id)) {

		}
	}

	public static void renderResponse() {
		facesContext().renderResponse();
	}

	public static void responseComplete() {
		facesContext().responseComplete();
	}

}