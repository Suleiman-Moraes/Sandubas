package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.service.PerfilService;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "PerfilConverter")
public class PerfilConverter implements Converter {

	private PerfilService perfilService;

	public PerfilConverter() {
		this.perfilService = CDIServiceLocator.getBean(PerfilService.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Perfil perfil = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				perfil = this.perfilService.getPerfilDAO()
						.buscarPerfilComFuncionalidadesPassandoId(Long.valueOf(value));
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
		return null;
	}

}
