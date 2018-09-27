package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.dao.UFDAO;
import br.com.sandubas.model.UF;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "UFConverter")
public class UFConverter implements Converter {

	private UFDAO ufDAO;

	public UFConverter() {
		this.ufDAO = CDIServiceLocator.getBean(UFDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		UF uf = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				uf = this.ufDAO.findById(UF.class, Long.valueOf(value));
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
		return (UF) uf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((UF) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
