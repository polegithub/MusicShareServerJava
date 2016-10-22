package eric.clapton.musician.web.controller.my;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.util.DateUtils;
import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.LongIdRequest;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountState;
import eric.clapton.musician.core.entity.po.account.AccountType;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;
import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.core.entity.po.publish.OrderComment;
import eric.clapton.musician.core.entity.po.publish.PublishGrap;
import eric.clapton.musician.core.entity.po.publish.PublishGrap.GrabState;
import eric.clapton.musician.core.entity.po.publish.PublishGrap.OrderType;
import eric.clapton.musician.core.entity.po.publish.PublishOrder;
import eric.clapton.musician.core.entity.po.publish.PublishStyle;
import eric.clapton.musician.core.entity.po.publish.PublishTime;
import eric.clapton.musician.core.util.Constants;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.account.AddressBookService;
import eric.clapton.musician.service.order.OrderErrors;
import eric.clapton.musician.service.performance.PerformanceTagService;
import eric.clapton.musician.service.publish.OrderCommentService;
import eric.clapton.musician.service.publish.PublishGrapService;
import eric.clapton.musician.service.publish.PublishOrderService;
import eric.clapton.musician.service.publish.PublishStyleService;
import eric.clapton.musician.service.publish.PublishTimeService;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/mOrder")
@ResponseBody
public class SearchOrderController extends ApiControllerSupport {
	@Resource
	private PublishOrderService publishOrderService;

	@Resource
	private PublishTimeService publishTimeService;

	@Resource
	private PublishGrapService orderGrapService;

	@Resource
	private AccountService accountService;

	@Resource
	private AddressBookService addressService;

	@Resource
	private OrderCommentService orderCommentService;

	@Resource
	private PublishStyleService publishStyleService;

	@Resource
	private PerformanceTagService orderStyleService;

	private int pageSize = Constants.PAGE_SIZE;

	private String[] imgList(Long orderid) {
		String src[] = { "http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg",
				"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg",
				"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg" };

		return src;

	}

	// 音乐人查看可接收order
	@RequestMapping("/music/aviabled")
	public ApiResponse aviabled(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("pageIndex"), request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[pageIndex,accountid]参数缺失");
		}

		int page = Integer.parseInt(request.get("pageIndex"));
		Long accountId = Long.parseLong(request.get("accountid"));
		int total = 0;
		List<Object> result = null;

		result = publishOrderService.getMusicumAviabledOrders(accountId, page, pageSize);
		total = publishOrderService.getMusicumAviabledOrdersNo(accountId);

		List<Map<String, Object>> list = StringUtils.list2ArrayMap(result, Constants.PUBLISH_ORDER_COLUMN);
		for (Map<String, Object> r : list) {
			String stat = (String) r.get("state");
			r.put("stateString", OrderState.getStateMessage(stat));
			r.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
			Long orderid = Long.parseLong((Integer) r.get("orderid") + "");
			List<String> style = orderStyleService.findOrderPerformance(orderid);
			r.put("style", style);

			String tel = (String) r.get("tel_nrs");
			if (tel != null) {
				String[] tels = tel.split(",");
				r.put("r", tels);
			}
			r.put("images", imgList(orderid));
		}
		// PageImpl<Object> content = new PageImpl<Object>(result, new
		// PageRequest(page, pageSize), 0);
		// if("0".equals(state)){//全部
		//
		// }else if("1".equals(state)){
		// searchable.in("state", OrderState.ING); //已报名
		// }else if("2".equals(state)){
		// searchable.in("state", OrderState.ENSURE); //待演出
		// }else if("3".equals(state)){
		// searchable.in("state", OrderState.COMPLETE);//待评价
		// }else if("4".equals(state)){
		// searchable.in("state",
		// OrderState.CANCELLED,OrderState.CLOSED,OrderState.ING);//已结束
		// }
		// searchable.orderByDescending("created");
		// searchable.setPage(new PageRequest((Integer)
		// request.get("pageIndex"), (Integer) (request.get("pageSize"))));
		// Page<PublishOrder> page = publishOrderService.findAll(searchable);
		// // ArrayList lists = Lists.newArrayList(page.getContent());
		// BasePageResponse pageResponse = getPageResponse(content);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("content", list);
		return ApiResponse.succeed(map);
	}

	// 音乐人查看可接收order
	@RequestMapping("/music/list")
	public ApiResponse list(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("state"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,state]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();

		Long accountId = Long.parseLong(request.get("accountid"));
		int page = Integer.parseInt(request.get("pageIndex"));
		// int pageSize = (Integer) (request.get("pageSize"));
		String state = request.get("state");
		int total = 0;
		List<Object> result = null;
		if ("0".equals(state)) {// 全部
			result = publishOrderService.getMusicumAllOrders(accountId, page, pageSize);
			total = publishOrderService.getMusicumAllOrdersNo(accountId);
		} else if ("1".equals(state)) { // 已报名
			result = publishOrderService.getMusicumStateOrders(accountId, page, pageSize, OrderState.ING);
			total = publishOrderService.getMusicumStateOrdersNo(accountId, OrderState.ING);
		} else if ("2".equals(state)) { // 待演出
			result = publishOrderService.getMusicumStateOrders(accountId, page, pageSize, OrderState.ENSURE);
			total = publishOrderService.getMusicumStateOrdersNo(accountId, OrderState.ENSURE);
		} else if ("3".equals(state)) {// 待评价
			result = publishOrderService.getMusicumStateOrders(accountId, page, pageSize, OrderState.COMPLETE);
			total = publishOrderService.getMusicumStateOrdersNo(accountId, OrderState.COMPLETE);
		} else if ("4".equals(state)) {// 已结束
			result = publishOrderService.getMusicumStateOrders(accountId, page, pageSize, OrderState.CANCELLED, OrderState.CLOSED, OrderState.UNING);
			total = publishOrderService.getMusicumStateOrdersNo(accountId, OrderState.CANCELLED, OrderState.CLOSED, OrderState.UNING);
		}

		List<Map<String, Object>> list = StringUtils.list2ArrayMap(result, Constants.PUBLISH_ORDER_COLUMN);
		for (Map<String, Object> r : list) {
			String stat = (String) r.get("state");
			r.put("stateString", OrderState.getStateMessage(stat));
			r.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
			Long orderid = Long.parseLong((Integer) r.get("orderid") + "");
			List<String> style = orderStyleService.findOrderPerformance(orderid);
			r.put("style", style);

			String tel = (String) r.get("tel_nrs");
			if (tel != null) {
				String[] tels = tel.split(",");
				r.put("r", tels);
			}
			r.put("images", imgList(orderid));
		}
		// PageImpl<Object> content = new PageImpl<Object>(result, new
		// PageRequest(page, pageSize), 0);
		// if("0".equals(state)){//全部
		//
		// }else if("1".equals(state)){
		// searchable.in("state", OrderState.ING); //已报名
		// }else if("2".equals(state)){
		// searchable.in("state", OrderState.ENSURE); //待演出
		// }else if("3".equals(state)){
		// searchable.in("state", OrderState.COMPLETE);//待评价
		// }else if("4".equals(state)){
		// searchable.in("state",
		// OrderState.CANCELLED,OrderState.CLOSED,OrderState.ING);//已结束
		// }
		// searchable.orderByDescending("created");
		// searchable.setPage(new PageRequest((Integer)
		// request.get("pageIndex"), (Integer) (request.get("pageSize"))));
		// Page<PublishOrder> page = publishOrderService.findAll(searchable);
		// // ArrayList lists = Lists.newArrayList(page.getContent());
		// BasePageResponse pageResponse = getPageResponse(content);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("content", list);
		return ApiResponse.succeed(map);
	}

	// 商户查看可接收order
	@RequestMapping("/merchant/list")
	public ApiResponse mList(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("state"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,state]参数缺失");
		}

		Long accountId = Long.parseLong(request.get("accountid"));
		int page = Integer.parseInt(request.get("pageIndex"));
		// pageSize = (Integer) (request.get("pageSize"));

		String state = (String) request.get("state");
		int total = 0;
		List<Object> result = null;
		if ("0".equals(state)) {// 全部
			result = publishOrderService.getShoperAllOrders(accountId, page, pageSize);
			total = publishOrderService.getShoperAllOrdersNo(accountId);
		} else if ("1".equals(state)) { // 待发布
			result = publishOrderService.getShoperStateOrders(accountId, page, pageSize, OrderState.CREATED);
			total = publishOrderService.getShoperStateOrdersNo(accountId, OrderState.CREATED);
		} else if ("2".equals(state)) { // 进行中
			result = publishOrderService.getShoperStateOrders(accountId, page, pageSize, OrderState.ING);
			total = publishOrderService.getShoperStateOrdersNo(accountId, OrderState.ING);
		} else if ("3".equals(state)) {// 待评价
			result = publishOrderService.getShoperStateOrders(accountId, page, pageSize, OrderState.COMPLETE);
			total = publishOrderService.getShoperStateOrdersNo(accountId, OrderState.COMPLETE);
		} else if ("4".equals(state)) {// 已结束
			result = publishOrderService.getShoperStateOrders(accountId, page, pageSize, OrderState.CANCELLED, OrderState.CLOSED);
			total = publishOrderService.getShoperStateOrdersNo(accountId, OrderState.CANCELLED, OrderState.CLOSED);
		}
		List<Map<String, Object>> list = StringUtils.list2ArrayMap(result, Constants.PUBLISH_ORDER_COLUMN);
		for (Map<String, Object> r : list) {
			String stat = (String) r.get("state");
			r.put("stateString", OrderState.getStateMessage(stat));
			r.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
			Long orderid = Long.parseLong((Integer) r.get("orderid") + "");
			List<String> style = orderStyleService.findOrderPerformance(orderid);
			r.put("style", style);

			String tel = (String) r.get("tel_nrs");
			if (tel != null) {
				String[] tels = tel.split(",");
				r.put("r", tels);
			}
			r.put("images", imgList(orderid));
		}
		// PageImpl<Object> content = new PageImpl<Object>(result, new
		// PageRequest(page, pageSize), 0);

		// Searchable searchable = Searchable.newSearchable();
		// Long accountId = Long.parseLong((Integer) request.get("accountid") +
		// "");
		// searchable.equal("account.id", accountId);
		// searchable.in("state", OrderState.CREATED, OrderState.PAYING,
		// OrderState.CLOSED);// 以创建订单、已支付订单、已抢订单、已过时订单
		//
		// searchable.orderByDescending("created");
		// searchable.setPage(new PageRequest((Integer)
		// request.get("pageIndex"), (Integer) (request.get("pageSize"))));
		//
		// Page<PublishOrder> page = publishOrderService.findAll(searchable);
		//
		// BasePageResponse pageResponse = getPageResponse(page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("content", list);
		return ApiResponse.succeed(map);
	}

	// 音乐人抢订单
	@RequestMapping("/music/grab")
	public ApiResponse grap(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();

		Long accountId = Long.parseLong(request.get("accountid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));
		String remark = (String) request.get("remark");
		Account acc = accountService.findOne(accountId);
		AccountType type = acc.getType();

		if (!type.equals(AccountType.MUSICIAN) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "您不能抢单");
		}

		PublishOrder order = publishOrderService.findOne(orderId);
		PublishTime time = publishTimeService.findOne(timeId);

		if (order == null || !(time.getState().equals(OrderState.PAYING) || time.getState().equals(OrderState.ING))) {
			return ApiResponse.fail("-100010", "可接订单不存在");
		}

		PublishGrap grab = orderGrapService.getOrderGrap(orderId, timeId, accountId);
		if (grab != null) {
			return ApiResponse.fail("-100011", "您已经抢过此单了");
		}
		time.setRecruited(time.getRecruited() + 1);
		time.setState(OrderState.ING);
		publishTimeService.update(time);

		// 记录音乐人抢订单记录
		PublishGrap grap = new PublishGrap();
		grap.setOrderid(orderId);
		grap.setTimeid(timeId);
		grap.setUserid(accountId);
		grap.setRemark(remark);
		grap.setType(OrderType.TIME);
		grap.setTime(new Date());
		grap.setState(GrabState.WAIT);
		orderGrapService.save(grap);

		order.setState(OrderState.ING);// 设定订单状态为抢购中
		publishOrderService.update(order);

		return ApiResponse.succeed();
	}

	// 音乐人抢订单撤销
	@RequestMapping("/grab/delete")
	public ApiResponse grapDelete(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"), request.get("userid"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid]参数缺失");
		}

		Long userId = Long.parseLong(request.get("userid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));
		Account acc = accountService.findOne(userId);
		AccountType type = acc.getType();

		if (!type.equals(AccountType.MUSICIAN) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "您不能撤单");
		}

		PublishGrap grap = orderGrapService.getOrderGrap(orderId, timeId, userId);

		if (grap == null) {
			return ApiResponse.fail("-100010", "订单不存在");
		}
		orderGrapService.delete(grap);
		return ApiResponse.succeed();
	}

	// 音乐人确认抢订单成功--无此步骤
	// @RequestMapping("/music/confirm")
	// public ApiResponse confirm(@RequestBody() Map<String, Object> request) {
	// // Long accountId = CurrentUser.getAccountId();
	// // AccountType type = CurrentUser.getAccount().getType();
	//
	// Long accountId = Long
	// .parseLong((Integer) request.get("accountid") + "");
	// Long orderId = Long.parseLong((Integer) request.get("orderid") + "");
	// Long timeId = Long.parseLong((Integer) request.get("timeid") + "");
	// String remark = (String) request.get("remark");
	// Account acc = accountService.findOne(accountId);
	// AccountType type = acc.getType();
	//
	// if (!type.equals(AccountType.MUSICIAN)) {
	// return ApiResponse.fail("-100010", "您不能抢单");
	// }
	//
	// PublishOrder order = publishOrderService.findOne(orderId);
	// PublishTime time = publishTimeService.findOne(timeId);
	//
	// if (order == null || !time.getState().equals(OrderState.SURE)) {
	// return ApiResponse.fail("-100010", "可接订单不存在");
	// }
	// time.setRecruited(time.getRecruited() + 1);
	// time.setState(OrderState.ENSURE);// 音乐人已确认后，待演出状态
	// publishTimeService.update(time);
	//
	// return ApiResponse.succeed();
	// }

	// 音乐人评论商家
	@RequestMapping("/music/comment")
	public ApiResponse comment(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"), request.get("score"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid,score]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();

		Long accountId = Long.parseLong(request.get("accountid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));
		double score = Double.parseDouble(request.get("score"));
		String remark = (String) request.get("remark");

		Account acc = accountService.findOne(accountId);
		AccountType type = acc.getType();
		if (!type.equals(AccountType.MUSICIAN) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "音乐人未审核");
		}

		PublishOrder order = publishOrderService.findOne(orderId);
		PublishTime time = publishTimeService.findOne(timeId);

		if (order == null) {
			return ApiResponse.fail("-100010", "订单不存在");
		}
		if (time.getState().equals(OrderState.COMPLETE)) {
			time.setState(OrderState.EVALUATE);// 音乐人评论后，订单等待商家评论
			publishTimeService.update(time);
			List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
			if (times != null) {
				boolean isGrap = true;// 是否已经被抢
				for (int i = 0; i < times.size(); i++) {
					PublishTime t = times.get(i);
					if (OrderState.COMPLETE.equals(t.getState())) {// 还有未被评论的时间段
						isGrap = false;
						break;
					}
				}
				if (isGrap) {
					order.setState(OrderState.EVALUATE);// 设定订单状态为(评价中,等待商家评论)
					publishOrderService.update(order);
				}
			}
		} else if (time.getState().equals(OrderState.EVALUATE)) {
			time.setState(OrderState.CLOSED);// 商家评论过，音乐人评论后，订单关闭
			publishTimeService.update(time);
			List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
			if (times != null) {
				boolean isGrap = true;// 是否已经被抢
				for (int i = 0; i < times.size(); i++) {
					PublishTime t = times.get(i);
					if (OrderState.EVALUATE.equals(t.getState())) {// 还有未被评论的时间段
						isGrap = false;
						break;
					}
				}
				if (isGrap) {
					order.setState(OrderState.CLOSED);// 设定订单状态为(已关闭)
					publishOrderService.update(order);
				}
			}
		} else {
			return ApiResponse.fail("-100011", "订单不能评论");
		}

		OrderComment comment = new OrderComment();
		comment.setOrderid(orderId);
		comment.setPuserid(accountId);// 评论人
		comment.setPeeuser(order.getAccountId());// 被评论人
		comment.setRemark(remark);
		comment.setScore(score);
		comment.setTime(new Date());
		comment.setTimeid(timeId);
		orderCommentService.save(comment);

		return ApiResponse.succeed();
	}

	// 音乐人评论商家
	@RequestMapping("/merchant/comment")
	public ApiResponse mComment(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"), request.get("score"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid,score]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();

		Long accountId = Long.parseLong(request.get("accountid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));
		double score = Double.parseDouble(request.get("score"));
		String remark = (String) request.get("remark");

		Account acc = accountService.findOne(accountId);
		AccountType type = acc.getType();
		if (!type.equals(AccountType.SELLER) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "商家未审核");
		}

		PublishOrder order = publishOrderService.findOne(orderId);
		PublishTime time = publishTimeService.findOne(timeId);

		PublishGrap grap = orderGrapService.getOne(timeId);

		if (order == null) {
			return ApiResponse.fail("-100010", "订单不存在");
		}
		if (time.getState().equals(OrderState.COMPLETE)) {// 还未开始评论
			time.setState(OrderState.EVALUATE);// 商家没有评论过，音乐人评论后，订单评价中
			publishTimeService.update(time);

			List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
			if (times != null) {
				boolean isGrap = true;// 是否已经被抢
				for (int i = 0; i < times.size(); i++) {
					PublishTime t = times.get(i);
					if (OrderState.COMPLETE.equals(t.getState())) {// 还有未被评论的时间段
						isGrap = false;
						break;
					}
				}
				if (isGrap) {
					order.setState(OrderState.EVALUATE);// 设定订单状态为(评论中)
					publishOrderService.update(order);
				}
			}

		} else if (time.getState().equals(OrderState.EVALUATE)) {
			time.setState(OrderState.CLOSED);// 商家评论过，音乐人评论后，订单关闭
			publishTimeService.update(time);

			List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
			if (times != null) {
				boolean isGrap = true;// 是否已经被抢
				for (int i = 0; i < times.size(); i++) {
					PublishTime t = times.get(i);
					if (OrderState.EVALUATE.equals(t.getState())) {// 还有未被评论的时间段
						isGrap = false;
						break;
					}
				}
				if (isGrap) {
					order.setState(OrderState.CLOSED);// 设定订单状态为(已结束)
					publishOrderService.update(order);
				}
			}

		} else {
			return ApiResponse.fail("-100010", "订单不存在");
		}

		OrderComment comment = new OrderComment();
		comment.setOrderid(orderId);
		comment.setPuserid(accountId);// 评论人
		comment.setPeeuser(grap.getUserid());// 被评论人
		comment.setRemark(remark);
		comment.setScore(score);
		comment.setTime(new Date());
		comment.setTimeid(timeId);

		orderCommentService.save(comment);

		return ApiResponse.succeed();
	}

	// 商家付款订单
	@RequestMapping("/merchant/pay")
	public ApiResponse pay(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();

		Long accountId = Long.parseLong(request.get("accountid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Account acc = accountService.findOne(accountId);
		AccountType type = acc.getType();

		if (!type.equals(AccountType.SELLER) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "您不能支付订单");
		}

		PublishOrder order = publishOrderService.findOne(orderId);
		if (order == null || !order.getAccountId().equals(accountId) || !order.getState().equals(OrderState.CREATED)) {
			return ApiResponse.fail("-100010", "可支付订单不存在");
		}

		order.setState(OrderState.PAYING);// 设定订单状态为已支付
		publishOrderService.update(order);

		List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
		for (PublishTime t : times) {
			t.setState(OrderState.PAYING); // 设定小订单状态为已支付
			publishTimeService.update(t);
		}

		return ApiResponse.succeed();
	}

	// 商家查看音乐人抢单列表
	@RequestMapping("/merchant/grabList")
	public ApiResponse grabList(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("orderid"), request.get("timeid"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,orderid,timeid]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();
		Long accountId = Long.parseLong(request.get("accountid"));
		int page = Integer.parseInt(request.get("pageIndex"));

		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));

		Account acc = accountService.findOne(accountId);
		AccountType type = acc.getType();

		if (!type.equals(AccountType.SELLER) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "您不能查看");
		}

		List<Object> result = publishOrderService.getShoperAgreeOrders(accountId, orderId, timeId, page, pageSize);
		int total = publishOrderService.getShoperAgreeOrdersNo(accountId, orderId, timeId);
		if (result == null) {
			return ApiResponse.succeed();
		}

		List<Map<String, Object>> list = StringUtils.list2ArrayMap(result, Constants.PUBLISH_GRAP_ORDER_COLUMN);
		for (Map<String, Object> r : list) {
			String stat = (String) r.get("state");
			r.put("stateString", OrderState.getStateMessage(stat));
			r.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
			Long orderid = Long.parseLong((Integer) r.get("orderid") + "");
			List<String> style = orderStyleService.findOrderPerformance(orderid);
			r.put("style", style);

			Long userid = Long.parseLong((Integer) r.get("userid") + "");

			AccountInfo user = accountService.getAccountInfo(userid);
			r.put("user", user);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("content", list);
		return ApiResponse.succeed(map);

	}

	// 商家选择音乐人
	@RequestMapping("/merchant/agree")
	public ApiResponse agree(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("userid"), request.get("orderid"), request.get("timeid"))) {
			return ApiResponse.fail("-10003", "[accountid,userid,orderid,timeid]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();
		Long accountId = Long.parseLong(request.get("accountid"));
		Long userId = Long.parseLong(request.get("userid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));
		Account acc = accountService.findOne(userId);
		AccountType type = acc.getType();

		if (!type.equals(AccountType.MUSICIAN) || !acc.getState().equals(AccountState.NORMAL)) {
			return ApiResponse.fail("-100010", "您选择的音乐人未审核");
		}

		PublishOrder order = publishOrderService.findOne(orderId);
		PublishTime timeOrder = publishTimeService.findOne(timeId);
		if (order == null || !order.getAccountId().equals(accountId) || !timeOrder.getState().equals(OrderState.ING)) {
			return ApiResponse.fail("-100010", "可用订单不存在");
		}

		// 更新订单时间段订单为正常
		timeOrder.setState(OrderState.ENSURE);
		publishTimeService.update(timeOrder);

		// 更新音乐人抢订单成功
		PublishGrap grap = orderGrapService.getOrderGrap(orderId, timeId, userId);
		grap.setState(GrabState.SUCCESS);
		orderGrapService.update(grap);

		List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
		if (times != null) {
			boolean isGrap = true;// 是否已经被抢
			for (int i = 0; i < times.size(); i++) {
				PublishTime time = times.get(i);
				if (OrderState.ING.equals(time.getState())) {// 还有未被抢的时间段
					isGrap = false;
					break;
				}
			}
			if (isGrap) {
				order.setState(OrderState.ENSURE);// 设定订单状态为(待演出)
				publishOrderService.update(order);
			}
		}

		return ApiResponse.succeed();
	}

	// 商家确认已完成
	@RequestMapping("/merchant/sure")
	public ApiResponse sure(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid]参数缺失");
		}

		// Long accountId = CurrentUser.getAccountId();
		// AccountType type = CurrentUser.getAccount().getType();
		Long accountId = Long.parseLong(request.get("accountid"));
		Long orderId = Long.parseLong(request.get("orderid"));
		Long timeId = Long.parseLong(request.get("timeid"));

		PublishOrder order = publishOrderService.findOne(orderId);
		PublishTime timeOrder = publishTimeService.findOne(timeId);
		if (order == null || !order.getAccountId().equals(accountId) || !timeOrder.getState().equals(OrderState.ENSURE)) {
			return ApiResponse.fail("-100010", "可用订单不存在");
		}

		// 更新订单时间段订单为演出完成
		timeOrder.setState(OrderState.COMPLETE);
		publishTimeService.update(timeOrder);

		List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
		if (times != null) {
			boolean isGrap = true;// 是否已经被抢
			for (int i = 0; i < times.size(); i++) {
				PublishTime time = times.get(i);
				if (OrderState.ENSURE.equals(time.getState())) {// 还有未被确认的时间段
					isGrap = false;
					break;
				}
			}
			if (isGrap) {
				order.setState(OrderState.COMPLETE);// 设定大订单状态为演出完成
				publishOrderService.update(order);
			}
		}

		return ApiResponse.succeed();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/merchant/create")
	public ApiResponse create(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("title"), request.get("accountid"), request.get("addressid"), request.get("times"), request.get("styles"))) {
			return ApiResponse.fail("-10003", "[title,accountid,addressid,times,styles]参数缺失");
		}

		PublishOrder orderInfo = new PublishOrder();
		orderInfo.setTitle((String) request.get("title"));
		orderInfo.setDescription((String) request.get("description"));

		Long accountId = Long.parseLong((String) request.get("accountid"));
		Account account = accountService.findOne(accountId);
		orderInfo.setAccount(account);

		Long addressId = Long.parseLong((String) request.get("addressid"));
		AddressBookEntry address = addressService.findOne(addressId);
		orderInfo.setAddress(address);

		orderInfo.setState(OrderState.CREATED);
		orderInfo.setCreated(new Date());
		PublishOrder order = publishOrderService.save(orderInfo);

		List<Map<String, String>> t = (List<Map<String, String>>) request.get("times");
		List<Map<String, String>> s = (List<Map<String, String>>) request.get("styles");

		for (Map<String, String> map : t) {
			PublishTime time = new PublishTime();
			time.setPrice(Double.parseDouble(map.get("price")));
			time.setRemark((String) map.get("remark"));
			time.setNeed(Integer.parseInt(map.get("needcount")));
			time.setStarttime(DateUtils.secStmpToDate(Long.parseLong(map.get("starttime"))));
			time.setEndtime(DateUtils.secStmpToDate(Long.parseLong(map.get("endtime"))));
			time.setDeadline(DateUtils.secStmpToDate(Long.parseLong(map.get("deadline"))));
			time.setOrderid(order.getId());
			time.setState(OrderState.CREATED);
			publishTimeService.save(time);
		}
		for (Map<String, String> map : s) {
			PublishStyle style = new PublishStyle();
			style.setOrderid(order.getId());
			style.setType(map.get("type"));

			publishStyleService.save(style);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderid", order.getId());
		return ApiResponse.succeed(map);
	}

	@RequestMapping("/merchant/update")
	public ApiResponse update(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("id"), request.get("title"), request.get("accountid"), request.get("addressid"), request.get("times"),
				request.get("styles"))) {
			return ApiResponse.fail("-10003", "[id,title,accountid,addressid,times,styles]参数缺失");
		}

		PublishOrder orderInfo = new PublishOrder();
		orderInfo.setId(Long.parseLong((String) request.get("id")));
		orderInfo.setTitle((String) request.get("title"));
		orderInfo.setDescription((String) request.get("description"));

		Long accountId = Long.parseLong((String) request.get("accountid"));
		Account account = accountService.findOne(accountId);
		orderInfo.setAccount(account);

		Long addressId = Long.parseLong((String) request.get("addressid"));
		AddressBookEntry address = addressService.findOne(addressId);
		orderInfo.setAddress(address);

		orderInfo.setState(OrderState.CREATED);
		orderInfo.setCreated(new Date());
		PublishOrder order = publishOrderService.save(orderInfo);

		List<Map<String, String>> t = (List<Map<String, String>>) request.get("times");
		List<Map<String, String>> s = (List<Map<String, String>>) request.get("styles");

		for (Map<String, String> map : t) {
			PublishTime time = new PublishTime();
			time.setId(Long.parseLong(map.get("id")));
			time.setPrice(Double.parseDouble(map.get("price")));
			time.setNeed(Integer.parseInt(map.get("needcount")));
			time.setRemark((String) map.get("remark"));
			time.setStarttime(DateUtils.secStmpToDate(Long.parseLong(map.get("starttime"))));
			time.setEndtime(DateUtils.secStmpToDate(Long.parseLong(map.get("endtime"))));
			time.setDeadline(DateUtils.secStmpToDate(Long.parseLong(map.get("deadline"))));
			time.setOrderid(order.getId());
			time.setState(OrderState.CREATED);
			publishTimeService.update(time);
		}
		for (Map<String, String> map : s) {
			PublishStyle style = new PublishStyle();
			style.setOrderid(order.getId());
			style.setType(map.get("type"));
			style.setId(Long.parseLong(map.get("id")));

			publishStyleService.update(style);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderid", order.getId());
		return ApiResponse.succeed(map);
	}

	@RequestMapping("/merchant/delete")
	public ApiResponse delete(@RequestBody() LongIdRequest request) {
		List<PublishTime> times = publishTimeService.getOrderTimes(request.getId());
		List<PublishGrap> graps = orderGrapService.getOrderGrap(request.getId());
		for (PublishTime p : times) {
			publishTimeService.delete(p);
		}
		for (PublishGrap p : graps) {
			orderGrapService.delete(p);
		}

		publishOrderService.delete(request.getId());
		return ApiResponse.succeed();
	}

	@RequestMapping("/merchant/detail")
	public ApiResponse detail(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("orderid"), request.get("timeid"))) {
			return ApiResponse.fail("-10003", "[orderid,timeid]参数缺失");
		}

		Long orderid = Long.parseLong(request.get("orderid"));
		Long timeid = Long.parseLong(request.get("timeid"));

		Object obj = publishTimeService.getOrderDetail(orderid, timeid);

		if (obj == null) {
			return ApiResponse.fail(OrderErrors.NO_ORDER_EXISTS + "", "订单飞到外太空啦，乐享君找不回来了Orz");
		}

		Map<String, Object> order = StringUtils.array2Map(obj, Constants.PUBLISH_ORDER_COLUMN);
		List<String> style = orderStyleService.findOrderPerformance(orderid);
		order.put("style", style);
		String stat = (String) order.get("state");
		order.put("stateString", OrderState.getStateMessage(stat));
		order.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
		order.put("images", imgList(orderid));
		String tel = (String) order.get("tel_nrs");
		if (tel != null) {
			String[] tels = tel.split(",");
			order.put("tel_nrs", tels);
		}
		return ApiResponse.succeed(order);
	}

	private boolean checkParam(Object... obj) {
		for (Object o : obj) {
			if (o == null || o.equals("")) {
				return false;
			}
		}
		return true;
	}

	// 不感兴趣
	@RequestMapping("/merchant/unintereste")
	public ApiResponse unintereste(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("orderid"), request.get("timeid"), request.get("accountid"), request.get("userid"))) {
			return ApiResponse.fail("-10003", "[orderid,timeid,accountid,userid]参数缺失");
		}
		Long accountId = Long.parseLong((String) request.get("accountid"));
		Account account = accountService.findOne(accountId);

		Long orderId = Long.parseLong((String) request.get("orderid"));
		Long timeId = Long.parseLong((String) request.get("timeid"));
		Long userId = Long.parseLong((String) request.get("userid"));

		PublishOrder order = publishOrderService.findOne(orderId);
		if (!order.getAccount().getId().equals(account.getId())) {
			return ApiResponse.fail("-10003", "订单未找到属于该商户的订单");
		}

		PublishGrap grap = orderGrapService.getOrderGrap(orderId, timeId, userId);
		if (grap == null) {
			return ApiResponse.fail("-10003", "订单未被该用户抢到");
		} else {
			grap.setState(GrabState.UNINTERESTED);
		}
		return ApiResponse.succeed();
	}

	// 取消
	@RequestMapping("/merchant/cancel")
	public ApiResponse cancel(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("orderid"), request.get("timeid"), request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[orderid,timeid,accountid]参数缺失");
		}
		Long accountId = Long.parseLong((String) request.get("accountid"));
		Account account = accountService.findOne(accountId);
		Long orderId = Long.parseLong((String) request.get("orderid"));
		PublishOrder order = publishOrderService.findOne(orderId);
		if (!order.getAccount().getId().equals(account.getId())) {
			return ApiResponse.fail("-10003", "订单未找到属于该商户的订单");
		}

		Long timeId = Long.parseLong((String) request.get("timeid"));
		PublishTime timeOrder = publishTimeService.findOne(timeId);

		if (timeOrder.getEndtime().after(new Date())) {// 已经过了演出时间

		}

		boolean isGrap = true;// 是否已经被抢
		List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
		for (int i = 0; i < times.size(); i++) {
			PublishTime time = times.get(i);
			if (!OrderState.CANCELLED.equals(time.getState())) {// 还有未被确认的时间段
				isGrap = false;
				break;
			}
		}
		if (isGrap) {
			order.setState(OrderState.CANCELLED);// 设定大订单状态为取消
			publishOrderService.update(order);
		}

		// List<PublishTime> times = publishTimeService.getOrderTimes(orderId);
		// for(PublishTime t:times){
		// t.setState(OrderState.CANCELLED);
		// publishTimeService.update(t);
		// }
		// 退款

		return ApiResponse.succeed();
	}

	// 取消
	@RequestMapping("/merchant/searchByName")
	public ApiResponse search(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("accountid"), request.get("orderid"), request.get("timeid"), request.get("pageIndex"), request.get("key"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid,timeid,pageIndex,key]参数缺失");
		}
		Long accountId = Long.parseLong((String) request.get("accountid"));
		Long orderId = Long.parseLong((String) request.get("orderid"));
		Long timeId = Long.parseLong((String) request.get("timeid"));
		int pageIndex = Integer.parseInt((String) request.get("pageIndex"));
		String name = request.get("key");

		List<Object> result = publishOrderService.getOrdersNyMusicName(accountId, orderId, timeId, name, pageIndex, pageSize);
		int total = publishOrderService.getOrdersNyMusicNameNo(accountId, orderId, timeId, name);
		List<Map<String, Object>> list = StringUtils.list2ArrayMap(result, Constants.PUBLISH_ORDER_COLUMN);
		for (Map<String, Object> r : list) {
			String stat = (String) r.get("state");
			r.put("stateString", OrderState.getStateMessage(stat));
			r.put("photoUrl", "http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg");
			Long orderid = Long.parseLong((Integer) r.get("orderid") + "");
			List<String> style = orderStyleService.findOrderPerformance(orderid);
			r.put("style", style);

			String tel = (String) r.get("tel_nrs");
			if (tel != null) {
				String[] tels = tel.split(",");
				r.put("r", tels);
			}
			r.put("images", imgList(orderid));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("content", list);
		return ApiResponse.succeed(map);
	}

}
