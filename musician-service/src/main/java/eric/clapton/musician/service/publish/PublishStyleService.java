package eric.clapton.musician.service.publish;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.publish.PublishStyle;

public interface PublishStyleService extends BaseService<PublishStyle> {
	List<PublishStyle> getOrderStyles(Long orderId);
}
