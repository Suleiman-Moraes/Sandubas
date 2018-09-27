package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class PerfilDAO extends GenericDAO<Perfil, Long> implements Serializable {

	private static final long serialVersionUID = 6113587111570027109L;

	private String filtroSelecionado;

	public Perfil buscarPerfilPorNome(String nome) {
		try {
			return this.getEntityManager().createQuery("FROM Perfil p WHERE UPPER(p.nome) = :nome ", Perfil.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Perfil buscarPerfilComFuncionalidadesPassandoId(Long id) {
		try {
			Perfil perfil = super.findById(Perfil.class, id);
			perfil.setFuncionalidades(this.getEntityManager()
					.createQuery("SELECT p.funcionalidades FROM Perfil p WHERE p.id = :id").setParameter("id", id).getResultList());
			return perfil;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		String paramPesquisa = (String) filters.get("globalFilter");
		if (paramPesquisa != null) {
			return this.getEntityManager()
					.createQuery(
							"select count(p) from Perfil p WHERE p." + getFiltroSelecionado() + " LIKE :paramPesquisa",
							Long.class)
					.setParameter("paramPesquisa", "%" + paramPesquisa + "%").getSingleResult();
		} else {
			return this.getEntityManager().createQuery("select count(p) from Perfil p", Long.class).getSingleResult();
		}
	}

	public List<Perfil> buscarPerfisUsuarioPassandoIdUsuario(Long id) {
		return this.getEntityManager()
				.createQuery("select u.perfis FROM Usuario u inner join u.perfis WHERE u.id = :id ")
				.setParameter("id", id).getResultList();
	}

	@Override
	public List<Perfil> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		String paramPesquisa = (String) filters.get("globalFilter");
		if (paramPesquisa != null) {
			return this.getEntityManager()
					.createQuery("FROM Perfil p WHERE p." + getFiltroSelecionado()
							+ " LIKE :paramPesquisa order by p.id DESC")
					.setFirstResult(first).setMaxResults(pageSize)
					.setParameter("paramPesquisa", "%" + paramPesquisa + "%").getResultList();
		} else {
			return this.getEntityManager().createQuery("FROM Perfil p order by p.id DESC").setFirstResult(first)
					.setMaxResults(pageSize).getResultList();
		}
	}


	public List<Perfil> buscarPerfisDoUsuarioPassandoUsuario(Usuario usuario) {
		try {
			return getEntityManager()
					.createQuery("select u.perfis from Usuario u where u.id = :usuario")
					.setParameter("usuario", usuario.getId()).getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getFiltroSelecionado() {
		if (filtroSelecionado == null)
			filtroSelecionado = "nome";
		return filtroSelecionado;
	}

	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}

	// ComboBox
	public List<Perfil> paginarPerfil(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Perfil.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	public List<Perfil> paginarPerfil(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(Perfil.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}
}
