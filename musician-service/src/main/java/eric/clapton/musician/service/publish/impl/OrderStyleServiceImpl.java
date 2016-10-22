package eric.clapton.musician.service.publish.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.publish.OrderStyle;
import eric.clapton.musician.repository.publish.OrderStyleRepository;
import eric.clapton.musician.service.publish.OrderStyleService;

@Service
public class OrderStyleServiceImpl extends BaseServiceImpl<OrderStyle, OrderStyleRepository> implements OrderStyleService {

	@Override
	@Resource
	protected void baseSetRepository(OrderStyleRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public List<OrderStyle> getOrderStyles(Long orderId) {
		List<OrderStyle> orderTimes = repository.getOrderStyles(orderId);
		return orderTimes;
	}

	
	

}
