package eric.clapton.musician.service.publish.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.publish.PublishGrap;
import eric.clapton.musician.repository.publish.PublishGrapRepository;
import eric.clapton.musician.service.publish.PublishGrapService;

@Service
public class PublishGrapServiceImpl extends BaseServiceImpl<PublishGrap, PublishGrapRepository> implements PublishGrapService {

	@Override
	@Resource
	protected void baseSetRepository(PublishGrapRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public PublishGrap getOrderGrap(Long orderId, Long TimeId, Long userId) {
		PublishGrap orderGrap = repository.getOrderGrap(orderId, TimeId, userId);
		return orderGrap;
	}

	@Override
	public List<PublishGrap> getOrderGrap(Long orderId) {
		return repository.getOrderGrap(orderId);
	}

}
