package br.com.sandubas.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.service.UsuarioService;
import br.com.sandubas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class MinhasInformacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario;

	public void atualizarDadosUsuario() {
		try {
			this.usuarioService.atualizarDadosUsuarioMinhasInformacoes(usuario);
			PrimeFaces.current().ajax().addCallbackParam("autenticar", Boolean.TRUE);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	public void carregarUsuario(String login) {
		setUsuario(this.usuarioService.getUsuarioDAO().buscarUsuarioPorLogin(login));
//		if (usuario.getLogin().equals("root") || usuario.getTelefone() != null
//				&& usuario.getTelefone().getTipoTelefoneEnum().equals(TipoTelefoneEnum.FIXO)) {
//			usuario.setTipoTelefone(Boolean.TRUE);
//		} else {
//			usuario.setTipoTelefone(Boolean.FALSE);
//		}
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
