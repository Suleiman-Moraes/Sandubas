package br.com.sandubas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;

@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_email")
	private Email email;

	@NotNull
	@NotBlank
	@Column(length = 100)
	private String login;

	@Size(min = 6, message = "A confirmação da senha deve ter pelo menos 6 caracteres")
	@Column(length = 32)
	private String senha;

	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	@Transient
	private String novaSenha;

	@Size(min = 6, message = "A confirmação da senha deve ter pelo menos 6 caracteres")
	@Transient
	private String confirmacaoSenha;

	@NotNull
	@NotBlank
	@Column(length = 180)
	private String nome;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusUsuarioEnum statusUsuarioEnum;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_funcao")
	private FuncaoUsuarioEnum funcaoUsuarioEnum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ativacao")
	private Date dataAtivacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_desativacao")
	private Date dataDesativacao;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_perfil", joinColumns = { @JoinColumn(name = "id_usuario") }, inverseJoinColumns = {
			@JoinColumn(name = "id_perfil") })
	private List<Perfil> perfis;

	@Transient
	private String tooltipStatus;
	
	@Transient
	private Boolean perfilCliente;
	
	@Transient
	private Boolean perfilFuncionario;
	
	@Transient
	private Boolean perfilAdministrador;
	
	@Transient
	private Boolean perfilOperador;
	
	@Transient
	private Boolean perfilROOT;

	public Usuario() {}
	/**
	 * 
	 * @param id
	 * @param email
	 * @param login
	 * @param senha
	 * @param novaSenha
	 * @param confirmacaoSenha
	 * @param nome
	 * @param statusUsuarioEnum
	 * @param funcaoUsuarioEnum
	 * @param dataAtivacao
	 * @param dataDesativacao
	 * @param perfis
	 */
	public Usuario(Long id, Email email, String login, String senha, String novaSenha, String confirmacaoSenha,
			String nome, StatusUsuarioEnum statusUsuarioEnum, FuncaoUsuarioEnum funcaoUsuarioEnum, Date dataAtivacao,
			Date dataDesativacao, List<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.novaSenha = novaSenha;
		this.confirmacaoSenha = confirmacaoSenha;
		this.nome = nome;
		this.statusUsuarioEnum = statusUsuarioEnum;
		this.funcaoUsuarioEnum = funcaoUsuarioEnum;
		this.dataAtivacao = dataAtivacao;
		this.dataDesativacao = dataDesativacao;
		this.perfis = perfis;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Email getEmail() {
		if (email == null) {
			email = new Email();
		}
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public StatusUsuarioEnum getStatusUsuarioEnum() {
		return statusUsuarioEnum;
	}
	public void setStatusUsuarioEnum(StatusUsuarioEnum statusUsuarioEnum) {
		this.statusUsuarioEnum = statusUsuarioEnum;
	}
	public FuncaoUsuarioEnum getFuncaoUsuarioEnum() {
		return funcaoUsuarioEnum;
	}
	public void setFuncaoUsuarioEnum(FuncaoUsuarioEnum funcaoUsuarioEnum) {
		this.funcaoUsuarioEnum = funcaoUsuarioEnum;
	}

	@Override
	public String toString() {
		return String.format("%s > %s", getEntidade(), getDescricao());
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}
	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}
	public Date getDataDesativacao() {
		return dataDesativacao;
	}
	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}
	public List<Perfil> getPerfis() {
		if (perfis == null) {
			perfis = new ArrayList<Perfil>(0);
		}
		return perfis;
	}
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	public String getTooltipStatus() {
		return tooltipStatus;
	}
	public void setTooltipStatus(String tooltipStatus) {
		this.tooltipStatus = tooltipStatus;
	}
	private String getEntidade() {
		return "Usuário";
	}
	private String getDescricao() {
		return nome + " " + email;
	}
	public Boolean getPerfilCliente() {
		if (perfilCliente == null) {
			perfilCliente = new Boolean(Boolean.FALSE);
		}
		return perfilCliente;
	}
	public Boolean getPerfilFuncionario() {
		if (perfilFuncionario == null) {
			perfilFuncionario = new Boolean(Boolean.FALSE);
		}
		return perfilFuncionario;
	}
	public Boolean getPerfilAdministrador() {
		if (perfilAdministrador == null) {
			perfilAdministrador = new Boolean(Boolean.FALSE);
		}
		return perfilAdministrador;
	}
	public Boolean getPerfilOperador() {
		if (perfilOperador == null) {
			perfilOperador = new Boolean(Boolean.FALSE);
		}
		return perfilOperador;
	}
	public Boolean getPerfilROOT() {
		if (perfilROOT == null) {
			perfilROOT = new Boolean(Boolean.FALSE);
		}
		return perfilROOT;
	}
	public void setPerfilCliente(Boolean perfilCliente) {
		this.perfilCliente = perfilCliente;
	}
	public void setPerfilFuncionário(Boolean perfilFuncionário) {
		this.perfilFuncionario = perfilFuncionário;
	}
	public void setPerfilAdministrador(Boolean perfilAdministrador) {
		this.perfilAdministrador = perfilAdministrador;
	}
	public void setPerfilOperador(Boolean perfilOperador) {
		this.perfilOperador = perfilOperador;
	}
	public void setPerfilROOT(Boolean perfilROOT) {
		this.perfilROOT = perfilROOT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
