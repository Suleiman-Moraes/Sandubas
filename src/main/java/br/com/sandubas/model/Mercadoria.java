package br.com.sandubas.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.sandubas.model.interfaces.IEntidadeRelacional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mercadoria implements IEntidadeRelacional, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double precoPago;
	private Double porcentagemVenda;
	private String marca;
	private Double quantidade;
	private String valorMedida;
	private Double valorAgrupamento;
	private String descricao;
	private TipoProduto tipoProduto;
	private ClassificacaoMercadoria classificacaoMercadoria; 

	@Override
	public Object getAtributoIndentificador() {
		return this.getId();
	}

	@Override
	public String getNomeTabela() {
		// TODO Auto-generated method stub
		return null;
	}
}
