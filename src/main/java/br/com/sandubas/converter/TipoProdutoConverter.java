package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.model.TipoProduto;
import br.com.sandubas.service.TipoProdutoService;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "TipoProdutoConverter")
public class TipoProdutoConverter implements Converter {

	private TipoProdutoService service;

	public TipoProdutoConverter() {
		this.service = CDIServiceLocator.getBean(TipoProdutoService.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TipoProduto objeto = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				objeto = this.service.getPersistencia().findById(TipoProduto.class, Long.valueOf(value));
			} else {
				throw new IOException();
			}
		} catch (IOException io) {
			try {
				context.getExternalContext().redirect("/ouvidoria/404.xhtml");
			} catch (IOException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			}
		}
		return (TipoProduto) objeto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return null;
	}

}
