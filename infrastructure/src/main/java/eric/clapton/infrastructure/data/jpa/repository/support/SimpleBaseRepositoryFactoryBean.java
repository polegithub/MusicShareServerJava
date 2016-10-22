package eric.clapton.infrastructure.data.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class SimpleBaseRepositoryFactoryBean<TRepository extends JpaRepository<M, ID>, M, ID extends Serializable>
		extends JpaRepositoryFactoryBean<TRepository, M, ID> {

	public SimpleBaseRepositoryFactoryBean() {

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new SimpleBaseRepositoryFactory(entityManager);
	}
}
