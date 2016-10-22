package eric.clapton.infrastructure.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.service.BaseService;

public abstract class BaseServiceImpl<TEntity extends BaseEntity, TRepository extends BaseRepository<TEntity, Long>>
		implements BaseService<TEntity> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected TRepository repository;

	protected final TRepository getRepository() {
		return this.repository;
	}

	protected final void setRepository(TRepository repository) {
		this.repository = repository;
	}

	protected abstract void baseSetRepository(TRepository repository);

	/**
	 * 提供简单的围绕方法，在保存实体之前调用。如果进行更复杂的方法拦截，请使用 AOP 或观察者模式实现。
	 * 
	 * @param t
	 *            要保存的实体。
	 * @return 如果要继续保存，则为 true，否则为 false。
	 */
	protected boolean preSave(TEntity t) {
		return true;
	}

	@Override
	public TEntity save(TEntity t) {
		if (t != null) {
			if (preSave(t)) {
				boolean isNew = t.isNew();
				t = repository.save(t);
				postSave(t, isNew);
			}
		}
		return t;
	}

	/**
	 * 提供简单的围绕方法，在保存之后调用。如果进行更复杂的方法拦截，请使用 AOP 或观察者模式实现。
	 * 
	 * @param t
	 *            已保存的实体。
	 * @param isNew
	 *            此实体在保存前是否是新的对象。
	 */
	protected void postSave(TEntity t, boolean isNew) {
	}

	@Override
	public TEntity getOne(Long id) {
		return id == null ? null : repository.getOne(id);
	}

	@Override
	public TEntity findOne(Long id) {
		return id == null ? null : repository.findOne(id);
	}

	@Override
	public TEntity update(TEntity t) {
		if (t != null) {
			if (preSave(t)) {
				boolean isNew = t.isNew();
				t = repository.saveAndFlush(t);
				postSave(t, isNew);
			}
		}
		return t;
	}

	@Override
	public TEntity delete(Long id) {
		return delete(findOne(id));
	}

	@Override
	public Page<TEntity> findAll(Searchable searchable) {
		return repository.findAll(searchable);
	}

	@Override
	public List<TEntity> findAllWithSort(Searchable searchable) {
		searchable.removePageable();
		return Lists.newArrayList(repository.findAll(searchable).getContent());
	}

	protected boolean preDelete(TEntity entity) {
		return true;
	}

	@Override
	public TEntity delete(TEntity m) {
		if (m != null) {
			if (preDelete(m)) {
				repository.delete(m);
				postDelete(m);
			}
		}
		return m;
	}

	protected void postDelete(TEntity entity) {
	}

	@Override
	public List<TEntity> findAll() {
		return repository.findAll();
	}

	protected final Logger getLogger() {
		return logger;
	}
}
