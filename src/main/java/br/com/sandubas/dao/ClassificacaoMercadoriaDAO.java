package br.com.sandubas.dao;

import java.util.List;
import java.util.Map;

import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.interfaces.IPaginarDAO;
import br.com.sandubas.util.jpa.GenericDAO;

public class ClassificacaoMercadoriaDAO extends GenericDAO<ClassificacaoMercadoria, Long> implements IPaginarDAO<ClassificacaoMercadoria> {

	private static final long serialVersionUID = -827632692877631146L;

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassificacaoMercadoria> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassificacaoMercadoria> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(ClassificacaoMercadoria.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	@Override
	public List<ClassificacaoMercadoria> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(ClassificacaoMercadoria.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}

}
