package br.com.sandubas.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.util.jpa.GenericDAO;

@SuppressWarnings("all")
public class UsuarioDAO extends GenericDAO<Usuario, Long> implements Serializable {

	private static final long serialVersionUID = -5775592693312823732L;

	@Override
	public List<Usuario> getWithPagination(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	public Usuario login(String username) {
		try {
			CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
			Root<Usuario> usuario = criteriaQuery.from(Usuario.class);
			Join<Usuario, Perfil> perfil = usuario.join("perfis", JoinType.LEFT);
			criteriaQuery.select(usuario).distinct(true);
			criteriaQuery.where(builder.equal(usuario.get("login"), username));
			TypedQuery<Usuario> query = getEntityManager().createQuery(criteriaQuery);
			Usuario us = query.getSingleResult();
			return us;
		} catch (Exception e) {
			return null;
		}
	}

	public List<String> permissoesPassandoUsuarioAutenticacaoSpringSecurity(Usuario usuario) {
		return getEntityManager().createQuery(
				"select distinct fun.descricao from Usuario u inner join u.perfis p inner join p.funcionalidades fun where u = :usuario",
				String.class).setParameter("usuario", usuario).getResultList();
	}

	public List<Usuario> buscarUsuarioPassandoPerfil(Perfil perfil) {
		try {
			return getEntityManager()
					.createQuery("select p from Usuario p where :perfil MEMBER OF p.perfis", Usuario.class)
					.setParameter("perfil", perfil).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> retornaTodosUsuariosOrdenados() {
		return this.getEntityManager().createQuery("SELECT u FROM Usuario u ORDER BY u.nome ASC").getResultList();
	}

	public Usuario buscarUsuarioPorLogin(String login) {
		try {
			return getEntityManager()
					.createQuery("select p from Usuario p where UPPER(p.login) = :login", Usuario.class)
					.setParameter("login", login.toUpperCase()).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Usuario buscarUsuarioPassandoId(Long id) {
		try {
			return this.getEntityManager()
					.createQuery("select u FROM Usuario u left join u.perfis WHERE u.id = :id ", Usuario.class)
					.setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> buscarUsuariosOrdenadosPorNome() {
		try {
			return this.getEntityManager().createQuery("FROM Usuario u order by u.nome ASC ", Usuario.class)
					.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> paginarUsuarios(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao) {
		return this.paginate(Usuario.class, tamanhoDaPagina, inicioDaPagina, condicao);
	}

	public List<Usuario> paginarUsuarios(Integer inicioDaPagina, Integer tamanhoDaPagina, String join, Object obj,
			boolean parametro, String... condicao) {
		return this.paginate(Usuario.class, tamanhoDaPagina, inicioDaPagina, join, obj.getClass(), obj, parametro,
				condicao);
	}

	@Override
	public Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Usuario> buscarUsuariosOuvidores() {
		try {
			return getEntityManager().createQuery(
					"select u from Usuario u where u.funcaoUsuarioEnum = :funcaoUsuarioEnum order by u.nome ASC",
					Usuario.class).setParameter("funcaoUsuarioEnum", FuncaoUsuarioEnum.OUVIDOR).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> buscarUsuariosIntelocutores() {
		try {
			return getEntityManager().createQuery(
					"SELECT u FROM Usuario u WHERE u.funcaoUsuarioEnum = :funcaoUsuarioEnum ORDER BY u.nome ASC",
					Usuario.class).setParameter("funcaoUsuarioEnum", FuncaoUsuarioEnum.INTERLOCUTOR).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> buscarUsuariosExternoPassandoEmail(String email) {
		try {
			List<Usuario> lista = getEntityManager().createQuery(
					"SELECT u FROM Usuario u WHERE u.funcaoUsuarioEnum = :funcaoUsuarioEnum AND email.email = :email",
					Usuario.class).setParameter("funcaoUsuarioEnum", FuncaoUsuarioEnum.MANIFESTANTE)
					.setParameter("email", email).getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> buscarOperadoresIntelocutores() {
		try {
			return getEntityManager().createQuery(
					"SELECT u FROM Usuario u WHERE u.funcaoUsuarioEnum = 'OPERADOR' OR funcaoUsuarioEnum = 'INTERLOCUTOR' ORDER BY u.nome",
					Usuario.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}
}
