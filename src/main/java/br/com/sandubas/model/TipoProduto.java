package br.com.sandubas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.sandubas.model.interfaces.IEntidadeRelacional;

@Entity
@Table(name = "tipo_produto")
public class TipoProduto implements Serializable, IEntidadeRelacional{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String descricao;

	public TipoProduto() {}
	public TipoProduto(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	public TipoProduto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	public TipoProduto(TipoProduto objeto) {
		this.id = objeto.id;
		this.nome = objeto.nome;
		this.descricao = objeto.descricao;
	}
	
	//Metodos
	@Override
	public Object getAtributoIndentificador() {
		return this.getId();
	}
	@Override
	public String getNomeTabela() {
		return "tipo_produto";
	}
	
	//Getters And Setters
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		TipoProduto other = (TipoProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
