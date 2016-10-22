package eric.clapton.infrastructure.util;

public class BooleanUtils {
    protected BooleanUtils() {

    }

    public static final boolean makeSafe(Boolean b, boolean defaultValue) {
        return b == null ? defaultValue : b.booleanValue();
    }

    public static final boolean makeSafe(Boolean b) {
        return makeSafe(b, false);
    }
}
