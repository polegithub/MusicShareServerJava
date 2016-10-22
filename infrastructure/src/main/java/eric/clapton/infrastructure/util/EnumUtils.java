package eric.clapton.infrastructure.util;

public class EnumUtils {
	protected EnumUtils() {

	}

	public final static int safeOrdinal(Enum<?> e) {
		return e == null ? 0 : e.ordinal();
	}
}
