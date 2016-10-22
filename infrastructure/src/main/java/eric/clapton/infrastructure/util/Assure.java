package eric.clapton.infrastructure.util;

import java.util.Collection;
import java.util.Locale;

public class Assure {
    protected Assure() {

    }

    public static final void isNotNull(Object value, String paramName) {
        if (value == null) {
            String message = paramName == null ? "Value cannot be null." : String.format(
                    Locale.ENGLISH, "Argument '%s' cannot be null.", paramName);
            throw new IllegalArgumentException(message);
        }
    }

    public static String isNotBlank(String s, String paramName) {
        String trimmed;

        if (StringUtils.isNullOrEmpty(s) || (trimmed = s.trim()).length() == 0) {
            String message = paramName == null ? "String cannot be blank." : String.format(
                    Locale.ENGLISH, "String cannot be blank. Parameter name: %s.", paramName);
            throw new IllegalArgumentException(message);
        }
        return trimmed;
    }

    public static void isNotEmpty(String s, String paramName) {
        if (StringUtils.isNullOrEmpty(s)) {
            String message = paramName == null ? "String cannot be null or empty." : String.format(
                    Locale.ENGLISH, "String cannot be null or empty. Parameter name: %s.",
                    paramName);
            throw new IllegalArgumentException(message);
        }
    }

    public static void any(Collection<?> collection, String paramName) {
        if (collection == null || collection.isEmpty()) {
            String message = paramName == null ? "Collection must contain at least one element."
                    : String.format(Locale.ENGLISH,
                            "Collection must contain at least one element. Parameter name: %s.",
                            paramName);
            throw new IllegalArgumentException(message);
        }
    }

}
