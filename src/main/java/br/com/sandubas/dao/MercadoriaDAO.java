package br.com.sandubas.dao;

import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Mercadoria;
import br.com.sandubas.model.interfaces.IPaginarDAO;
import br.com.sandubas.util.jpa.GenericDAO;

public class MercadoriaDAO extends GenericDAO<Mercadoria, Long> implements IPaginarDAO<Mercadoria> {

	private static final long serialVersionUID = 8363480582292076870L;

	@Override
	public List<Mercadoria> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Mercadoria.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}
	
	public List<Mercadoria> paginar(String join, Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Mercadoria.class, tamanhoDaPagina, inicioDaPagina, join, condicao);
	}

	@Override
	public List<Mercadoria> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(Mercadoria.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mercadoria> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}
}
