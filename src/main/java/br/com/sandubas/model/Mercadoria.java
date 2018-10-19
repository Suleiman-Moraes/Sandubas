package br.com.sandubas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.sandubas.model.interfaces.IEntidadeRelacional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mercadoria")
public class Mercadoria implements IEntidadeRelacional, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotNull
	@Column(name = "preco_pago")
	private Double precoPago;
	
	@NotBlank
	@NotNull
	@Column(name = "porcentagem_venda")
	private Double porcentagemVenda;
	
	@NotBlank
	@NotNull
	@Column
	private String marca;
	
	@NotBlank
	@NotNull
	@Column
	private Double quantidade;
	
	@NotBlank
	@NotNull
	@Column(name = "valor_medida")
	private String valorMedida;
	
	@NotBlank
	@NotNull
	@Column(name = "valor_agrupamento")
	private Double valorAgrupamento;
	
	@NotBlank
	@NotNull
	@Column
	private String descricao;
	
	
	private TipoProduto tipoProduto;
	private ClassificacaoMercadoria classificacaoMercadoria; 
	
	public Mercadoria() {}
	public Mercadoria(Long id, Double precoPago, Double porcentagemVenda, String marca, Double quantidade,
			String valorMedida, Double valorAgrupamento, String descricao, TipoProduto tipoProduto,
			ClassificacaoMercadoria classificacaoMercadoria) {
		this.id = id;
		this.precoPago = precoPago;
		this.porcentagemVenda = porcentagemVenda;
		this.marca = marca;
		this.quantidade = quantidade;
		this.valorMedida = valorMedida;
		this.valorAgrupamento = valorAgrupamento;
		this.descricao = descricao;
		this.tipoProduto = tipoProduto;
		this.classificacaoMercadoria = classificacaoMercadoria;
	}
	public Mercadoria(Mercadoria objeto) {
		this.id = objeto.id;
		this.precoPago = objeto.precoPago;
		this.porcentagemVenda = objeto.porcentagemVenda;
		this.marca = objeto.marca;
		this.quantidade = objeto.quantidade;
		this.valorMedida = objeto.valorMedida;
		this.valorAgrupamento = objeto.valorAgrupamento;
		this.descricao = objeto.descricao;
		this.tipoProduto = objeto.tipoProduto;
		this.classificacaoMercadoria = objeto.classificacaoMercadoria;
	}

	@Override
	public Object getAtributoIndentificador() {
		return this.getId();
	}

	@Override
	public String getNomeTabela() {
		return "mercadoria";
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
		Mercadoria other = (Mercadoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
