package eric.clapton.musician.service.account;

import java.util.List;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.order.OrderGenre;

public interface HomeSearchService extends BaseService<OrderGenre>  {
	List getTypeList();
	List showList();
	List serach(String keyword);
	
}
