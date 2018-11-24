package br.com.sandubas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.sandubas.model.enums.TipoTelefoneEnum;
import br.com.sandubas.model.interfaces.IEntidadeRelacional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable, IEntidadeRelacional{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(length = 20)
	private String numero;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoTelefoneEnum tipoTelefoneEnum;

	public Telefone() {}
	public Telefone(Long id, String numero, TipoTelefoneEnum tipoTelefoneEnum) {
		this.id = id;
		this.numero = numero;
		this.tipoTelefoneEnum = tipoTelefoneEnum;
	}
	public Telefone(Telefone objeto) {
		this.id = objeto.id;
		this.numero = objeto.numero;
		this.tipoTelefoneEnum = objeto.tipoTelefoneEnum;
	}
	
	//Metodos
	@Override
	public Object getAtributoIndentificador() {
		return this.getId();
	}
	@Override
	public String getNomeTabela() {
		return "telefone";
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
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}