package eric.clapton.musician.service.publish.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.publish.OrderComment;
import eric.clapton.musician.repository.publish.OrderCommentRepository;
import eric.clapton.musician.service.publish.OrderCommentService;

@Service
public class OrderCommentServiceImpl extends
		BaseServiceImpl<OrderComment, OrderCommentRepository> implements
		OrderCommentService {

	@Override
	@Resource
	protected void baseSetRepository(OrderCommentRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public List<OrderComment> getCommonsByOrder(Long orderId) {
		return repository.getCommonsByOrder(orderId);
	}

}
