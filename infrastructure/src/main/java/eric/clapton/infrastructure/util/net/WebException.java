package eric.clapton.infrastructure.util.net;

import org.springframework.core.NestedCheckedException;

/**
 * 在操作 HTTP 资源时出现的异常。
 * 
 * @author xuw
 *
 */
public class WebException extends NestedCheckedException {
    private static final long serialVersionUID = -3069731375342835759L;

    public WebException(String message) {
        super(message);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }
}
