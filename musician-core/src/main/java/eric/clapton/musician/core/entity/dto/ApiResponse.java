package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

import eric.clapton.infrastructure.util.JsonMapper;

public class ApiResponse implements Serializable {
	private static final long serialVersionUID = -5706316088734601357L;

	private final String status;
	private final String errorCode;
	private final String errorMessage;
	private final Object payload;

	public ApiResponse(String status, String errorCode, String errorMessage, Object payload) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.payload = payload;
	}

	public final String getStatus() {
		return status;
	}

	public final String getErrorCode() {
		return errorCode;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	public final Object getPayload() {
		return payload;
	}

	public static final String STATUS_OK = "ok";
	public static final String STATUS_FAIL = "fail";
	public static final String STATUS_ERROR = "error";

	public static final ApiResponse succeed() {
		return ApiResponse.succeed(null);
	}

	public static final ApiResponse succeed(Object payload) {
		return new ApiResponse(STATUS_OK, null, null, payload);
	}

	public static final ApiResponse fail(String errorCode, String errorMessage, Object payload) {
		return new ApiResponse(STATUS_FAIL, errorCode, errorMessage, payload);
	}

	public static final ApiResponse fail(String errorCode, String errorMessage) {
		return ApiResponse.fail(errorCode, errorMessage, null);
	}

	public static final ApiResponse error(String errorCode, String errorMessage) {
		return new ApiResponse(STATUS_ERROR, errorCode, errorMessage, null);
	}
	
	@Override
	public String toString() {
		String res = new JsonMapper().toJson(this);
		return res;
	}
}
