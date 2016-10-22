package eric.clapton.musician.service.publish.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.publish.PublishTime;
import eric.clapton.musician.repository.publish.PublishTimeRepository;
import eric.clapton.musician.service.publish.PublishTimeService;

@Service
public class PublishTimeServiceImpl extends BaseServiceImpl<PublishTime, PublishTimeRepository> implements PublishTimeService {

	@Override
	@Resource
	protected void baseSetRepository(PublishTimeRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public List<PublishTime> getOrderTimes(Long orderId) {
		List<PublishTime> orderTimes = repository.getOrderTimes(orderId);
		return orderTimes;
	}

	@Override
	public PublishTime getOrderByTime(Long orderId, Date startTime, Date endTime) {
		PublishTime time = repository.getOrderTimes(orderId, startTime, endTime);
		return time;
	}

	@Override
	public Object getOrderDetail(Long orderId, Long timeId) {
		
		return repository.getOrderDetail(orderId,timeId);
	}

	@Override
	public PublishTime getTimeOrder(Long orderId, Long timeId) {
		
		return repository.getTimeOrder(orderId, timeId);
	}

	

}
