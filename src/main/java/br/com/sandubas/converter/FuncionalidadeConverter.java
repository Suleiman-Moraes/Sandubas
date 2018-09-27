package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.dao.FuncionalidadeDAO;
import br.com.sandubas.model.Funcionalidade;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "FuncionalidadeConverter")
public class FuncionalidadeConverter implements Converter {

	private FuncionalidadeDAO funcionalidadeDAO;

	public FuncionalidadeConverter() {
		this.funcionalidadeDAO = CDIServiceLocator.getBean(FuncionalidadeDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Funcionalidade funcionalidade = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				funcionalidade = this.funcionalidadeDAO.findById(Funcionalidade.class, Long.valueOf(value));
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
		return (Funcionalidade) funcionalidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Funcionalidade) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
