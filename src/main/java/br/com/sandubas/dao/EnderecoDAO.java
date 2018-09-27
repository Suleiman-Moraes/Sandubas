package br.com.sandubas.dao;

import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Endereco;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class EnderecoDAO extends GenericDAO<Endereco, Long> {

	private static final long serialVersionUID = 4702159857986780715L;

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Endereco> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
