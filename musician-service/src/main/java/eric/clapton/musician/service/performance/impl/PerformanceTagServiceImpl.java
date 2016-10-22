package eric.clapton.musician.service.performance.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.performance.PerformanceTag;
import eric.clapton.musician.repository.performance.PerformanceTagRepository;
import eric.clapton.musician.service.performance.PerformanceTagService;

@Service
public class PerformanceTagServiceImpl extends BaseServiceImpl<PerformanceTag, PerformanceTagRepository> implements PerformanceTagService {
	@Override
	@Resource
	protected void baseSetRepository(PerformanceTagRepository repository) {
		setRepository(repository);
	}

	@Override
	public List<PerformanceTag> findByPerformanceId(Long performanceId) {
		return getRepository().findByPerformanceIdOrderByDisplayOrder(performanceId);
	}

	@Override
	public String findPerformanceNameByid(Long tagId) {

		return getRepository().findPerformanceNameByid(tagId);
	}

	@Override
	public List<String> findOrderPerformance(Long orderId) {
		
		return getRepository().findOrderPerformance(orderId);
	}
}
