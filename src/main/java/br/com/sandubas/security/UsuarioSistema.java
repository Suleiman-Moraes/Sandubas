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

	private Boolean perfilCliente;
	
	private Boolean perfilFuncionario;
	
	private Boolean perfilAdministrador;
	
	private Boolean perfilOperador;
	
	private Boolean perfilROOT;
	
	/**
	 * 
	 * @param usuario
	 * @param perfis
	 * @param authorities
	 * @param perfilCliente
	 * @param perfilFuncionario
	 * @param perfilAdministrador
	 * @param perfilOperador
	 * @param perfilROOT
	 */
	public UsuarioSistema(Usuario usuario, List<Perfil> perfis, Collection<? extends GrantedAuthority> authorities,
			Boolean perfilCliente, Boolean perfilFuncionario, Boolean perfilAdministrador, Boolean perfilOperador, Boolean perfilROOT) {
		super(usuario.getLogin(), usuario.getSenha(), authorities);
		this.usuario = usuario;
		this.perfis = perfis;
		this.perfilCliente = perfilCliente;
		this.perfilFuncionario = perfilFuncionario;
		this.perfilAdministrador = perfilAdministrador;
		this.perfilOperador = perfilOperador;
		this.perfilROOT = perfilROOT;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public List<Perfil> getPerfis() {
		return perfis;
	}
	public Boolean getPerfilOperador() {
		return perfilOperador;
	}
	public Boolean getPerfilCliente() {
		return perfilCliente;
	}
	public Boolean getPerfilFuncionario() {
		return perfilFuncionario;
	}
	public Boolean getPerfilAdministrador() {
		return perfilAdministrador;
	}
	public Boolean getPerfilROOT() {
		return perfilROOT;
	}
}
