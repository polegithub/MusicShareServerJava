package eric.clapton.musician.web.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.WebResponse;
import eric.clapton.musician.core.entity.po.admin.Admin;
import eric.clapton.musician.core.util.Constants;
import eric.clapton.musician.service.admin.AdminService;
import eric.clapton.musician.service.image.provider.decrypt.DecryptEncryptProvider;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private ObjectMapper objectMapper;

	@Resource
	private AdminService adminService;
	
	
	@Resource
	private DecryptEncryptProvider decryProvider;
	

	@RequestMapping("/login")
	public String login(String username,String password,String dycode) {
		if(username==null || "".equals(username) || password==null || "".equals(password)){
			return "redirect:/index";
		}
		password = decryProvider.encryptToMD5(password).toLowerCase();
		Admin admin = adminService.login(username, password);
		
		if(admin==null){
			return WebResponse.error("-10001", "用户或密码错误");
		}
		
		WebResponse.sessionAttr(Constants.SESSION_ADMIN_NAME, admin);
		return "main";
	}


	@RequestMapping("/list")
	public ApiResponse list(int page,int pageSize,String date) {
		
		return ApiResponse.succeed();
	}
	
}
