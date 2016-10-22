package eric.clapton.musician.repository.publish;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.OrderComment;

public interface OrderCommentRepository extends BaseRepository<OrderComment, Long> {

	
	@Query(value = "select c.* from ms_publish_common c where c.orderid = ?1 ", nativeQuery = true)
	List<OrderComment> getCommonsByOrder(Long orderId);


}
