package eric.clapton.musician.service.publish.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.core.entity.po.publish.FieldOrder;
import eric.clapton.musician.repository.publish.FieldOrderRepository;
import eric.clapton.musician.service.publish.FieldOrderService;

@Service
public class FieldOrderServiceImpl extends BaseServiceImpl<FieldOrder, FieldOrderRepository> implements FieldOrderService {

	@Override
	@Resource
	protected void baseSetRepository(FieldOrderRepository repository) {
		this.setRepository(repository);
	}


	private String state2String(OrderState... state) {
		StringBuilder sb = new StringBuilder();
		for (OrderState s : state) {
			sb.append("'").append(s.toString()).append("',");

		}
		sb = sb.deleteCharAt(0);
		sb = sb.deleteCharAt(sb.length()-1).deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	@Override
	public List<Object> getShoperStateOrders(Long userId, int startPos, int size, OrderState... state) {

		return repository.getShoperOrders(state2String(state), userId, (startPos - 1) * size, size);
	}

	@Override
	public List<Object> getShoperAllOrders(Long userId, int startPos, int size) {

		return repository.getShoperAllOrders(userId, (startPos - 1) * size, size);
	}

	@Override
	public List<Object> getMusicumStateOrders(Long userId, int startPos, int size, OrderState... state) {

		return repository.getMusicumOrders(state2String(state), userId, (startPos - 1) * size, size);
	}

	@Override
	public List<Object> getMusicumAllOrders(Long userId, int startPos, int size) {

		return repository.getMusicumAllOrders(userId, (startPos - 1) * size, size);
	}

	@Override
	public int getShoperStateOrdersNo(Long userId, OrderState... state) {
		return repository.getShoperOrdersNo(state2String(state), userId);
	}

	@Override
	public int getShoperAllOrdersNo(Long userId) {

		return repository.getShoperAllOrdersNo(userId);
	}

	@Override
	public int getMusicumStateOrdersNo(Long userId, OrderState... state) {
		return repository.getMusicumOrdersNo(state2String(state), userId);
	}

	@Override
	public int getMusicumAllOrdersNo(Long userId) {

		return repository.getMusicumAllOrdersNo(userId);
	}

	@Override
	public List<Object> getShoperAgreeOrders(Long userId,Long orderId,Long timeId, int startPos, int size) {
		return repository.getShoperAgreeOrders(userId,orderId,timeId, (startPos-1)*size, size);
	}

	@Override
	public int getShoperAgreeOrdersNo(Long userId,Long orderId,Long timeId) {
		return repository.getShoperAgreeOrdersNo(userId,orderId,timeId);
	}

	@Override
	public List<Object> getMusicumAviabledOrders(Long accountId,int startPos, int size) {
		return repository.getMusicumAviabledOrders(accountId,(startPos-1)*size, size);
	}

	@Override
	public int getMusicumAviabledOrdersNo(Long accountId) {
		
		return repository.getMusicumAviabledOrdersNo(accountId);
	}

}
