package eric.clapton.musician.service.publish;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.publish.PublishGrap;

public interface PublishGrapService extends BaseService<PublishGrap> {
	PublishGrap getOrderGrap(Long orderId,Long timeId,Long userId);
	
	List<PublishGrap> getOrderGrap(Long orderId);
}
