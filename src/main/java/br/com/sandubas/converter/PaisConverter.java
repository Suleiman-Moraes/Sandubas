package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.dao.PaisDAO;
import br.com.sandubas.model.Pais;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "PaisConverter")
public class PaisConverter implements Converter {

	private PaisDAO paisDAO;

	public PaisConverter() {
		this.paisDAO = CDIServiceLocator.getBean(PaisDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pais pais = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				pais = this.paisDAO.findById(Pais.class, Long.valueOf(value));
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
		return (Pais) pais;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Pais) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
