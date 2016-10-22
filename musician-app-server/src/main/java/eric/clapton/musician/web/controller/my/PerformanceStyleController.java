package eric.clapton.musician.web.controller.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.po.performance.PerformanceTag;
import eric.clapton.musician.service.performance.PerformanceTagService;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/mOrder")
@ResponseBody
public class PerformanceStyleController extends ApiControllerSupport {
	
	@Resource
	private PerformanceTagService orderStyleService;

	@RequestMapping("/style/list")
	public ApiResponse list() {
		List<PerformanceTag> style = orderStyleService.findAll();
		List<Map<String,String>> sty = new ArrayList<Map<String,String>>();
		for(PerformanceTag p:style){
			Map<String,String> sytleMap = new HashMap<String,String>();
			sytleMap.put("id", p.getId()+"");
			sytleMap.put("tag", p.getTag());
			sty.add(sytleMap);
		}
		
		return ApiResponse.succeed(sty);
	}
	
}
