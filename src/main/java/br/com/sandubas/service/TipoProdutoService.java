package br.com.sandubas.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.sandubas.dao.TipoProdutoDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.TipoProduto;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.model.interfaces.IPaginacaoService;
import br.com.sandubas.util.jsf.FacesUtil;
import lombok.Setter;

@SuppressWarnings("all")
public class TipoProdutoService implements Serializable, ICRUDService<TipoProduto>, IPaginacaoService<TipoProduto>{

	private static final long serialVersionUID = -8735358206076950412L;
	 
	@Setter
	@Inject 
	private TipoProdutoDAO persistencia;

	@Override
	public List<TipoProduto> paginarRegistro(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo,
			String valor) {
		List<TipoProduto> listaRegistro = null;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			condicao += devolverCondicaoParaPaginacao(campo, valor);
		}
		condicao += " ORDER BY tipoproduto.id DESC";
		
		listaRegistro = persistencia.paginar(inicioDaPagina, tamanhoDaPagina, condicao);
		
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
		total = persistencia.count(TipoProduto.class, condicao).intValue();
		return total;
	}
	
	@Override
	public String devolverCondicaoParaPaginacao(String campo, String valor) {
		String condicao = "";
		switch (campo) {
		case "nome":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(tipoproduto." + campo + ")", valor.toUpperCase());
			break;
		case "id":
			condicao += String.format(" AND %s = %s", campo, valor);
			break;
		case "descricao":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(tipoproduto." + campo + ")", valor.toUpperCase());
			break;
		}
		return condicao;
	}
	
	@Override
	public void salvar(TipoProduto objeto) throws NegocioException {
		try {
			if (!this.registroExiste(objeto)) {
				this.persistencia.update(objeto);
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("tipoProdutoExistente"),
						Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public void excluir(TipoProduto objeto) throws NegocioException {
		try {
//			this.validarExclusao(objeto.getId(), "areaEntrada", Protocolo.class.getName(), "areaEntradaInvalidaExclusaoManifestacao");
			this.persistencia.delete(TipoProduto.class, objeto.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("tipoProdutoExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public Boolean registroExiste(TipoProduto objeto) {
		//TODO implementar
		return Boolean.FALSE;
	}
	
	public TipoProdutoDAO getPersistencia() {
		return persistencia;
	}
}
