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
import br.com.sandubas.model.interfaces.IEntidadeRelacional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuario implements Serializable, IEntidadeRelacional{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	@NotBlank
	@Column(length = 100, unique = true)
	private String login;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "id_email")
	private Email email;

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "id_telefone")
	private Telefone telefone;

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
	
	@Override
	public Object getAtributoIndentificador() {
		return this.getId();
	}
	@Override
	public String getNomeTabela() {
		return "usuario";
	}
	
	public Email getEmail() {
		if (email == null) {
			email = new Email();
		}
		return email;
	}

	@Override
	public String toString() {
		return String.format("%s > %s", getDescricao());
	}

	public List<Perfil> getPerfis() {
		if (perfis == null) {
			perfis = new ArrayList<Perfil>(0);
		}
		return perfis;
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
	
	public Telefone getTelefone(){
		if(this.telefone == null) {
			this.telefone = new Telefone();
		}
		return this.telefone;
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
