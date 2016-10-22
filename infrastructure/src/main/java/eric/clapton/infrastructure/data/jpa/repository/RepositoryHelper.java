package eric.clapton.infrastructure.data.jpa.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.util.Assert;

import eric.clapton.infrastructure.data.jpa.repository.callback.SearchCallback;
import eric.clapton.infrastructure.data.jpa.repository.support.annotation.EnableQueryCache;
import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.util.StringUtils;

/**
 * 仓库辅助类.
 * 
 * @author wuwei 2014-4-29
 */
public class RepositoryHelper {

	/** The entity manager. */
	private static EntityManager defaultEntityManager;
	private static final Map<String, EntityManager> entityManagers = new HashMap<String, EntityManager>();

	/** The entity class. */
	private Class<?> entityClass;
	private EntityManager entityManager;

	/** The enable query cache. */
	private boolean enableQueryCache = false;

	/**
	 * Instantiates a new repository helper.;
	 * 
	 * @param entityClass
	 *            是否开启查询缓存
	 */
	public RepositoryHelper(Class<?> entityClass) {
		this.entityClass = entityClass;

		EnableQueryCache enableQueryCacheAnnotation = AnnotationUtils.findAnnotation(entityClass,
				EnableQueryCache.class);

		boolean enableQueryCache = false;
		if (enableQueryCacheAnnotation != null) {
			enableQueryCache = enableQueryCacheAnnotation.value();
		}
		this.enableQueryCache = enableQueryCache;

		UseEntityManager q = AnnotationUtils.findAnnotation(entityClass, UseEntityManager.class);
		if (q == null || StringUtils.isNullOrEmpty(q.value())) {
			entityManager = defaultEntityManager;
		} else {
			entityManager = entityManagers.get(q.value());
		}
	}

	/**
	 * Sets the entity manager factory.
	 * 
	 * @param entityManagerFactory
	 *            the new entity manager factory
	 */
	public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		defaultEntityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
		entityManagers.put("", defaultEntityManager);
	}

	public static void setEntityManagerFactories(Map<String, EntityManagerFactory> factories) {
		if (factories != null) {
			for (String k : factories.keySet()) {
				EntityManagerFactory f = factories.get(k);

				entityManagers.put(k, SharedEntityManagerCreator.createSharedEntityManager(f));
			}
		}
	}

	public static Map<String, EntityManager> getEntityManagerFactoryMap() {
		Assert.notNull(defaultEntityManager, "entityManager must null, please see "
				+ "[cn.roomy.logistics.repository.RepositoryHelper#setEntityManagerFactory]");

		return entityManagers;
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Flush.
	 */
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * Clear.
	 */
	public void clear() {
		flush();
		getEntityManager().clear();
	}

	/**
	 * <p>
	 * ql条件查询<br/>
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param searchable
	 *            查询条件、分页 排序
	 * @param searchCallback
	 *            查询回调 自定义设置查询条件和赋值
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public <M> List<M> findAll(final String ql, final Searchable searchable, final SearchCallback searchCallback) {

		assertConverted(searchable);
		StringBuilder s = new StringBuilder(ql);
		searchCallback.prepareQL(s, searchable);
		searchCallback.prepareOrder(s, searchable);
		Query query = getEntityManager().createQuery(s.toString());
		applyEnableQueryCache(query);
		searchCallback.setValues(query, searchable);
		searchCallback.setPageable(query, searchable);

		return query.getResultList();
	}

	/**
	 * <p>
	 * 按条件统计<br/>
	 * 
	 * @param ql
	 *            the ql
	 * @param searchable
	 *            the searchable
	 * @param searchCallback
	 *            the search callback
	 * @return the long
	 */
	public long count(final String ql, final Searchable searchable, final SearchCallback searchCallback) {

		assertConverted(searchable);

		StringBuilder s = new StringBuilder(ql);
		searchCallback.prepareQL(s, searchable);
		Query query = getEntityManager().createQuery(s.toString());
		applyEnableQueryCache(query);
		searchCallback.setValues(query, searchable);

		return (Long) query.getSingleResult();
	}

	/**
	 * 按条件查询一个实体.
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param searchable
	 *            the searchable
	 * @param searchCallback
	 *            the search callback
	 * @return the m
	 */
	@SuppressWarnings("unchecked")
	public <M> M findOne(final String ql, final Searchable searchable, final SearchCallback searchCallback) {

		assertConverted(searchable);

		StringBuilder s = new StringBuilder(ql);
		searchCallback.prepareQL(s, searchable);
		searchCallback.prepareOrder(s, searchable);
		Query query = getEntityManager().createQuery(s.toString());
		applyEnableQueryCache(query);
		searchCallback.setValues(query, searchable);
		searchCallback.setPageable(query, searchable);
		query.setMaxResults(1);
		List<M> result = query.getResultList();

		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Find all.
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param params
	 *            the params
	 * @return the list
	 * @see RepositoryHelper#findAll(String,
	 *      org.springframework.data.domain.Pageable, Object...)
	 */
	public <M> List<M> findAll(final String ql, final Object... params) {

		// 此处必须 (Pageable) null 否则默认有调用自己了 可变参列表
		return findAll(ql, (Pageable) null, params);

	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql，pageable存储分页信息 null表示不分页<br/>
	 * 具体使用请参考测试用例：{@see
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param pageable
	 *            null表示不分页
	 * @param params
	 *            the params
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public <M> List<M> findAll(final String ql, final Pageable pageable, final Object... params) {

		Query query = getEntityManager().createQuery(ql + prepareOrder(pageable != null ? pageable.getSort() : null));
		applyEnableQueryCache(query);
		setParameters(query, params);
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return query.getResultList();
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql，sort存储排序信息 null表示不排序<br/>
	 * 具体使用请参考测试用例：{@see
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param sort
	 *            null表示不排序
	 * @param params
	 *            the params
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public <M> List<M> findAll(final String ql, final Sort sort, final Object... params) {

		Query query = getEntityManager().createQuery(ql + prepareOrder(sort));
		applyEnableQueryCache(query);
		setParameters(query, params);

		return query.getResultList();
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params查询一个实体<br/>
	 * 具体使用请参考测试用例：{@see
	 * 
	 * @param <M>
	 *            the generic type
	 * @param ql
	 *            the ql
	 * @param params
	 *            the params
	 * @return the m
	 */
	public <M> M findOne(final String ql, final Object... params) {

		List<M> list = findAll(ql, new PageRequest(0, 1), params);

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql统计<br/>
	 * testCountAll()
	 * 
	 * @param ql
	 *            the ql
	 * @param params
	 *            the params
	 * @return the long
	 */
	public long count(final String ql, final Object... params) {
		Query query = entityManager.createQuery(ql);
		applyEnableQueryCache(query);
		setParameters(query, params);

		return (Long) query.getSingleResult();
	}

	/**
	 * <p>
	 * 执行批处理语句.如 之间insert, update, delete 等.<br/>
	 * 具体使用请参考测试用例：{@see
	 * 
	 * @param ql
	 *            the ql
	 * @param params
	 *            the params
	 * @return the int
	 */
	public int batchUpdate(final String ql, final Object... params) {

		Query query = getEntityManager().createQuery(ql);
		setParameters(query, params);

		return query.executeUpdate();
	}

	/**
	 * 按顺序设置Query参数.
	 * 
	 * @param query
	 *            the query
	 * @param params
	 *            the params
	 */
	public void setParameters(Query query, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
	}

	/**
	 * 拼排序.
	 * 
	 * @param sort
	 *            the sort
	 * @return the string
	 */
	public String prepareOrder(Sort sort) {
		if (sort == null || !sort.iterator().hasNext()) {
			return "";
		}
		StringBuilder orderBy = new StringBuilder("");
		orderBy.append(" order by ");
		orderBy.append(sort.toString().replace(":", " "));
		return orderBy.toString();
	}

	/**
	 * Gets the metadata.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @return the metadata
	 */
	public <T> JpaEntityInformation<T, ?> getMetadata(Class<T> entityClass) {
		return JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
	}

	/**
	 * Gets the entity name.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @return the entity name
	 */
	public String getEntityName(Class<?> entityClass) {
		return getMetadata(entityClass).getEntityName();
	}

	/**
	 * Assert converted.
	 * 
	 * @param searchable
	 *            the searchable
	 */
	private void assertConverted(Searchable searchable) {
		if (!searchable.isConverted()) {
			searchable.convert(this.entityClass);
		}
	}

	/**
	 * Apply enable query cache.
	 * 
	 * @param query
	 *            the query
	 */
	public void applyEnableQueryCache(Query query) {
		if (enableQueryCache) {
			query.setHint("org.hibernate.cacheable", true);// 开启查询缓存
		}
	}
}
