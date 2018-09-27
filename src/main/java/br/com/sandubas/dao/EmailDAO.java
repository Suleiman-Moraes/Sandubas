package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Email;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class EmailDAO extends GenericDAO<Email, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	public List<Email> paginarUnidades(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Email.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	public List<Email> buscarEmailsDosManifestantes() {
		try {
			return (this.getEntityManager()
					.createQuery(
							"select DISTINCT(email) from Email email where email.id in (select ma.id.email.id from ManifestacaoEmail ma group by ma.id.email.id) order by email asc",
							Email.class)
					.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Email> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
