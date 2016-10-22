package eric.clapton.musician.service.address;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.address.Division;

public interface DivisionService extends BaseService<Division> {
	Division findByCode(String code);
}
