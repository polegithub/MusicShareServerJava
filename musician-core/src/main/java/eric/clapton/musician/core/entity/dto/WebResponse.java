package eric.clapton.musician.core.entity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import eric.clapton.musician.core.util.Constants;

public class WebResponse {

	public static String error(String errorCode, String errorMessage) {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute(Constants.ERROR_CODE_NAME, errorCode);
		request.setAttribute(Constants.ERROR_MESSAGE_NAME, errorMessage);
		return "/common/error";
	}
	
	
	public static void ajaxPage(Page elements,int page,int pageSize) {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		int totalSize = elements.getTotalPages();
		int toatlPage = totalSize / pageSize + 1;
		request.setAttribute("num", totalSize);
		request.setAttribute("toatl", toatlPage);
		request.setAttribute("page", page);
		ArrayList lists = Lists.newArrayList(elements.getContent());
		System.out.println(lists);
		attr("data", lists);
		
	}
	
	public static void attr(String key, Object object) {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute(key, object);
	}

	public static void sessionAttr(String key, Object object) {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().setAttribute(key, object);
	}
	
	public static HttpSession getSession() {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getSession();
	}
	
	public static String toJson(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	public static String success(){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put(Constants.ERROR_CODE_NAME, "0");
			map.put(Constants.ERROR_MESSAGE_NAME, "成功");
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	public static String pageSuccess(String action){
		return noResultPage("0","成功",action);
	}
	
	public static String noResultPage(String code,String message,String action){
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute(Constants.ERROR_CODE_NAME, code);
		request.setAttribute(Constants.ERROR_MESSAGE_NAME, message);
		request.setAttribute(Constants.ERROR_FORWORD_ACTION_NAME, action);
		return "/common/ajax";
	}
	
	public static String failure(){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put(Constants.ERROR_CODE_NAME, "1");
			map.put(Constants.ERROR_MESSAGE_NAME, "失败");
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	public static String failure(String code,String message){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put(Constants.ERROR_CODE_NAME, code);
			map.put(Constants.ERROR_MESSAGE_NAME, message);
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	private static final ObjectMapper mapper = new ObjectMapper();

}
