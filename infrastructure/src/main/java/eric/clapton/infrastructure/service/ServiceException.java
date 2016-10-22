package eric.clapton.infrastructure.service;

import java.util.Locale;

/**
 * 表示业务错误。
 * 
 * @author xuw
 *
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 866925799887409285L;
    private final Integer errorCode;

    public ServiceException() {
        this("未指定的错误。");
    }

    public ServiceException(String message) {
        this(null, message, null);
    }

    public ServiceException(int errorCode, String message) {
        this(Integer.valueOf(errorCode), message, null);
    }

    public ServiceException(String message, Throwable cause) {
        this(null, message, cause);
    }

    public ServiceException(int errorCode, String message, Throwable cause) {
        this(Integer.valueOf(errorCode), message, cause);
    }

    private ServiceException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);

        this.errorCode = errorCode;
    }

    public final boolean hasErrorCode() {
        return errorCode != null;
    }

    public final Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 获得一个描述此错误的字符串。
     * 
     * @return
     */
    public String describe() {
        if (!hasErrorCode()) {
            return getMessage();
        }

        return String.format(Locale.CHINA, "%1$s (错误代码 0x%2$08X)", getMessage(), getErrorCode());
    }

    public static ServiceException corruptedData(String message) {
        return new ServiceException(CommonErrors.CORRUPTED_DATA, message);
    }

    public static ServiceException notFound(String message) {
        return new ServiceException(CommonErrors.NOT_FOUND, message);
    }
}
