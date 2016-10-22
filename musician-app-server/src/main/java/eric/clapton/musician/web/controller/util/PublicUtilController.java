package eric.clapton.musician.web.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eric.clapton.musician.core.entity.dto.WebResponse;

@Controller
public class PublicUtilController {

	@RequestMapping("/index")
	public String home() {

		return "index";
	}

	@RequestMapping("/exist")
	public String exist() {
		WebResponse.getSession().invalidate();
		return "index";
	}

	@RequestMapping("/page/{scope}/{business}/{page}")
	public String page(@PathVariable String scope,@PathVariable String business,@PathVariable String page) {
		StringBuilder sb = new StringBuilder();
		if(scope!=null && !"".equals(scope)){
			sb.append("/").append(scope);
		}
		if(business!=null && !"".equals(business)){
			sb.append("/").append(business);
		}
		if(page!=null && !"".equals(page)){
			sb.append("/").append(page);
		}
		return sb.toString();
	}
	
}
