package eric.clapton.musician.web.controller.my;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.util.DateUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/fOrder")
@ResponseBody
public class FieldOrderController extends ApiControllerSupport {
	private boolean checkParam(Object... obj) {
		for (Object o : obj) {
			if (o == null || o.equals("")) {
				return false;
			}
		}
		return true;
	}

	@RequestMapping("/music/aviabled")
	public String aviabled(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("pageIndex"),request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[pageIndex,accountid]参数缺失").toString();
		}

		// int total = 20;
		// List<Object> result = new ArrayList<Object>();
		// List<Map<String, Object>> list = StringUtils.list2ArrayMap(result,
		// Constants.PUBLISH_ORDER_COLUMN);
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("total", total);
		// map.put("content", list);
		// return ApiResponse.succeed(map);

		String str = "{\"status\":\"ok\",\"errorCode\":null,\"errorMessage\":null,\"payload\":{\"total\":2,\"content\":[{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要5个人。。\",\"starttime\":1456833600000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":100.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":10,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456837200000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]},{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要55个人。。\",\"starttime\":1456840800000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":200.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":0,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456844400000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]}]}}";

		return str;
	}

	@RequestMapping("/music/list")
	public String list(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("state"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,state]参数缺失").toString();
		}
		String str = "{\"status\":\"ok\",\"errorCode\":null,\"errorMessage\":null,\"payload\":{\"total\":2,\"content\":[{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要5个人。。\",\"starttime\":1456833600000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":100.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":10,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456837200000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]},{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要55个人。。\",\"starttime\":1456840800000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":200.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":0,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456844400000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]}]}}";

		return str;
	}

	@RequestMapping("/merchant/list")
	public String mList(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("state"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,state]参数缺失").toString();
		}
		String str = "{\"status\":\"ok\",\"errorCode\":null,\"errorMessage\":null,\"payload\":{\"total\":2,\"content\":[{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要5个人。。\",\"starttime\":1456833600000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":100.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":10,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456837200000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]},{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要55个人。。\",\"starttime\":1456840800000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":200.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":0,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456844400000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]}]}}";

		return str;
	}

	// 音乐人抢订单
	@RequestMapping("/music/grab")
	public ApiResponse grap(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("orderid"))) {
			return ApiResponse.fail("-10003", "[accountid,orderid]参数缺失");
		}

		return ApiResponse.succeed();
	}

	// 商家查看音乐人抢单列表
	@RequestMapping("/merchant/grabList")
	public String grabList(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("pageIndex"), request.get("orderid"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex,orderid]参数缺失").toString();
		}
		String str = "{\"status\":\"ok\",\"errorCode\":null,\"errorMessage\":null,\"payload\":{\"total\":2,\"content\":[{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要5个人。。\",\"starttime\":1456833600000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":100.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":10,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456837200000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]},{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要55个人。。\",\"starttime\":1456840800000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":200.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":0,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456844400000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]}]}}";

		return str;

	}

	// 商家选择音乐人
	@RequestMapping("/merchant/agree")
	public ApiResponse agree(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"), request.get("userid"), request.get("orderid"))) {
			return ApiResponse.fail("-10003", "[accountid,userid,orderid]参数缺失");
		}
		return ApiResponse.succeed();
	}

	// 商家统计
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/merchant/total")
	public ApiResponse total(@RequestBody() Map<String, String> request) {

		if (!checkParam(request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[accountid]参数缺失");
		}

		int pOrderNo = 2;
		int fOrderNo = 3;

		Calendar c = Calendar.getInstance();
		int tody = c.get(Calendar.DAY_OF_YEAR);
		Map<String, Object> map = new HashMap<String, Object>();

		List history = new ArrayList();
		for (int i = 0; i < 30; i++) {
			Map list = new HashMap();
			c.set(Calendar.DAY_OF_YEAR, tody - i);
			list.put(DateUtils.fomatDate(c.getTime()), 30 - i);
			history.add(list);
		}

		map.put("pOrderNo", pOrderNo);
		map.put("fOrderNo", fOrderNo);
		map.put("history", history);

		return ApiResponse.succeed(map);
	}
	
	// 商家统计
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping("/music/total")
		public ApiResponse mTotal(@RequestBody() Map<String, String> request) {

			if (!checkParam(request.get("accountid"))) {
				return ApiResponse.fail("-10003", "[accountid]参数缺失");
			}

			int pOrderNo = 2;
			int fOrderNo = 3;

			Calendar c = Calendar.getInstance();
			int tody = c.get(Calendar.DAY_OF_YEAR);
			Map<String, Object> map = new HashMap<String, Object>();

			List history = new ArrayList();
			for (int i = 0; i < 30; i++) {
				Map list = new HashMap();
				c.set(Calendar.DAY_OF_YEAR, tody - i);
				list.put(DateUtils.fomatDate(c.getTime()), 30 - i);
				history.add(list);
			}

			map.put("pOrderNo", pOrderNo);
			map.put("fOrderNo", fOrderNo);
			map.put("history", history);

			return ApiResponse.succeed(map);
		}

	// 商家统计
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/merchant/flowno")
	public ApiResponse flow(@RequestBody() Map<String, String> request) {
		if (!checkParam(request.get("accountid"),request.get("pageIndex"))) {
			return ApiResponse.fail("-10003", "[accountid,pageIndex]参数缺失");
		}
		Map map = new HashMap();
		map.put("total", 10);
		Calendar c = Calendar.getInstance();
		int tody = c.get(Calendar.DAY_OF_YEAR);
		List history = new ArrayList();
		for (int i = 0; i < 30; i++) {
			Map list = new HashMap();
			c.set(Calendar.DAY_OF_YEAR, tody - i);
			list.put(DateUtils.fomatDate(c.getTime()), "发单-驻场订单" + 100 * i + "元");
			history.add(list);
		}

		map.put("history", history);

		return ApiResponse.succeed(map);

	}

	// 商家统计
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/music/flowno")
	public ApiResponse flowNo(@RequestBody() Map<String, String> request) {
		Map map = new HashMap();
		map.put("total", 10);
		Calendar c = Calendar.getInstance();
		int tody = c.get(Calendar.DAY_OF_YEAR);
		List history = new ArrayList();
		for (int i = 0; i < 30; i++) {
			Map list = new HashMap();
			c.set(Calendar.DAY_OF_YEAR, tody - i);
			list.put(DateUtils.fomatDate(c.getTime()), "接单-驻场订单" + 100 * i + "元");
			history.add(list);
		}

		map.put("history", history);

		return ApiResponse.succeed(map);

	}

	@RequestMapping("/merchant/create")
	public ApiResponse create(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("title"), request.get("accountid"), request.get("addressid"), request.get("starttime"), request.get("needcount"),
				request.get("price"), request.get("endtime"), request.get("deadline"), request.get("styles"))) {
			return ApiResponse.fail("-10003", "[title,accountid,starttime,needcount,price,addressid,endtime,deadline,styles]参数缺失");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderid", DateUtils.fomatDate(new Date(), "yyyyMMddHHmmss"));
		return ApiResponse.succeed(map);
	}

	@RequestMapping("/merchant/update")
	public ApiResponse update(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("title"), request.get("orderid"), request.get("accountid"), request.get("addressid"), request.get("starttime"),
				request.get("needcount"), request.get("price"), request.get("endtime"), request.get("deadline"), request.get("styles"))) {
			return ApiResponse.fail("-10003", "[orderid,title,accountid,starttime,needcount,price,addressid,endtime,deadline,styles]参数缺失");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderid", DateUtils.fomatDate(new Date(), "yyyyMMddHHmmss"));
		return ApiResponse.succeed(map);
	}

	@RequestMapping("/merchant/delete")
	public ApiResponse delete(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("orderid"), request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[orderid,accountid]参数缺失");
		}

		return ApiResponse.succeed();
	}
	
	@RequestMapping("/merchant/detail")
	public String detail(@RequestBody() Map<String, Object> request) {

		if (!checkParam(request.get("orderid"), request.get("accountid"))) {
			return ApiResponse.fail("-10003", "[orderid,accountid]参数缺失").toString();
		}
		
		
		String str = "{\"status\":\"ok\",\"errorCode\":null,\"errorMessage\":null,\"payload\":{\"total\":2,\"content\":[{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要5个人。。\",\"starttime\":1456833600000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":100.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":10,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456837200000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]},{\"tel_nrs\":[\"11223344\",\"55667788\",\"1155555\"],\"images\":[\"http://i1.sinaimg.cn/gm/2014/0415/U10229P115DT20140415134404.jpg\",\"http://anhui.sinaimg.cn/2014/0901/U10284P1276DT20140901171435.jpg\",\"http://imgsrc.baidu.com/baike/pic/item/a8773912b31bb051094a9140347adab44aede068.jpg\"],\"city\":\"上海\",\"latitude\":30.54,\"description\":\"张江会所开张表演，需要20-50人的乐队表演\",\"remark\":\"需要55个人。。\",\"starttime\":1456840800000,\"stateString\":\"演出完成\",\"title\":\"张江会所开张表演\",\"accountid\":1000000,\"photoUrl\":\"http://img1.imgtn.bdimg.com/it/u=2262317107,1332749424&fm=206&gp=0.jpg\",\"price\":200.21,\"contact\":\"宋冬野\",\"state\":\"COMPLETE\",\"deadline\":1456837200000,\"longitude\":10.152,\"address\":\"浦东张江\",\"orderid\":1000000016,\"created\":1456222115000,\"needcount\":0,\"detail_address\":\"上海市张江镇高科中路1100\",\"endtime\":1456844400000,\"addressid\":9,\"addressName\":\"张江公馆\",\"style\":[{\"type\":\"流行\"},{\"type\":\"古典\"}]}]}}";

		return str;
	}
	

}
