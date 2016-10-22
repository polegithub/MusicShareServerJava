package eric.clapton.infrastructure.util.config;

import org.springframework.core.NestedRuntimeException;

public class ConfigurationErrorsException extends NestedRuntimeException {
    private static final long serialVersionUID = -889977734389327915L;

    public ConfigurationErrorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationErrorsException(String message) {
        super(message);
    }
}
