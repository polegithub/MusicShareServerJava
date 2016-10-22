package eric.clapton.musician.service.performance.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.Assure;
import eric.clapton.musician.core.entity.dto.performance.PublishedPerformancesSearchParams;
import eric.clapton.musician.core.entity.po.account.AccountType;
import eric.clapton.musician.core.entity.po.performance.Performance;
import eric.clapton.musician.core.entity.po.performance.PublishedPerformance;
import eric.clapton.musician.repository.performance.PerformanceRepository;
import eric.clapton.musician.service.performance.PerformanceService;

@Service
public class PerformanceServiceImpl extends BaseServiceImpl<Performance, PerformanceRepository>
		implements PerformanceService {

	@Override
	public Page<Performance> findPublishedPerformances(PublishedPerformancesSearchParams searchParams,
			Pageable pageable) {
		Assure.isNotNull(searchParams, "searchParams");

		final Long accountId = searchParams.getAccountId();
		final AccountType accountType = searchParams.getAccountType();

		return getRepository().findAll(new Specification<Performance>() {
			@Override
			public Predicate toPredicate(Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				// select * from Performance p where exists (select
				if (accountId != null || accountType != null) {
					List<Predicate> publishedPerformanceSubqueryPredicates = new ArrayList<Predicate>();

					Subquery<PublishedPerformance> publishedPerformanceSubquery = query
							.subquery(PublishedPerformance.class);
					Root<PublishedPerformance> publishedPerformanceRoot = publishedPerformanceSubquery
							.from(PublishedPerformance.class);
					publishedPerformanceSubquery.select(publishedPerformanceRoot);

					publishedPerformanceSubqueryPredicates
							.add(cb.equal(publishedPerformanceRoot.get("account").get("id"), accountId));

					if (accountType != null) {
						publishedPerformanceSubqueryPredicates
								.add(cb.equal(publishedPerformanceRoot.get("accountType"), accountType));
					}

					publishedPerformanceSubqueryPredicates
							.add(cb.equal(publishedPerformanceRoot.get("performance").get("id"), root.get("id")));

					publishedPerformanceSubquery.where(publishedPerformanceSubqueryPredicates
							.toArray(new Predicate[publishedPerformanceSubqueryPredicates.size()]));

					predicates.add(cb.exists(publishedPerformanceSubquery));
				}

				query.where(predicates.toArray(new Predicate[predicates.size()]));

				// apply default sort.
				Sort sort = pageable == null ? null : pageable.getSort();
				if (sort == null || !sort.iterator().hasNext()) {
					query.orderBy(cb.desc(root.get("created")));
				}

				return null;
			}
		}, pageable, searchParams.isPageCountDisabled());
	}

	@Override
	@Resource
	protected void baseSetRepository(PerformanceRepository repository) {
		setRepository(repository);
	}
}
