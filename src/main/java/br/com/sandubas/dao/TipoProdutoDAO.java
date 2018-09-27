package br.com.sandubas.dao;

import java.util.List;
import java.util.Map;

import br.com.sandubas.model.TipoProduto;
import br.com.sandubas.model.interfaces.IPaginarDAO;
import br.com.sandubas.util.jpa.GenericDAO;

public class TipoProdutoDAO extends GenericDAO<TipoProduto, Long> implements IPaginarDAO<TipoProduto> {

	private static final long serialVersionUID = -827632692877631146L;

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProduto> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProduto> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(TipoProduto.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	@Override
	public List<TipoProduto> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(TipoProduto.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}

}
