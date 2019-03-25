package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.service.ClassificacaoMercadoriaService;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "ClassificacaoMercadoriaConverter")
public class ClassificacaoMercadoriaConverter implements Converter {

	private ClassificacaoMercadoriaService service;

	public ClassificacaoMercadoriaConverter() {
		this.service = CDIServiceLocator.getBean(ClassificacaoMercadoriaService.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ClassificacaoMercadoria objeto = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				objeto = this.service.findById(value);
			} else {
				throw new IOException();
			}
		} catch (IOException io) {
			try {
				context.getExternalContext().redirect("/sandubas/404.xhtml");
			} catch (IOException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			}
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		return (ClassificacaoMercadoria) objeto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((ClassificacaoMercadoria) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}
		return "";
	}
}
