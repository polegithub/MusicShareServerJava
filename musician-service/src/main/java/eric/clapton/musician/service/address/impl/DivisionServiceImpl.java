package eric.clapton.musician.service.address.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.address.Division;
import eric.clapton.musician.repository.address.DivisionRepository;
import eric.clapton.musician.service.address.DivisionService;

@Service
public class DivisionServiceImpl extends BaseServiceImpl<Division, DivisionRepository> implements DivisionService {

	@Override
	@Resource
	protected void baseSetRepository(DivisionRepository repository) {
		super.setRepository(repository);
	}

	@Override
	public Division findByCode(String code) {
		return getRepository().findByCode(code);
	}
}
