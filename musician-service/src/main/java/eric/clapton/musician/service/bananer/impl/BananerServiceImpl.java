package eric.clapton.musician.service.bananer.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.bananer.Bananer;
import eric.clapton.musician.repository.bananer.BananerRepository;
import eric.clapton.musician.service.bananer.BananerService;

@Service
public class BananerServiceImpl extends
		BaseServiceImpl<Bananer, BananerRepository> implements BananerService {

	@Override
	@Resource
	protected void baseSetRepository(BananerRepository repository) {
		this.setRepository(repository);
	}
	

	
}
