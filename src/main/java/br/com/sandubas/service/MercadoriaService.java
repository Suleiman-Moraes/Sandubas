package br.com.sandubas.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.sandubas.dao.MercadoriaDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Mercadoria;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.model.interfaces.IPaginacaoService;
import br.com.sandubas.util.jsf.FacesUtil;
import lombok.Getter;

public class MercadoriaService implements Serializable, ICRUDService<Mercadoria>, IPaginacaoService<Mercadoria> {

	private static final long serialVersionUID = 7205023645288324462L;
	
	@Getter
	@Inject
	private MercadoriaDAO persistencia;
	
	@Getter
	@Inject
	private ClassificacaoMercadoriaService classificacaoMercadoriaService;  
	
	@Getter
	@Inject
	private TipoProdutoService tipoProdutoService;  

	@Override
	public List<Mercadoria> paginarRegistro(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo,
			String valor) {
		List<Mercadoria> listaRegistro = null;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		String join = " join fetch mercadoria.tipoProduto tp join fetch mercadoria.classificacaoMercadoria cm ";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			condicao += devolverCondicaoParaPaginacao(campo, valor);
		}
		condicao += " ORDER BY mercadoria.id DESC";
		
		listaRegistro = persistencia.paginar(join, inicioDaPagina, tamanhoDaPagina, condicao);
		
		return listaRegistro;
	}

	@Override
	public Integer contarRegistrosCadastrados(String campo, String valor) {
		Integer total = 0;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			condicao += devolverCondicaoParaPaginacao(campo, valor);
		}
		total = persistencia.count(Mercadoria.class, condicao).intValue();
		return total;
	}

	@Override
	public String devolverCondicaoParaPaginacao(String campo, String valor) {
		String condicao = "";
		switch (campo) {
		case "id":
		case "precoPago":
		case "porcentagemVenda":
		case "quantidade":
		case "valorAgrupamento":
			condicao += String.format(" AND %s = %s", campo, valor);
			break;
		case "marca":
		case "valorMedida":
		case "descricao":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(mercadoria." + campo + ")", valor.toUpperCase());
			break;
		case "tipoProduto_nome":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(mercadoria.tipoProduto.nome)", valor.toUpperCase());
			break;
		case "classificacaoMercadoria_nome":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(mercadoria.classificacaoMercadoria.nome)", valor.toUpperCase());
			break;
		}
		return condicao;
	}

	@Override
	public void salvar(Mercadoria objeto) throws NegocioException {
		try {
			if (!this.registroExiste(objeto)) {
				this.persistencia.update(objeto);
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("mercadoriaExistente"),
						Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public void excluir(Mercadoria objeto) throws NegocioException {
		try {
//			this.validarExclusao(objeto.getId(), "areaEntrada", Protocolo.class.getName(), "areaEntradaInvalidaExclusaoManifestacao");
			this.persistencia.delete(Mercadoria.class, objeto.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("mercadoriaExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public Boolean registroExiste(Mercadoria objeto) {
		//TODO implementar
		return Boolean.FALSE;
	}
}
