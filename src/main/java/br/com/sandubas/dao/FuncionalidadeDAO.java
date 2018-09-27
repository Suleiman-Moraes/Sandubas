package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Funcionalidade;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class FuncionalidadeDAO extends GenericDAO<Funcionalidade, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Funcionalidade> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
