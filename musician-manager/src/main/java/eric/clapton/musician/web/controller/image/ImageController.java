package eric.clapton.musician.web.controller.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.WebResponse;
import eric.clapton.musician.core.entity.po.image.ImageStore;
import eric.clapton.musician.service.image.ImageService;
import eric.clapton.musician.service.image.provider.seven.SevenImageProvider;

@Controller
@RequestMapping("/image")
public class ImageController {

	@Resource
	private SevenImageProvider imageProvider;

	@Resource
	private ObjectMapper objectMapper;

	@Resource
	private ImageService imageService;

	@RequestMapping("/token")
	@ResponseBody
	public ApiResponse token(String file) {
		Map<String, Object> result = new HashMap<String, Object>();
		String token = imageProvider.token();
		result.put("token", token);
		// 保存图片链接
		ImageStore image = new ImageStore();
		image.setToken(token);
		image.setFile(file);
		image.setState("0");
		imageService.save(image);
		return ApiResponse.succeed(result);
	}

	@RequestMapping("/notify")
	@ResponseBody
	public String page(String name) throws JsonProcessingException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("name", name);

		// 保存图片链接
		ImageStore image = imageService.findByFile(name);
		image.setSrc("");
		imageService.update(image);

		return objectMapper.writeValueAsString(result);
	}

	@RequestMapping("/list")
	public String list(int page, int pageSize, String date) {
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page-1, pageSize);
		
		 Page<ImageStore> images = imageService.findAll(searchable);
		 WebResponse.ajaxPage(images, page, pageSize);
		
		return "admin/images/imgListAjax";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public ApiResponse delete(String file) {

		ImageStore image = imageService.findByFile(file);
		if(image==null){
			return ApiResponse.fail("-10002", "图片不存在");
		}
		imageService.delete(image);

		return ApiResponse.succeed();
	}
}
