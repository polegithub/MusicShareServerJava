package eric.clapton.musician.web.controller.my;

import static eric.clapton.musician.util.FormatUtils.formatAsDateTimeString;

import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.BasePageResponse;
import eric.clapton.musician.core.entity.dto.LongIdRequest;
import eric.clapton.musician.core.entity.dto.order.OrderInfo;
import eric.clapton.musician.core.entity.dto.order.SellerOrderDetail;
import eric.clapton.musician.core.entity.dto.order.SellerOrderListItem;
import eric.clapton.musician.core.entity.dto.order.PublishedOrderListRequest;
import eric.clapton.musician.core.entity.po.order.Order;
import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.service.order.OrderErrors;
import eric.clapton.musician.service.order.OrderService;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/my/order")
@ResponseBody
public class MyOrderController extends ApiControllerSupport {
	@Resource
	private OrderService orderService;

	@RequestMapping("/create")
	public ApiResponse create(@RequestBody() OrderInfo orderInfo) {
		orderService.create(CurrentUser.getAccountId(), orderInfo);

		return ApiResponse.succeed();
	}

	@RequestMapping("/update")
	public ApiResponse update(@RequestBody() OrderInfo orderInfo) {
		orderService.update(CurrentUser.getAccountId(), orderInfo);

		return ApiResponse.succeed();
	}

	@RequestMapping("/delete")
	public ApiResponse delete(@RequestBody() LongIdRequest id) {
		orderService.delete(CurrentUser.getAccountId(), id.getId());

		return ApiResponse.succeed();
	}

	@RequestMapping("/detail")
	public ApiResponse detail(@RequestBody() LongIdRequest id) {
		Order order = orderService.getOrderOf(id.getId(), CurrentUser.getAccountId());
		if (order == null) {
			return ApiResponse.fail(OrderErrors.NO_ORDER_EXISTS + "", "订单飞到外太空啦，乐享君找不回来了Orz");
		}

		SellerOrderDetail d = toOrderDetail(order);

		return ApiResponse.succeed(d);
	}

	private SellerOrderDetail toOrderDetail(Order order) {
		if (order == null) {
			return null;
		}

		SellerOrderDetail i = new SellerOrderDetail();

		i.setCreated(formatAsDateTimeString(order.getCreated()));
		i.setDeadline(formatAsDateTimeString(order.getDeadline()));
		i.setDescription(order.getDescription());
		i.setId(order.getId());
		i.setMusicType(order.getGenres().stream().map(e -> e.getGenre()).collect(Collectors.toList()));
		i.setPeopleCount(order.getRecruits());
		i.setPrice(order.getPrice());
		i.setTimeEnd(formatAsDateTimeString(order.getDateFrom()));
		i.setTimeStart(formatAsDateTimeString(order.getDateTo()));
		i.setTitle(order.getTitle());
		i.setSignedUp(order.getSignedUp());
		i.setRecruited(order.getRecruited());

		OrderState state = order.getState();
		i.setState(state.ordinal());
		i.setStateText(state.getDescription());

		return i;
	}
	
	//查看自己order
	@RequestMapping("/list")
	public ApiResponse list(@RequestBody() PublishedOrderListRequest r) {
		Searchable searchable = Searchable.newSearchable();
		Long accountId = CurrentUser.getAccountId();
		searchable.equal("account.id", accountId);
		searchable.orderByDescending("created");
		searchable.setPage(r.getPageable());

		Page<Order> page = orderService.findAll(searchable);

		BasePageResponse pageResponse = getPageResponse(page, e -> toOrderListItem(e));

		return ApiResponse.succeed(pageResponse);
	}

	private SellerOrderListItem toOrderListItem(Order order) {
		if (order == null) {
			return null;
		}

		SellerOrderListItem i = new SellerOrderListItem();

		i.setCreated(formatAsDateTimeString(order.getCreated()));
		i.setDeadline(formatAsDateTimeString(order.getDeadline()));
		i.setDescription(order.getDescription());
		i.setId(order.getId());
		i.setMusicType(order.getGenres().stream().map(e -> e.getGenre()).collect(Collectors.toList()));
		i.setPeopleCount(order.getRecruits());
		i.setPrice(order.getPrice());
		i.setTimeEnd(formatAsDateTimeString(order.getDateFrom()));
		i.setTimeStart(formatAsDateTimeString(order.getDateTo()));
		i.setTitle(order.getTitle());
		i.setSignedUp(order.getSignedUp());
		i.setRecruited(order.getRecruited());

		OrderState state = order.getState();
		i.setState(state.ordinal());
		i.setStateText(state.getDescription());

		return i;
	}
}
