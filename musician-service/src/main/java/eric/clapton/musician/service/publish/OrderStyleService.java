package eric.clapton.musician.service.publish;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.publish.OrderStyle;

public interface OrderStyleService extends BaseService<OrderStyle> {
	List<OrderStyle> getOrderStyles(Long orderId);
}
