package eric.clapton.infrastructure.data.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.StringUtils;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.infrastructure.data.jpa.repository.callback.SearchCallback;
import eric.clapton.infrastructure.data.jpa.repository.support.annotation.SearchableQuery;

public class SimpleBaseRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {

	private EntityManager entityManager;

	public SimpleBaseRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	protected SimpleBaseRepository<M, ID> createRepository(JpaEntityInformation<M, ID> entityInformation,
			EntityManager entityManager) {
		return new SimpleBaseRepository<M, ID>(entityInformation, entityManager);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getTargetRepository(RepositoryInformation information) {
		Class<?> repositoryInterface = information.getRepositoryInterface();

		if (isBaseRepository(repositoryInterface)) {

			JpaEntityInformation<M, ID> entityInformation = getEntityInformation(
					(Class<M>) information.getDomainType());
			SimpleBaseRepository repository = createRepository(entityInformation, entityManager);

			SearchableQuery searchableQuery = AnnotationUtils.findAnnotation(repositoryInterface,
					SearchableQuery.class);
			if (searchableQuery != null) {
				String countAllQL = searchableQuery.countAllQuery();
				if (!StringUtils.isEmpty(countAllQL)) {
					repository.setCountAllQL(countAllQL);
				}
				String findAllQL = searchableQuery.findAllQuery();
				if (!StringUtils.isEmpty(findAllQL)) {
					repository.setFindAllQL(findAllQL);
				}
				Class<? extends SearchCallback> callbackClass = searchableQuery.callbackClass();
				if (callbackClass != null && callbackClass != SearchCallback.class) {
					repository.setSearchCallback(BeanUtils.instantiate(callbackClass));
				}

				repository.setJoins(searchableQuery.joins());

			}

			return repository;
		}
		return super.getTargetRepository(information);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isBaseRepository(metadata.getRepositoryInterface())) {
			return SimpleBaseRepository.class;
		}
		return super.getRepositoryBaseClass(metadata);
	}

	private boolean isBaseRepository(Class<?> repositoryInterface) {
		return BaseRepository.class.isAssignableFrom(repositoryInterface);
	}
}
