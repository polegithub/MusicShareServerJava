package eric.clapton.musician.service.publish;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.core.entity.po.publish.PublishOrder;
import eric.clapton.musician.core.entity.po.publish.PublishTime;

public interface PublishOrderService extends BaseService<PublishOrder> {
	List<PublishOrder> getOuttimeOrders();
	
	List<PublishTime> getWaitOrders();
	
	List<PublishTime> getCompleteOrders();
	
	
	List<Object> getShoperStateOrders(Long userId,int startPos,int size,OrderState... state);
	int getShoperStateOrdersNo(Long userId,OrderState... state);
	
	List<Object> getShoperAllOrders(Long userId,int startPos,int size);
	int getShoperAllOrdersNo(Long userId);
	
	List<Object> getMusicumStateOrders(Long userId,int startPos,int size,OrderState... state);
	int getMusicumStateOrdersNo(Long userId,OrderState... state);
	
	List<Object> getMusicumAllOrders(Long userId,int startPos,int size);
	int getMusicumAllOrdersNo(Long userId);
	
	
	List<Object> getShoperAgreeOrders(Long userId,Long orderId,Long timeId ,int startPos,int size);
	int getShoperAgreeOrdersNo(Long userId,Long orderId,Long timeId);
	
	
	List<Object> getMusicumAviabledOrders(Long accountId,int startPos,int size);
	int getMusicumAviabledOrdersNo(Long accountId);
	
	
	List<Object> getOrdersNyMusicName(Long accountId,Long orderId,Long timeId,String name ,int startPos,int size);
	int getOrdersNyMusicNameNo(Long accountId,Long orderId,Long timeId,String name);
	
}
