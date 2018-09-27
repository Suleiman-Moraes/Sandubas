package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Pais;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class PaisDAO extends GenericDAO<Pais, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	public Pais buscarPaisPadrao() {
		try {
			return this.getEntityManager().createQuery("FROM Pais p  WHERE p.nome = :pais ", Pais.class)
					.setParameter("pais", "Brasil").getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Pais buscarPaisNaoInformado() {
		try {
			return this.getEntityManager().createQuery("FROM Pais p  WHERE p.nome = :pais ", Pais.class)
					.setParameter("pais", "Não informado").getSingleResult();
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
	public List<Pais> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
