package eric.clapton.musician.service.performance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.dto.performance.PublishedPerformancesSearchParams;
import eric.clapton.musician.core.entity.po.performance.Performance;

public interface PerformanceService extends BaseService<Performance> {
	Page<Performance> findPublishedPerformances(PublishedPerformancesSearchParams searchParams, Pageable pageable);
}
