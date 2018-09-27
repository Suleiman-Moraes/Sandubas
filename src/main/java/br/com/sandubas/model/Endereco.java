package br.com.sandubas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@NotNull
	@Column(length = 250)
	private String bairro;

	@Column(length = 250)
	private String complemento;

	@NotBlank
	@NotNull
	@Column(length = 250)
	private String logradouro;

	@NotBlank
	@NotNull
	@Column(length = 20)
	private String cep;

	private Integer numero;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_municipio")
	private Municipio municipio;

	@Transient
	private String localidade;

	@Transient
	private String uf;

	@Transient
	@JsonIgnore
	private Boolean erro;

	public Endereco() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Municipio getMunicipio() {
		if (municipio == null) {
			municipio = new Municipio();
		}
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Boolean getErro() {
		return erro;
	}

	public void setErro(Boolean erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", bairro=" + bairro + ", complemento=" + complemento + ", logradouro="
				+ logradouro + ", cep=" + cep + ", numero=" + numero + ", municipio=" + municipio + "]";
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
