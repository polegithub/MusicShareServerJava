package eric.clapton.infrastructure.util;

import org.springframework.core.NestedRuntimeException;

public class ConversionException extends NestedRuntimeException {
    private static final long serialVersionUID = -6437699089957747397L;

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

}
