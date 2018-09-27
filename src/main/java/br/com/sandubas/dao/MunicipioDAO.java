package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Municipio;
import br.com.sandubas.model.UF;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class MunicipioDAO extends GenericDAO<Municipio, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	public List<Municipio> buscarMunicipiosPassandoUF(UF uf) {
		try {
			return this.getEntityManager().createQuery("FROM Municipio m WHERE m.uf = :uf ", Municipio.class)
					.setParameter("uf", uf).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public Municipio buscarMunicipioPassandoUfELocalidade(UF uf, String localidade) {
		try {
			return this.getEntityManager()
					.createQuery("FROM Municipio m WHERE m.uf = :uf AND m.nome = :localidade ", Municipio.class)
					.setParameter("uf", uf).setParameter("localidade", localidade).getSingleResult();
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
	public List<Municipio> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
