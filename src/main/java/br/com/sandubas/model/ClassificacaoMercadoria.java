package br.com.sandubas.model;

import java.io.Serializable;

import br.com.sandubas.model.interfaces.IEntidadeRelacional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassificacaoMercadoria implements Serializable, IEntidadeRelacional{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private String descricao;

	public ClassificacaoMercadoria() {}
	public ClassificacaoMercadoria(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	public ClassificacaoMercadoria(ClassificacaoMercadoria objeto) {
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
		return "classificacao_mercadoria";
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
		ClassificacaoMercadoria other = (ClassificacaoMercadoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
