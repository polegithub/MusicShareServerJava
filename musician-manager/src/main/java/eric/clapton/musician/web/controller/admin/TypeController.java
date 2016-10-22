package eric.clapton.musician.web.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.po.product.ProductType;
import eric.clapton.musician.service.product.ProductTypeService;

@Controller
@RequestMapping("/admin/protype")
public class TypeController {

	
	@Resource
	private ProductTypeService productTypeService;
	
	
	@RequestMapping("/list")
	public String list() {
		List<ProductType> type = productTypeService.findAll();
		
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute("data", type);
		return "admin/type/typeListAjax";
	}
	
	
	@RequestMapping("/add")
	@ResponseBody
	public ApiResponse add(ProductType type) {
		
		productTypeService.save(type);
		
		
		return ApiResponse.succeed();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ApiResponse delete(Long id) {
		
		productTypeService.delete(id);
		
		
		return ApiResponse.succeed();
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public ApiResponse update(ProductType type) {
		
		productTypeService.update(type);
		
		
		return ApiResponse.succeed();
	}
	
	
	@RequestMapping("/detail")
	@ResponseBody
	public ApiResponse detail(Long id) {
		
		ProductType type = productTypeService.findOne(id);
		
		return ApiResponse.succeed(type);
	}
	
}
