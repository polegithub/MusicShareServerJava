package eric.clapton.musician.repository.publish;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.PublishGrap;

public interface PublishGrapRepository extends
        BaseRepository<PublishGrap, Long> {
    @Query(value = "select c from PublishGrap c where c.orderid = ?1 and c.timeid = ?2 and c.userid = ?3 ")
    PublishGrap getOrderGrap(Long orderId, Long TimeId, Long userId);
    
    
    @Query(value = "select c from PublishGrap c where c.orderid = ?1 ")
    List<PublishGrap> getOrderGrap(Long orderId);
}
