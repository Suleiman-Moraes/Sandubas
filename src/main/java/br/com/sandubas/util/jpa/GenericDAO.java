package br.com.sandubas.util.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.DAOException;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.helper.Assert;
import lombok.Setter;

public abstract class GenericDAO<T, PK extends Serializable> extends LazyDataModel<T> implements Serializable {

	private static final long serialVersionUID = -1350868251167801491L;

	@Setter
	protected EntityManagerFactory entityManagerFactory;

	@Inject
	protected EntityManager entityManager;

	public Object executeQuery(String query, Object... params) {
		Query createdQuery = this.entityManager.createQuery(query);
		for (int i = 0; i < params.length; i++) {
			createdQuery.setParameter(i, params[i]);
		}
		return createdQuery.getResultList();
	}

	public long contarPassandoIdColunaTabela(long id, String coluna, String tabela) {
		try {
			return getEntityManager()
					.createQuery("select count(t) from " + tabela + " t where t." + coluna + ".id = :id", Long.class)
					.setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getOrderList(Class<T> type, String field) {
		return this.entityManager.createQuery(("FROM " + type.getName() + " t ORDER BY t." + field)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(Class<T> type) {
		return this.entityManager.createQuery(("FROM " + type.getName())).getResultList();
	}

	public T findById(Class<T> type, PK pk) {
		return this.entityManager.find(type, pk);
	}

	/**
	 * 
	 * @param type
	 * @param pk
	 * @return
	 * @author suleiman
	 */
	public T findByIdEager(Class<T> type, PK pk) {
		TypedQuery<T> query = null;
		Table table = type.getAnnotation(javax.persistence.Table.class);
		if (table != null) {

			String hql = String.format("SELECT %s FROM %s %s", type.getSimpleName().toLowerCase(), type.getSimpleName(),
					type.getSimpleName().toLowerCase());
			//JOIN FETCH apelidoTabela.atributo apelidoAtributo
			Field[] atribustos = type.getDeclaredFields();
			StringBuilder joins = new StringBuilder("");
			StringBuilder where = new StringBuilder("");
			for(Field atributo : atribustos) {
				if(atributo.getAnnotation(ManyToOne.class) != null 
						|| atributo.getAnnotation(OneToOne.class) != null 
						|| atributo.getAnnotation(OneToMany.class) != null) {
					joins.append(" JOIN FETCH ").append(type.getSimpleName().toLowerCase());
					joins.append(".").append(atributo.getName()).append(" ");
					joins.append(atributo.getName().toLowerCase());
				}else if(atributo.getAnnotation(Id.class) != null) {
					where.append(" WHERE ").append(type.getSimpleName().toLowerCase());
					where.append(".").append(atributo.getName()).append(" = '").append(pk).append("'");
				}
			}
			hql += joins.toString();
			hql += where.toString();
			query = this.entityManager.createQuery(hql, type);
			query.setMaxResults(1);
			return query.getSingleResult();

		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public boolean findBy(T t) throws NegocioException {
		return this.entityManager.contains(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> selectList(String select, HashMap<String, Object> map) throws Exception {
		Query query = this.entityManager.createQuery(select);
		List<T> res = new ArrayList<T>();

		try {
			for (String key : map.keySet())
				query.setParameter(key, map.get(key));

			res.addAll(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
		return res;
	}

	public List<?> selectLista(String select, HashMap<String, Object> map) throws Exception {
		Query query = this.entityManager.createQuery(select);
		List<?> res = null;

		try {
			for (String key : map.keySet())
				query.setParameter(key, map.get(key));

			res = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
		return res;
	}

	@Transactional
	public void insert(T entity) throws NegocioException {
		this.entityManager.persist(entity);
	}

	@Transactional
	public void update(T entity) throws NegocioException {
		this.entityManager.merge(entity);
	}

	/**
	 * Abstrai detalhes (iserção ou edição) da persistência de um objeto no banco de
	 * dados. Método experimental.
	 * 
	 * @param entity
	 * @throws DAOException
	 * @author thiago
	 * @throws NegocioException
	 */
	@Transactional
	public void save(T entity) throws DAOException, NegocioException {
		try {
			if (entity != null) {
				Class<?> clazz = entity.getClass();
				Field field = clazz.getField("id");
				field.setAccessible(true);
				// entidade não gerenciada?
				if (!entityManager.contains(entity)) {
					// estado transient.
					if (field.get(entity) == null) {
						entityManager.persist(entity);
					} else {
						entity = entityManager.merge(entity);
					}
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new DAOException(e);
		}
	}

	@Transactional
	public void delete(Class<T> type, PK codigo) throws NegocioException {
		this.entityManager.remove(this.findById(type, codigo));
		this.entityManager.flush();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> elements = this.getWithPagination(first, pageSize, filters);
		this.setRowCount(this.getNumberOfEntities(first, pageSize, filters).intValue());
		return elements;
	}

	public abstract Long getNumberOfEntities(int first, int pageSize, Map<String, Object> filters);

	public abstract List<T> getWithPagination(int first, int pageSize, Map<String, Object> filters);

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}

	public EntityTransaction transaction() {
		return getTransaction();
	}

	public void begin() {
		getTransaction().begin();
	}

	public void commit() {
		getTransaction().commit();
	}

	public void rollback() {
		getTransaction().rollback();
	}

	/**
	 * @author thiago-amm
	 * @param clazz
	 * @param criteria
	 * @return
	 */
	public Long count(Class<T> clazz, String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {

			String hql = String.format("SELECT COUNT(*) FROM %s %s", clazz.getSimpleName(),
					clazz.getSimpleName().toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					conditions.append(c);
				}
				hql = String.format("%s WHERE %s", hql, conditions.toString());
			}
			TypedQuery<Long> query = this.entityManager.createQuery(hql, Long.class);
			return query.getSingleResult();

		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Long countStoredProcedureQueryByDeadLine(Class<T> clazz, int op, String countNamedStoredProcedureQuery,
			Long idUsuario, int funcao, String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery countDeadline = entityManager
					.createNamedStoredProcedureQuery(countNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = countDeadline.setParameter("op", op);
			countDeadline.setParameter("u", idUsuario.intValue());
			countDeadline.setParameter("f", funcao);
			return ((BigInteger) storedProcedure.getSingleResult()).longValue();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Long countStoredProcedureQueryByDeadLine(Class<T> clazz, int op, String countNamedStoredProcedureQuery,
			String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery countDeadline = entityManager
					.createNamedStoredProcedureQuery(countNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = countDeadline.setParameter("op", op);
			return ((BigInteger) storedProcedure.getSingleResult()).longValue();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Long countStoredProcedureQueryByDeadLineDateRegistre(Class<T> clazz, int op,
			String countNamedStoredProcedureQuery, String dataInicio, String dataFim) {
		// dataInicio e dataFim no formato '2015-06-23' - yyyy-MM-dd
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery countDeadlineDateRegistre = entityManager
					.createNamedStoredProcedureQuery(countNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = countDeadlineDateRegistre.setParameter("op", op)
					.setParameter("inicio", dataInicio).setParameter("fim", dataFim);
			return ((BigInteger) storedProcedure.getSingleResult()).longValue();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Long count(Class<T> clazz, String parameter1, String parameter2, java.sql.Date data1, java.sql.Date data2,
			String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			String hql = String.format("SELECT COUNT(*) FROM %s %s", clazz.getSimpleName(),
					clazz.getSimpleName().toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					conditions.append(c);
				}
				hql = String.format("%s WHERE %s", hql, conditions.toString());
			}
			TypedQuery<Long> query = this.entityManager.createQuery(hql, Long.class).setParameter(parameter1, data1)
					.setParameter(parameter2, data2);
			return query.getSingleResult();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * @author thiago-amm
	 * @param clazz
	 * @param rows
	 * @param start
	 * @param criteria
	 * @return
	 */
	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String... criteria) {
		return this.paginate(clazz, rows, start, "", criteria);
	}

	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String join, String... criteria) {
		TypedQuery<T> query = null;
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {

			String hql = String.format("SELECT %s FROM %s %s", clazz.getSimpleName().toLowerCase(),
					clazz.getSimpleName(), clazz.getSimpleName().toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					if (Assert.isNotEmptyOrNull(c)) {
						conditions.append(c);
					}
				}
				if (!conditions.toString().isEmpty()) {
					hql += join == null ? "" : join;
					hql = String.format("%s WHERE %s", hql, conditions.toString());
				}
			}
			rows = rows == null ? 10 : rows;
			start = start == null ? 0 : start;
			query = this.entityManager.createQuery(hql, clazz);
			query.setFirstResult(start);
			query.setMaxResults(rows);
			return query.getResultList();

		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> paginateStoredProcedureQueryByDeadline(Class<T> clazz, Integer rows, Integer start, int op,
			String paginateNamedStoredProcedureQuery, Long idUnidade, String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery findByDeadline = entityManager
					.createNamedStoredProcedureQuery(paginateNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = findByDeadline.setParameter("op", op);
			findByDeadline.setParameter("l", rows);
			findByDeadline.setParameter("o", start);
			if (idUnidade != null) {
				findByDeadline.setParameter("u", idUnidade.intValue());
			}
			return storedProcedure.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> paginateStoredProcedureQueryByDeadline(Class<T> clazz, Integer rows, Integer start, int op,
			String paginateNamedStoredProcedureQuery, Long idUsuario, int funcao, String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery findByDeadline = entityManager
					.createNamedStoredProcedureQuery(paginateNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = findByDeadline.setParameter("op", op);
			findByDeadline.setParameter("f", funcao);
			findByDeadline.setParameter("l", rows);
			findByDeadline.setParameter("o", start);
			findByDeadline.setParameter("u", idUsuario.intValue());
			return storedProcedure.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> paginateStoredProcedureQueryByDeadlineDateRegistre(Class<T> clazz, Integer rows, Integer start,
			int op, String paginateNamedStoredProcedureQuery, String dataInicio, String dataFim) {
		// dataInicio e dataFim no formato '2015-06-23' - yyyy-MM-dd
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			StoredProcedureQuery findByDeadlineDateRegistre = entityManager
					.createNamedStoredProcedureQuery(paginateNamedStoredProcedureQuery);
			StoredProcedureQuery storedProcedure = findByDeadlineDateRegistre.setParameter("op", op);
			findByDeadlineDateRegistre.setParameter("l", rows);
			findByDeadlineDateRegistre.setParameter("o", start);
			findByDeadlineDateRegistre.setParameter("inicio", dataInicio);
			findByDeadlineDateRegistre.setParameter("fim", dataFim);
			return storedProcedure.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String parametro, List<Long> lista,
			String hql) {
		TypedQuery<T> query = null;
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			rows = rows == null ? 10 : rows;
			start = start == null ? 0 : start;
			query = this.entityManager.createQuery(hql, clazz);
			query.setFirstResult(start);
			query.setMaxResults(rows);
			return query.setParameter(parametro, lista).getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<T> paginateListPredicate(CriteriaBuilder cb, Root<T> root, String orderByColumn, Integer rows,
			Integer start, CriteriaQuery<T> cq, List<Predicate> restricoes) {
		cq.where(restricoes.toArray(new Predicate[restricoes.size()]));
		cq.orderBy(cb.desc(root.get(orderByColumn)));
		TypedQuery<T> q = getEntityManager().createQuery(cq);
		if (start != null && rows != null) {
			return q.setFirstResult(start).setMaxResults(rows).getResultList();
		} else {
			return q.getResultList();
		}
	}

	public Long countPaginateListPredicate(CriteriaQuery<Long> countCriteria, CriteriaBuilder cb, Root<T> countRoot,
			List<Predicate> restricoes) {
		countCriteria.select(cb.count(countRoot)).where(restricoes.toArray(new Predicate[restricoes.size()]));
		TypedQuery<Long> queryCount = this.getEntityManager().createQuery(countCriteria);
		Long count = queryCount.getSingleResult();
		return count;
	}

	@SuppressWarnings("rawtypes")
	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String join, Class joinClass, Object obj,
			String... criteria) {
		TypedQuery<T> query = null;
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			String hql = String.format("SELECT DISTINCT(%s) FROM %s %s JOIN %s.%s %s",
					clazz.getSimpleName().toLowerCase(), clazz.getSimpleName(), clazz.getSimpleName().toLowerCase(),
					clazz.getSimpleName().toLowerCase(), join, join.toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					if (Assert.isNotEmptyOrNull(c)) {
						conditions.append(c);
					}
				}
				if (!conditions.toString().isEmpty()) {
					hql = String.format("%s WHERE %s", hql, conditions.toString());
				}
			}
			rows = rows == null ? 10 : rows;
			start = start == null ? 0 : start;
			query = this.entityManager.createQuery(hql, clazz).setParameter(joinClass.getSimpleName().toLowerCase(),
					obj);
			query.setFirstResult(start);
			query.setMaxResults(rows);
			return query.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String parameter1, String parameter2,
			java.sql.Date data1, java.sql.Date data2, String... criteria) {
		TypedQuery<T> query = null;
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			String hql = String.format("SELECT %s FROM %s %s", clazz.getSimpleName().toLowerCase(),
					clazz.getSimpleName(), clazz.getSimpleName().toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					if (Assert.isNotEmptyOrNull(c)) {
						conditions.append(c);
					}
				}
				if (!conditions.toString().isEmpty()) {
					hql = String.format("%s WHERE %s", hql, conditions.toString());
				}
			}
			rows = rows == null ? 10 : rows;
			start = start == null ? 0 : start;

			query = this.entityManager.createQuery(hql, clazz).setParameter(parameter1, data1).setParameter(parameter2,
					data2);
			query.setFirstResult(start);
			query.setMaxResults(rows);
			return query.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List<T> paginate(Class<T> clazz, Integer rows, Integer start, String join, Class joinClass, Object obj,
			boolean parameter, String... criteria) {
		TypedQuery<T> query = null;
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			String hql = String.format("SELECT %s FROM %s %s JOIN %s.%s %s", clazz.getSimpleName().toLowerCase(),
					clazz.getSimpleName(), clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase(),
					join, join.toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					if (Assert.isNotEmptyOrNull(c)) {
						conditions.append(c);
					}
				}
				if (!conditions.toString().isEmpty()) {
					hql = String.format("%s WHERE %s", hql, conditions.toString());
				}
			}
			rows = rows == null ? 10 : rows;
			start = start == null ? 0 : start;
			if (parameter) {
				query = this.entityManager.createQuery(hql, clazz).setParameter(joinClass.getSimpleName().toLowerCase(),
						obj);
			} else {
				query = this.entityManager.createQuery(hql, clazz);
			}
			query.setFirstResult(start);
			query.setMaxResults(rows);
			return query.getResultList();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * @author thiago-amm
	 * @author manoel
	 * @param clazz
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long count(Class<T> clazz, String join, Class joinClass, Object obj, boolean parameter, String... criteria) {
		Table table = clazz.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			String hql = String.format("SELECT %s FROM %s %s JOIN %s.%s %s", clazz.getSimpleName().toLowerCase(),
					clazz.getSimpleName(), clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase(),
					join, join.toLowerCase());
			if (criteria != null && criteria.length >= 1) {
				StringBuilder conditions = new StringBuilder();
				for (String c : criteria) {
					conditions.append(c);
				}
				hql = String.format("%s WHERE %s", hql, conditions.toString());
			}
			TypedQuery<T> query = null;
			if (parameter) {
				query = this.entityManager.createQuery(hql, clazz).setParameter(joinClass.getSimpleName().toLowerCase(),
						obj);
			} else {
				query = this.entityManager.createQuery(hql, clazz);
			}
			List<T> list = query.getResultList();
			return list.stream().distinct().count();
		} else {
			try {
				throw new DAOException("A classe informada não é uma Entidade JPA!");
			} catch (DAOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<T> paginate(Class<T> clazz, Integer rows, Integer start) {
		return paginate(clazz, rows, start, (String[]) null);
	}

	/**
	 * @author thiago-amm
	 * @param clazz
	 * @return
	 */
	public List<T> paginate(Class<T> clazz) {
		return paginate(clazz, null, null, (String[]) null);
	}

	/**
	 * @author thiago-amm
	 * @param clazz
	 * @param criteria
	 * @return
	 */
	public List<T> paginate(Class<T> clazz, String... criteria) {
		return paginate(clazz, null, null, criteria);
	}

	/**
	 * @author thiago-amm
	 * @return
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		if (entityManager != null) {
			return entityManager.getEntityManagerFactory();
		}
		return null;
	}

	/**
	 * @author thiago-amm
	 * @param persistenceUnit
	 * @return
	 */
	public EntityManagerFactory getEntityManagerFactory(String persistenceUnit) {
		persistenceUnit = persistenceUnit == null ? "" : persistenceUnit;
		if (!persistenceUnit.isEmpty()) {
			return entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		}
		return null;
	}

	/**
	 * @author thiago-amm
	 * @param persistenceUnit
	 * @return
	 */
	public EntityManager getEntityManager(String persistenceUnit) {
		EntityManager entityManager = null;
		EntityManagerFactory entityManagerFactory = getEntityManagerFactory(persistenceUnit);
		if (entityManagerFactory != null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	/**
	 * @author thiago-amm
	 * @param persistenceUnit
	 */
	public void use(String persistenceUnit) {
		persistenceUnit = persistenceUnit == null ? "" : persistenceUnit;
		if (!persistenceUnit.isEmpty()) {
			this.entityManager = getEntityManager(persistenceUnit);
		}
	}

	/**
	 * @author thiago-amm
	 * @param hql
	 * @param clazz
	 * @return
	 * @throws DAOException
	 */
	public TypedQuery<T> getQuery(String hql, Class<T> clazz) throws DAOException {
		TypedQuery<T> query = null;
		hql = hql == null ? "" : hql;
		if (!hql.isEmpty()) {
			if (entityManager == null) {
				throw new DAOException("Nenhum EntityManager está sendo referenciado!");
			}
			query = entityManager.createQuery(hql, clazz);
		}
		return query;
	}

	/**
	 * @author thiago-amm
	 * @param hql
	 * @param clazz
	 * @return
	 * @throws DAOException
	 */
	public List<T> select(String hql, Class<T> clazz) throws DAOException {
		List<T> list = null;
		hql = hql == null ? "" : hql;
		if (!hql.isEmpty()) {
			if (entityManager == null) {
				throw new DAOException("Nenhum EntityManager está sendo referenciado!");
			}
			TypedQuery<T> query = entityManager.createQuery(hql, clazz);
			list = query.getResultList();
		}
		return list;
	}

	/**
	 * @author thiago-amm
	 * @param hql
	 * @param clazz
	 * @return
	 * @throws DAOException
	 */
	public int update(String hql, Class<T> clazz) throws DAOException {
		int rowsAffected = 0;
		hql = hql == null ? "" : hql;
		if (!hql.isEmpty()) {
			if (entityManager == null) {
				throw new DAOException("Nenhum EntityManager está sendo referenciado!");
			}
			TypedQuery<T> query = entityManager.createQuery(hql, clazz);
			rowsAffected = query.executeUpdate();
		}
		return rowsAffected;
	}

	public void close() {
		if (entityManager != null) {
			entityManager.close();
		}
	}

}
