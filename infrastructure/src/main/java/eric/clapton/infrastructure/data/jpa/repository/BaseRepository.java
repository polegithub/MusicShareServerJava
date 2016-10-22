package eric.clapton.infrastructure.data.jpa.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import eric.clapton.infrastructure.data.jpa.search.Searchable;

/**
 * <p>
 * 抽象DAO层基类 提供一些简便方法<br/>
 * 想要使用该接口需要在spring配置文件的jpa:repositories中添加
 * factory-class="SimpleBaseRepositoryFactoryBean"
 * <p/>
 * <p>
 * 泛型 ： M 表示实体类型；ID表示主键类型.
 * 
 * @param <M>
 *            the generic type
 * @param <ID>
 *            the generic type
 * @author wuwei 2014-4-29
 */
@NoRepositoryBean
public interface BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID> {
	public final static String QUERY_HINT_HIBERNATE_CACHEABLE = "org.hibernate.cacheable";

	List<M> findAll();

	List<M> findAll(Sort sort);

	Page<M> findAll(Pageable pageable);

	Page<M> findAll(Searchable searchable);

	long count(Searchable searchable);

	M findOne(Specification<M> spec);

	List<M> findAll(Specification<M> spec);

	Page<M> findAll(Specification<M> spec, Pageable pageable);

	Page<M> findAll(Specification<M> spec, Pageable pageable, boolean pageCountDisabled);

	EntityManager getEntityManager();

}
