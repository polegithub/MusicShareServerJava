package eric.clapton.musician.service.publish.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.publish.PublishStyle;
import eric.clapton.musician.repository.publish.PublishStyleRepository;
import eric.clapton.musician.service.publish.PublishStyleService;

@Service
public class PublishStyleServiceImpl extends BaseServiceImpl<PublishStyle, PublishStyleRepository> implements PublishStyleService {

	@Override
	@Resource
	protected void baseSetRepository(PublishStyleRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public List<PublishStyle> getOrderStyles(Long orderId) {
		List<PublishStyle> orderStyles = repository.getOrderStyles(orderId);
		return orderStyles;
	}

	
	

}
