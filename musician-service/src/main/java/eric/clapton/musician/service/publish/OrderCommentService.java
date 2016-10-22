package eric.clapton.musician.service.publish;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.publish.OrderComment;

public interface OrderCommentService extends BaseService<OrderComment> {
	List<OrderComment> getCommonsByOrder(Long orderId);
	
}
