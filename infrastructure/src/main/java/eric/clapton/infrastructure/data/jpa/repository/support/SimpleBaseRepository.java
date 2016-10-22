package eric.clapton.infrastructure.data.jpa.repository.support;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.infrastructure.data.jpa.repository.RepositoryHelper;
import eric.clapton.infrastructure.data.jpa.repository.callback.SearchCallback;
import eric.clapton.infrastructure.data.jpa.repository.support.annotation.QueryJoin;
import eric.clapton.infrastructure.data.jpa.search.Searchable;

public class SimpleBaseRepository<M, ID extends Serializable> extends SimpleJpaRepository<M, ID>
		implements BaseRepository<M, ID> {
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";

	public static final String FIND_QUERY_STRING = "from %s x where 1 = 1 ";

	public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1 = 1 ";

	private final EntityManager em;
	private final JpaEntityInformation<M, ID> entityInformation;

	private final RepositoryHelper repositoryHelper;

	private CrudMethodMetadata metadata;

	private Class<M> entityClass;

	private String entityName;
	private String findAllQL;
	private String countAllQL;
	private QueryJoin[] joins;
	private SearchCallback searchCallback = SearchCallback.DEFAULT;

	public SimpleBaseRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.entityInformation = entityInformation;
		this.em = entityManager;
		this.entityClass = this.entityInformation.getJavaType();
		this.entityName = this.entityInformation.getEntityName();

		this.repositoryHelper = new RepositoryHelper(entityClass);

		this.findAllQL = String.format(FIND_QUERY_STRING, entityName);
		this.countAllQL = String.format(COUNT_QUERY_STRING, entityName);
	}

	@Override
	public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
		super.setRepositoryMethodMetadata(crudMethodMetadata);

		this.metadata = crudMethodMetadata;
	}

	public void setSearchCallback(SearchCallback searchCallback) {
		this.searchCallback = searchCallback;
	}

	public void setFindAllQL(String findAllQL) {
		this.findAllQL = findAllQL;
	}

	public void setCountAllQL(String countAllQL) {
		this.countAllQL = countAllQL;
	}

	public void setJoins(QueryJoin[] joins) {
		this.joins = joins;
	}

	// ///////////////////////////////////////////////
	// //////覆盖默认spring data jpa的实现////////////
	// ///////////////////////////////////////////////

	/**
	 * 根据主键删除相应实体.
	 * 
	 * @param id
	 *            主键
	 */
	@Transactional
	@Override
	public void delete(final ID id) {
		M m = findOne(id);
		if (m == null) {
			return;
		}

		delete(m);
	}

	/**
	 * 删除实体.
	 * 
	 * @param m
	 *            实体
	 */
	@Transactional
	@Override
	public void delete(final M m) {
		if (m == null) {
			return;
		}

		super.delete(m);
	}

	public void delete(Iterable<? extends M> entities) {
		for (M entity : entities) {
			delete(entity);
		}
	}

	@Transactional
	@Override
	public void deleteInBatch(final Iterable<M> entities) {
		super.delete(entities);
	}

	/**
	 * 按照主键查询.
	 * 
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	@Transactional
	@Override
	public M findOne(ID id) {
		if (id == null) {
			return null;
		}
		if (id instanceof Integer && ((Integer) id).intValue() == 0) {
			return null;
		}
		if (id instanceof Long && ((Long) id).longValue() == 0L) {
			return null;
		}
		return super.findOne(id);
	}

	// //////根据Specification查询
	// 直接从SimpleJpaRepository复制过来的///////////////////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
	 * findOne (org.springframework.data.jpa.domain.Specification)
	 */
	@Override
	public M findOne(Specification<M> spec) {
		try {
			return getQuery(spec, (Sort) null).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findAll(ID[])
	 */
	@Override
	public List<M> findAll(Iterable<ID> ids) {

		return getQuery(new Specification<M>() {
			@SuppressWarnings("synthetic-access")
			@Override
			public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<?> path = root.get(entityInformation.getIdAttribute());
				return path.in(cb.parameter(Iterable.class, "ids"));
			}
		}, (Sort) null).setParameter("ids", ids).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll
	 * (org.springframework.data.jpa.domain.Specification)
	 */
	@Override
	public List<M> findAll(Specification<M> spec) {
		return getQuery(spec, (Sort) null).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll
	 * (org.springframework.data.jpa.domain.Specification,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<M> findAll(Specification<M> spec, Pageable pageable) {
		return findAll(spec, pageable, false);
	}

	@Override
	public Page<M> findAll(Specification<M> spec, Pageable pageable, boolean pageCountDisabled) {

		TypedQuery<M> query = getQuery(spec, pageable);
		return pageable == null ? new PageImpl<M>(query.getResultList())
				: readPage(query, pageable, spec, pageCountDisabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll
	 * (org.springframework.data.jpa.domain.Specification,
	 * org.springframework.data.domain.Sort)
	 */
	@Override
	public List<M> findAll(Specification<M> spec, Sort sort) {

		return getQuery(spec, sort).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#count
	 * (org.springframework.data.jpa.domain.Specification)
	 */
	@Override
	public long count(Specification<M> spec) {

		return getCountQuery(spec).getSingleResult();
	}

	// //////根据Specification查询
	// 直接从SimpleJpaRepository复制过来的///////////////////////////////////

	// /////直接从SimpleJpaRepository复制过来的///////////////////////////////

	/**
	 * Reads the given {@link javax.persistence.TypedQuery} into a
	 * 
	 * @param query
	 *            must not be {@literal null}.
	 * @param pageable
	 *            can be {@literal null}.
	 * @param spec
	 *            can be {@literal null}.
	 * @return the page {@link org.springframework.data.domain.Page} applying
	 *         the given {@link org.springframework.data.domain.Pageable} and
	 *         {@link org.springframework.data.jpa.domain.Specification}.
	 */
	@Override
	protected Page<M> readPage(TypedQuery<M> query, Pageable pageable, Specification<M> spec) {
		return readPage(query, pageable, spec, false);
	}

	protected Page<M> readPage(TypedQuery<M> query, Pageable pageable, Specification<M> spec,
			boolean pageCountDisabled) {
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Long total = pageCountDisabled ? Long.MAX_VALUE : executeCountQuery(getCountQuery(spec));
		List<M> content = total > pageable.getOffset() ? query.getResultList() : Collections.<M> emptyList();

		return new PageImpl<M>(content, pageable, total);
	}

	private static Long executeCountQuery(TypedQuery<Long> query) {
		Assert.notNull(query);

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}

	/**
	 * Creates a new count query for the given.
	 * 
	 * @param spec
	 *            can be {@literal null}.
	 * @return the count query
	 *         {@link org.springframework.data.jpa.domain.Specification}.
	 */
	@Override
	protected TypedQuery<Long> getCountQuery(Specification<M> spec) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<M> root = applySpecificationToCriteria(spec, query);

		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}

		TypedQuery<Long> q = em.createQuery(query);
		repositoryHelper.applyEnableQueryCache(q);
		return q;
	}

	/**
	 * Creates a new {@link javax.persistence.TypedQuery} from the given
	 * 
	 * @param spec
	 *            can be {@literal null}.
	 * @param pageable
	 *            can be {@literal null}.
	 * @return the query
	 *         {@link org.springframework.data.jpa.domain.Specification}.
	 */
	@Override
	protected TypedQuery<M> getQuery(Specification<M> spec, Pageable pageable) {

		Sort sort = pageable == null ? null : pageable.getSort();
		return getQuery(spec, sort);
	}

	/**
	 * Creates a {@link javax.persistence.TypedQuery} for the given
	 * 
	 * @param spec
	 *            can be {@literal null}.
	 * @param sort
	 *            can be {@literal null}.
	 * @return the query
	 *         {@link org.springframework.data.jpa.domain.Specification} and
	 *         {@link org.springframework.data.domain.Sort}.
	 */
	@Override
	protected TypedQuery<M> getQuery(Specification<M> spec, Sort sort) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<M> query = builder.createQuery(entityClass);

		Root<M> root = applySpecificationToCriteria(spec, query);
		query.select(root);

		applyJoins(root);

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		TypedQuery<M> q = em.createQuery(query);

		repositoryHelper.applyEnableQueryCache(q);

		return applyLockMode(q);
	}

	/**
	 * Apply joins.
	 * 
	 * @param root
	 *            the root
	 */
	private void applyJoins(Root<M> root) {
		if (joins == null) {
			return;
		}

		for (QueryJoin join : joins) {
			root.join(join.property(), join.joinType());
		}
	}

	/**
	 * Applies the given.
	 * 
	 * @param <S>
	 *            the generic type
	 * @param spec
	 *            can be {@literal null}.
	 * @param query
	 *            must not be {@literal null}.
	 * @return the root
	 *         {@link org.springframework.data.jpa.domain.Specification} to the
	 *         given {@link javax.persistence.criteria.CriteriaQuery}.
	 */
	private <S> Root<M> applySpecificationToCriteria(Specification<M> spec, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<M> root = query.from(entityClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	/**
	 * Apply lock mode.
	 * 
	 * @param query
	 *            the query
	 * @return the typed query
	 */
	private TypedQuery<M> applyLockMode(TypedQuery<M> query) {
		LockModeType type = metadata == null ? null : metadata.getLockModeType();
		return type == null ? query : query.setLockMode(type);
	}

	// /////直接从SimpleJpaRepository复制过来的///////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
	 * findAll ()
	 */
	@Override
	public List<M> findAll() {
		return repositoryHelper.findAll(findAllQL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
	 * findAll (org.springframework.data.domain.Sort)
	 */
	@Override
	public List<M> findAll(final Sort sort) {
		return repositoryHelper.findAll(findAllQL, sort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
	 * findAll (org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<M> findAll(final Pageable pageable) {
		return new PageImpl<M>(repositoryHelper.<M> findAll(findAllQL, pageable), pageable,
				repositoryHelper.count(countAllQL));
	}

	@Override
	public long count() {
		// em.createQuery(getCountQueryString(), Long.class).getSingleResult();
		return repositoryHelper.count(countAllQL);
	}

	@Override
	public Page<M> findAll(final Searchable searchable) {
		List<M> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);

		long total;
		if (searchable.hasPageable()) {
			if (searchable.isPageCountDisabled()) {
				total = Integer.MAX_VALUE;
			} else {
				total = count(searchable);
			}
		} else {
			total = list.size();
		}
		return new PageImpl<M>(list, searchable.getPage(), total);
	}

	@Override
	public long count(final Searchable searchable) {
		return repositoryHelper.count(countAllQL, searchable, searchCallback);
	}

	@Override
	public boolean exists(ID id) {
		return findOne(id) != null;
	}

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}
}
