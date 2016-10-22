package eric.clapton.musician.service.performance;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.performance.PerformanceTag;

public interface PerformanceTagService extends BaseService<PerformanceTag> {
	List<PerformanceTag> findByPerformanceId(Long performanceId);
	String findPerformanceNameByid(Long tagId);
	
	List<String> findOrderPerformance(Long orderId);
	
}
