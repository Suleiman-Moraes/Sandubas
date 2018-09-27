package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.dao.MunicipioDAO;
import br.com.sandubas.model.Municipio;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "MunicipioConverter")
public class MunicipioConverter implements Converter {

	private MunicipioDAO municipioDAO;

	public MunicipioConverter() {
		this.municipioDAO = CDIServiceLocator.getBean(MunicipioDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Municipio municipio = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				municipio = this.municipioDAO.findById(Municipio.class, Long.valueOf(value));
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
		return (Municipio) municipio;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Municipio) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
