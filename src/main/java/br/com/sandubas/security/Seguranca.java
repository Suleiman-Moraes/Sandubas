package br.com.sandubas.security;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.util.jsf.FacesUtil;

@Named
@RequestScoped
public class Seguranca {

	public Usuario getUsuario() {
		Usuario usuario = null;
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		if (usuarioLogado != null) {
			usuario = usuarioLogado.getUsuario();
			usuario.setPerfis(usuarioLogado.getPerfis());
			usuario.setPerfilAdm(usuarioLogado.getPerfilAdm());
			usuario.setSAVE_DIR_SMB(FacesUtil.propertiesLoader().getProperty("diretorioAnexoSmb"));
		}
		return usuario;
	}

	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		return usuario;
	}

	public boolean temPerfil(String nomePerfil) {
		boolean retorno = false;
		for (Perfil perfil : getUsuarioLogado().getPerfis()) {
			if (perfil.getNome().equals(nomePerfil)) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	public boolean temFuncionalidade(String funcionalidade) {
		Collection<? extends GrantedAuthority> authorities = getUsuarioLogado().getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(funcionalidade));
	}

}
