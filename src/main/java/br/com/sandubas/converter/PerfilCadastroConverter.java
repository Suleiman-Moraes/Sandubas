package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.dao.PerfilDAO;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "PerfilCadastroConverter")
public class PerfilCadastroConverter implements Converter {

	private PerfilDAO perfilDAO;

	public PerfilCadastroConverter() {
		this.perfilDAO = CDIServiceLocator.getBean(PerfilDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Perfil perfil = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				perfil = this.perfilDAO.findById(Perfil.class, Long.valueOf(value));
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
		return (Perfil) perfil;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Perfil) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
