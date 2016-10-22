package eric.clapton.musician.repository.publish;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.OrderStyle;

public interface OrderStyleRepository extends BaseRepository<OrderStyle, Long> {
	@Query(value = "select c from PublishTime c where c.orderid = ?1")
	List<OrderStyle> getOrderStyles(Long orderId);

}
