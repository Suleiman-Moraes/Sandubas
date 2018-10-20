package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.model.Mercadoria;
import br.com.sandubas.service.MercadoriaService;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "MercadoriaConverter")
public class MercadoriaConverter implements Converter {

	private MercadoriaService service;

	public MercadoriaConverter() {
		this.service = CDIServiceLocator.getBean(MercadoriaService.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Mercadoria objeto = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				objeto = this.service.getPersistencia().findById(Mercadoria.class, Long.valueOf(value));
			} else {
				throw new IOException();
			}
		} catch (IOException io) {
			try {
				context.getExternalContext().redirect("/sandubas/404.xhtml");
			} catch (IOException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			}
		}
		return (Mercadoria) objeto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return null;
	}

}
