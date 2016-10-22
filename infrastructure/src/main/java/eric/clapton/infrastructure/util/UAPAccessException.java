package eric.clapton.infrastructure.util;

import org.springframework.core.NestedRuntimeException;

public class UAPAccessException extends NestedRuntimeException {
    private static final long serialVersionUID = -7038234569121064750L;

    private static final String DEFAULT_MESSAGE = "UAP 访问错误。";

    public UAPAccessException(String message, Throwable cause) {
        super(StringUtils.isNullOrEmpty(message) ? DEFAULT_MESSAGE : message, cause);
    }

    public UAPAccessException(String message) {
        this(message, null);
    }

    public UAPAccessException() {
        this("");
    }

    public UAPAccessException(Throwable cause) {
        this(null, cause);
    }

}
