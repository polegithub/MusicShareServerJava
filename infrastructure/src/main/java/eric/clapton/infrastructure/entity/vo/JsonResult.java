package eric.clapton.infrastructure.entity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.util.JsonMapper;

@JsonSerialize
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 8628366136805401911L;

    private final String error;
    private final Object data;
    private final Object extraInfo;

    private static final JsonMapper jsonMapper = new JsonMapper();

    private JsonResult(String error, Object data, Object extraInfo) {
        this.error = error;
        this.data = data;
        this.extraInfo = extraInfo;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public final boolean hasError() {
        return getError() != null;
    }

    public static JsonResult fail(String error) {
        return new JsonResult(error, null, null);
    }

    public static JsonResult fail(ServiceException e) {
        return fail(e.describe());
    }

    public static JsonResult fail(String error, Object extraInfo) {
        return new JsonResult(error, null, extraInfo);
    }

    public static JsonResult succeed(Object data, Object extraInfo) {
        return new JsonResult(null, data, extraInfo);
    }

    public static JsonResult succeed(Object data) {
        return new JsonResult(null, data, null);
    }

    public static JsonResult succeed() {
        return new JsonResult(null, null, null);
    }

    public String toJsonString() {
        return jsonMapper.toJson(this);
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
