package eric.clapton.musician.web.controller.admin;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.musician.core.entity.dto.WebResponse;
import eric.clapton.musician.core.entity.po.bananer.Bananer;
import eric.clapton.musician.service.admin.AdminService;
import eric.clapton.musician.service.bananer.BananerService;
import eric.clapton.musician.service.image.ImageService;
import eric.clapton.musician.service.image.provider.seven.SevenImageProvider;

@Controller
@RequestMapping("/admin/home")
public class AdminHomeImageController {

	@Resource
	private AdminService adminService;

	@Resource
	private BananerService bananerService;

	@Resource
	private ImageService imageService;
	
	@Resource
	private SevenImageProvider imageProvider;

	@RequestMapping("/welcome/init")
	public String welInit() {

		return "admin/images/welcome";
	}
	
	@RequestMapping("/welcome/addPage")
	public String addInit() {
		String token = imageProvider.token();
		WebResponse.attr("token", token);
		return "admin/images/welAdd";
	}

	@RequestMapping("welcome/list")
	public String welList(int page, int pageSize, String state) {
		getDate(page, pageSize, state, "0");
		return "admin/images/imgListAjax";
	}

	@RequestMapping("welcome/add")
	public String welAdd() {

		return WebResponse.success();
	}

	@RequestMapping("welcome/update")
	public String updateBanner(long id) {

		return WebResponse.success();
	}

	@RequestMapping("welcome/query")
	public String queryBanner(long id) {

		return "admin/images/welDetail";
	}

	@RequestMapping("frozeen")
	@ResponseBody
	public String frozeen(long id, String state) {
		Bananer bananer = bananerService.findOne(id);
		if(bananer==null){
			return WebResponse.failure("1", "失败");
		}
		bananer.setState(state);
		bananerService.update(bananer);
		return WebResponse.success();
	}

	@RequestMapping("delete")
	@ResponseBody
	public String delete(long id) {
		bananerService.delete(id);
		return WebResponse.success();
	}

	@RequestMapping("/bananer/init")
	public String banInit() {
		return "admin/images/bananer";
	}

	@RequestMapping("bananer/list")
	public String banList(int page, int pageSize, String date) {
		getDate(page, pageSize, date, "1");
		return "admin/images/imgListAjax";
	}

	private void getDate(int page, int pageSize, String state, String type) {
		Searchable searchable = Searchable.newSearchable();
		searchable.orderBy("sequence");
		searchable.orderByDescending("pubtime");
		searchable.setPage(new PageRequest(page - 1, pageSize));
		if (state != null) {
			searchable.contains("state", state);
		}
		searchable.equal("type", type);

		Page<Bananer> pageList = bananerService.findAll(searchable);

		WebResponse.ajaxPage(pageList, page, pageSize);

	}
}
