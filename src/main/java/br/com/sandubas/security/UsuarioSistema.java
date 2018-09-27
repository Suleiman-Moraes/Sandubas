package br.com.sandubas.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private List<Perfil> perfis;

	private Boolean perfilAdm;
	
	private Boolean perfilOuvidor;
	
	private Boolean perfilInterlocutor;
	
	private Boolean perfilOperador;

	public UsuarioSistema(Usuario usuario, List<Perfil> perfis, Collection<? extends GrantedAuthority> authorities,
			Boolean perfilAdm, Boolean perfilOuvidor, Boolean perfilInterlocutor, Boolean perfilOperador) {
		super(usuario.getLogin(), usuario.getSenha(), authorities);
		this.usuario = usuario;
		this.perfis = perfis;
		this.perfilAdm = perfilAdm;
		this.perfilOuvidor = perfilOuvidor;
		this.perfilInterlocutor = perfilInterlocutor;
		this.perfilOperador = perfilOperador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public Boolean getPerfilAdm() {
		return perfilAdm;
	}

	public Boolean getPerfilOuvidor() {
		return perfilOuvidor;
	}

	public Boolean getPerfilInterlocutor() {
		return perfilInterlocutor;
	}

	public Boolean getPerfilOperador() {
		return perfilOperador;
	}

	
}
