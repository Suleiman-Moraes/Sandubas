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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	@NotNull
	@Column(length = 250)
	private String bairro;

	@Column(length = 250)
	private String complemento;

	@NotNull
	@Column(length = 250)
	private String logradouro;

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

	public Endereco() {}

	public Municipio getMunicipio() {
		if (municipio == null) {
			municipio = new Municipio();
		}
		return municipio;
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
