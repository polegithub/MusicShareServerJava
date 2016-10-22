package eric.clapton.musician.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.BasePageResponse;
import eric.clapton.musician.web.ErrorCodes;

/**
 * 提供 API 异常处理以及日志工具。
 * 
 * @author cheer
 *
 */
public class ApiControllerSupport {
	protected final Logger logger;

	public ApiControllerSupport() {
		logger = LoggerFactory.getLogger(getClass());
	}

	protected ApiResponse parameterMissing() {
		return parameterMissing(null);
	}

	protected ApiResponse parameterMissing(String paramName) {
		String error = StringUtils.isNullOrEmpty(paramName)
				? "Required parameter is missing or the value is empty."
						+ " Please refer to the API documentation for more information."
				: String.format(Locale.CHINA, "Required parameter '%s' is missing or the value is empty."
						+ " Please refer to the API documentation for more information.", paramName);

		return ApiResponse.fail(ErrorCodes.REQUEST_PARAM_MISSING, error);
	}

	/**
	 * 从{@link Page} 对象中创建一个分页响应对象。
	 * 
	 * @param <TSource>
	 *            Page 对象中列表项的类型。
	 * @param page
	 *            包含分页数据的分页对象。不能为 null。
	 * @param mapper
	 *            如果需要对分页中列表进行映射，则为对应的映射函数。否则为 null。
	 * @return
	 */
	protected <TSource> BasePageResponse getPageResponse(Page<? extends TSource> page,
			Function<? super TSource, ?> mapper) {
		List<?> list = mapper == null ? page.getContent()
				: page.getContent().stream().map(mapper).collect(Collectors.toList());

		return new BasePageResponse(page.getNumber(), page.getSize(), page.getTotalElements(), list);
	}

	protected final <TSource> BasePageResponse getPageResponse(Page<? extends TSource> page) {
		return getPageResponse(page, null);
	}

	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	public ApiResponse onServiceException(HttpServletRequest request, ServiceException e) {
		Throwable cause = e.getCause();
		if (cause == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("[{}] 在调用服务时出现了业务异常：{}", getFormattedRequestURI(request), e.describe());
			}
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(Locale.CHINA, "[%s] 在调用服务时出现了异常：%s", getFormattedRequestURI(request),
						e.describe()), cause);
			}
		}

		Integer errorCode = e.getErrorCode();

		return ApiResponse.fail(errorCode == null ? Integer.MAX_VALUE + "" : errorCode.toString(), e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse onHttpMessageNotReadableException(HttpServletRequest request,
			HttpMessageNotReadableException e) {
		if (logger.isWarnEnabled()) {
			logger.warn(String.format(Locale.CHINA, "[%s] 输入了不合法的参数。", getFormattedRequestURI(request)), e);
		}

		String message;
		Throwable cause = e.getCause();
		if (cause instanceof JsonProcessingException) {
			JsonProcessingException ex = (JsonProcessingException) cause;

			int lineNumber = 0;
			int columnNumber = 0;
			JsonLocation location = ex.getLocation();
			if (location != null) {
				lineNumber = location.getLineNr();
				columnNumber = location.getColumnNr();
			}
			if (lineNumber > 0 && columnNumber > 0) {
				message = String.format(Locale.CHINA, "%s（第 %d 行，第 %d 列）", getErrorMessageFor(ex), lineNumber,
						columnNumber);
			} else {
				message = getErrorMessageFor(ex);
			}

		} else {
			message = String.format(Locale.CHINA,
					"在读取输入的内容时发生错误，请与您的系统管理员联系。" + "下面记录的错误信息可能有助于排查该问题产生的原因，请将其发送给系统管理员。%s%s", StringUtils.NEW_LINE,
					getStackTrace(e));
		}

		return ApiResponse.fail("isv.input_not_readable", message);
	}

	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse onGenericException(HttpServletRequest request, Throwable t) {
		if (logger.isErrorEnabled()) {
			logger.error(String.format(Locale.CHINA, "[%s] 在处理请求时出现了未处理的异常。", getFormattedRequestURI(request)), t);
		}

		String message = String.format(Locale.CHINA,
				"很抱歉，我们在尝试处理您的请求时遇到了些问题。" + "下面记录的错误信息可能有助于排查该问题产生的原因，请将其发送给系统管理员。%s%s", StringUtils.NEW_LINE,
				getStackTrace(t));

		return ApiResponse.fail("sys.error", message);
	}

	private static String getErrorMessageFor(JsonProcessingException e) {
		if (e instanceof JsonParseException) {
			return "输入的 JSON 字符串格式无效。";
		}
		if (e instanceof JsonMappingException) {
			return "输入的 JSON 字符串结构不符合预期。有关详细信息，请参阅 API 文档。";
		}
		return "服务器无法处理输入，请与您的系统管理员联系。";
	}

	private static String getFormattedRequestURI(HttpServletRequest request) {
		String query = request.getQueryString();
		if (StringUtils.isNullOrEmpty(query)) {
			return String.format(Locale.ROOT, "%s %s %s", request.getMethod(), request.getRequestURI(),
					request.getProtocol());
		}
		return String.format(Locale.ROOT, "%s %s?%s %s", request.getMethod(), request.getRequestURI(), query,
				request.getProtocol());
	}

	private static String getStackTrace(Throwable t) {
		if (t == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(t.toString());
		builder.append(StringUtils.NEW_LINE);

		StackTraceElement[] stackTrace = t.getStackTrace();

		for (int i = 0; i < stackTrace.length; i++) {
			builder.append(StringUtils.NEW_LINE);

			StackTraceElement e = stackTrace[i];
			builder.append("在");
			if (e.isNativeMethod()) {
				builder.append("本地方法");
			} else {
				builder.append("方法");
			}
			builder.append(' ');

			builder.append(e.getClassName()).append('.').append(e.getMethodName());

			builder.append(" - ");

			String fileName = e.getFileName();
			if (fileName != null) {
				builder.append(fileName);

				int lineNumber = e.getLineNumber();
				if (lineNumber > 0) {
					builder.append(" 第 ").append(lineNumber).append(" 行");
				}
			} else {
				builder.append("[源未知]");
			}
		}

		return builder.toString();
	}
}
