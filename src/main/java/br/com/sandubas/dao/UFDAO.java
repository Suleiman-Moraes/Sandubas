package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.UF;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class UFDAO extends GenericDAO<UF, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	public UF buscarUFPassandoSiglaUF(String uf) {
		try {
			return this.getEntityManager().createQuery("FROM UF uf WHERE uf.sigla = :uf ", UF.class)
					.setParameter("uf", uf).getSingleResult();
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
	public List<UF> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
