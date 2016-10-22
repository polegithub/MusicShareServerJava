package eric.clapton.musician.service.order;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.dto.order.OrderInfo;
import eric.clapton.musician.core.entity.po.order.Order;

public interface OrderService extends BaseService<Order> {
	Order create(Long accountId, OrderInfo orderInfo);

	Order update(Long accountId, OrderInfo orderInfo);

	Order delete(Long accountId, Long id);

	Order getOrderOf(Long orderId, Long accountId);
}
