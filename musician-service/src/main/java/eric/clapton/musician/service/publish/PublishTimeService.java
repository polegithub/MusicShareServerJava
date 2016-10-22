package eric.clapton.musician.service.publish;

import java.util.Date;
import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.publish.PublishTime;

public interface PublishTimeService extends BaseService<PublishTime> {
	List<PublishTime> getOrderTimes(Long orderId);
	PublishTime getOrderByTime(Long orderId,Date startTime,Date endTime);
	PublishTime getTimeOrder(Long orderId,Long timeId);
	Object getOrderDetail(Long orderId,Long timeId);
}
