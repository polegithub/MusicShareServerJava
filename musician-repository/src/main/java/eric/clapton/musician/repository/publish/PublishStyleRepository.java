package eric.clapton.musician.repository.publish;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.PublishStyle;

public interface PublishStyleRepository extends BaseRepository<PublishStyle, Long> {
	@Query(value = "select c from PublishStyle c where c.orderid = ?1")
	List<PublishStyle> getOrderStyles(Long orderId);

}
