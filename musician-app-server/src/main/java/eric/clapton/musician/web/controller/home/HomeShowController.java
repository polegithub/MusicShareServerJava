package eric.clapton.musician.web.controller.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.po.activity.Activity;
import eric.clapton.musician.core.entity.po.bananer.Bananer;
import eric.clapton.musician.core.entity.po.product.Product;
import eric.clapton.musician.core.entity.po.product.ProductType;
import eric.clapton.musician.service.activity.ActivityService;
import eric.clapton.musician.service.bananer.BananerService;
import eric.clapton.musician.service.image.ImageService;
import eric.clapton.musician.service.product.ProductService;
import eric.clapton.musician.service.product.ProductTypeService;

@Controller
@ResponseBody
@RequestMapping("/home")
public class HomeShowController {

	@Resource
	private BananerService bananerService;
	
	@Resource
	private ActivityService activityService;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private ProductService productService;

	@Resource
	private ImageService imageService;

	@RequestMapping("/welcome")
	@ResponseBody
	public ApiResponse welcome() {
		// int pageIndex = 0, pageSize = 4;
		Searchable searchable = Searchable.newSearchable();
		searchable.orderByDescending("pubtime");
		// searchable.setPage(new PageRequest(pageIndex, pageSize));
		searchable.equal("type", "0");
		searchable.equal("state", "0");
		Page<Bananer> page = bananerService.findAll(searchable);

		ArrayList<Bananer> list = Lists.newArrayList(page.getContent());

		return ApiResponse.succeed(list);
	}

	@RequestMapping("/banner")
	@ResponseBody
	public ApiResponse list() {
		// int pageIndex = 0, pageSize = 4;
		Searchable searchable = Searchable.newSearchable();
		searchable.orderByDescending("pubtime");
		searchable.equal("type", "1");
		searchable.equal("state", "0");
		// searchable.setPage(new PageRequest(pageIndex, pageSize));

		Page<Bananer> page = bananerService.findAll(searchable);

		ArrayList<Bananer> list = Lists.newArrayList(page.getContent());

		return ApiResponse.succeed(list);
	}

	@RequestMapping("/activity")
	@ResponseBody
	public ApiResponse activity() {
		Searchable searchable = Searchable.newSearchable();
		searchable.orderByDescending("time");
		searchable.equal("state", "0");

		Page<Activity> page = activityService.findAll(searchable);

		ArrayList<Activity> list = Lists.newArrayList(page.getContent());

		return ApiResponse.succeed(list);
	}

	@RequestMapping("/type")
	@ResponseBody
	public ApiResponse goodsType() {
		// List<Map<String, String>> data = new LinkedList<Map<String,
		// String>>();
		// Map<String, String> record = new HashMap<String, String>();
		// record.put("type", "1");
		// record.put("name", "乐手");
		// record.put("logo", "https://git.oschina.net/logo.gif");
		// data.add(record);
		//
		// record = new HashMap<String, String>();
		// record.put("type", "2");
		// record.put("name", "乐谱");
		// record.put("logo", "https://git.oschina.net/logo.gif");
		// data.add(record);
		//
		// record = new HashMap<String, String>();
		// record.put("type", "3");
		// record.put("name", "乐器");
		// record.put("logo", "https://git.oschina.net/logo.gif");
		// data.add(record);
		//
		// record = new HashMap<String, String>();
		// record.put("type", "4");
		// record.put("name", "调音师");
		// record.put("logo", "https://git.oschina.net/logo.gif");
		// data.add(record);

		Searchable searchable = Searchable.newSearchable();
		searchable.equal("state", "0");

		Page<ProductType> page = productTypeService.findAll(searchable);

		ArrayList<ProductType> list = Lists.newArrayList(page.getContent());

		return ApiResponse.succeed(list);
	}

	@RequestMapping("/search")
	@ResponseBody
	public ApiResponse search(String key) {
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		Map<String, Object> record = new HashMap<String, Object>();

		record.put("type", "乐谱");
		List<Map<Object, Object>> list = new LinkedList<Map<Object, Object>>();
		Map<Object, Object> temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "广陵散");
		temp.put("desc", "广陵散xxxx");
		temp.put("id", "123223");
		list.add(temp);
		temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "广陵散");
		temp.put("desc", "广陵散xxxx");
		temp.put("id", "123223");
		list.add(temp);
		record.put("data", list);
		data.add(record);

		record = new HashMap<String, Object>();
		list = new LinkedList<Map<Object, Object>>();
		record.put("type", "乐手");
		temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "女子十二乐坊");
		temp.put("desc", "女子十二乐坊xxxx");
		temp.put("id", "5241454");
		list.add(temp);

		temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "信乐团");
		temp.put("desc", "信乐团xxxx");
		temp.put("id", "5241454");
		list.add(temp);
		record.put("data", list);
		data.add(record);

		record = new HashMap<String, Object>();
		list = new LinkedList<Map<Object, Object>>();
		record.put("type", "器材/设备");
		temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "笛子");
		temp.put("desc", "笛子xxxx");
		temp.put("id", "23232332");
		list.add(temp);
		temp = new HashMap<Object, Object>();
		temp.put("src", "https://git.oschina.net/logo.gif");
		temp.put("name", "古筝");
		temp.put("desc", "古筝xxxx");
		temp.put("id", "23232332");
		list.add(temp);
		record.put("data", list);
		data.add(record);

		return ApiResponse.succeed(data);
	}

	@RequestMapping("/product")
	@ResponseBody
	public ApiResponse goodsType(int type, int pageIndex, int pageSize) {

		Searchable searchable = Searchable.newSearchable();
		searchable.equal("state", "0");
		searchable.equal("category", type);
		searchable.setPage(new PageRequest(pageIndex, pageSize));

		Page<Product> pageData = productService.findAll(searchable);

		ArrayList<Product> list = Lists.newArrayList(pageData.getContent());

		return ApiResponse.succeed(list);
	}

}
