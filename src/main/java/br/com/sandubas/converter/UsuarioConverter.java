package br.com.sandubas.converter;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.StatusUsuarioEnum;
import br.com.sandubas.service.UsuarioService;
import br.com.sandubas.util.cdi.CDIServiceLocator;
import br.com.sandubas.util.jsf.FacesUtil;

@FacesConverter(value = "UsuarioConverter")
public class UsuarioConverter implements Converter {

	private UsuarioService usuarioService;

	public UsuarioConverter() {
		this.usuarioService = CDIServiceLocator.getBean(UsuarioService.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario usuario = null;
		try {
			if (StringUtils.isNumeric(value) && value != null) {
				usuario = this.usuarioService.getUsuarioDAO().buscarUsuarioPassandoId(Long.valueOf(value));
				usuario.setConfirmacaoSenha(usuario.getSenha());
				usuario.setRedefinirSenha(Boolean.TRUE);
				if (StatusUsuarioEnum.INATIVO.equals(usuario.getStatusUsuarioEnum())) {
					usuario.setTooltipStatus(FacesUtil.propertiesLoader().getProperty("usuarioInativar"));
				} else {
					usuario.setTooltipStatus(FacesUtil.propertiesLoader().getProperty("usuarioCadastrarSenha"));
				}
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
		return (Usuario) usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Usuario) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
