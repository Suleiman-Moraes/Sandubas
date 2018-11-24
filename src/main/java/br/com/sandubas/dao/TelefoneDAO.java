package br.com.sandubas.dao;

import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Telefone;
import br.com.sandubas.model.interfaces.IPaginarDAO;
import br.com.sandubas.util.jpa.GenericDAO;

public class TelefoneDAO extends GenericDAO<Telefone, Long> implements IPaginarDAO<Telefone> {

	private static final long serialVersionUID = -827632692877631146L;

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telefone> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telefone> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Telefone.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	@Override
	public List<Telefone> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(Telefone.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}

}
