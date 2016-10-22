package eric.clapton.musician.web.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.core.entity.po.publish.PublishOrder;
import eric.clapton.musician.core.entity.po.publish.PublishTime;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.publish.PublishGrapService;
import eric.clapton.musician.service.publish.PublishOrderService;
import eric.clapton.musician.service.publish.PublishTimeService;

/**
 * 
 * 驻场订单管理
 *
 */
@Component
public class PublishOrderTask {

	@Resource
	private PublishOrderService publishOrderService;

	@Resource
	private PublishTimeService publishTimeService;

	@Resource
	private PublishGrapService orderGrapService;

	@Resource
	private AccountService accountService;

	/*
	 * 过期未付款订单取消
	 */
	@Scheduled(cron = "30 *  * * * ? ")
	// 间隔30秒钟执行
	public void cancleOrder() {
		List<PublishOrder> orders = publishOrderService.getOuttimeOrders();
		for (PublishOrder order : orders) {
			order.setState(OrderState.CANCELLED);
			publishOrderService.update(order);
		}
	}

	/*
	 * 过期未未抢购订单退款
	 */
	@Scheduled(cron = "* * * 0/1  * ? ")
	// 间隔1天执行
	public void bacOrderkMoney() {
		List<PublishTime> orders = publishOrderService.getWaitOrders();
		for (PublishTime order : orders) {

			// 此处退款给商家

			order.setState(OrderState.CLOSED);
			publishTimeService.update(order);
		}
	}

	/*
	 * 过期打款订单打款款
	 */
	@Scheduled(cron = "* * * 0/1  * ? ")
	// 间隔1天执行
	public void OrderkMoney() {
		List<PublishTime> orders = publishOrderService.getCompleteOrders();
		for (PublishTime order : orders) {
			// 此处打款给音乐人

			order.setState(OrderState.CLOSED);
			publishTimeService.update(order);
		}
	}

}
