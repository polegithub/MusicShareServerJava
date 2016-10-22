package eric.clapton.infrastructure.util;

import java.math.BigDecimal;

public class NumberUtils {
    protected NumberUtils() {

    }

    public static final int strictCompare(Integer x, Integer y) {
        if (x == null) {
            return y == null ? 0 : 1;
        }
        return y == null ? 1 : x.compareTo(y);
    }

    public static final int strictCompare(Long x, Long y) {
        if (x == null) {
            return y == null ? 0 : 1;
        }
        return y == null ? 1 : x.compareTo(y);
    }

    public static final int looseCompare(Integer x, Integer y) {
        return (x == null ? Integer.valueOf(0) : x).compareTo(y == null ? Integer.valueOf(0) : y);
    }

    public static final int looseCompare(Long x, Long y) {
        return (x == null ? Long.valueOf(0) : x).compareTo(y == null ? Long.valueOf(0) : y);
    }

    public static double safeDoubleValue(Double d) {
        return d == null ? 0d : d.doubleValue();
    }

    public static double safeFloatValue(Float f) {
        return f == null ? 0f : f.floatValue();
    }

    public static boolean equals(Long n1, Long n2) {
        if (n1 == n2) {
            return true;
        }
        if (n1 != null) {
            return n1.equals(n2);
        }
        return false;
    }

    public static byte safeByteValue(Byte b) {
        return b == null ? (byte) 0 : b.byteValue();
    }

    public static short safeShortValue(Short s) {
        return s == null ? (short) 0 : s.shortValue();
    }

    public static int safeIntValue(Integer i) {
        return i == null ? 0 : i.intValue();
    }

    public static long safeLongValue(Long l) {
        return l == null ? 0L : l.intValue();
    }

    public static boolean isNullOrZero(Double d) {
        return d == null || d.doubleValue() == 0d;
    }

    public static boolean isNullOrZero(Float f) {
        return f == null || f.floatValue() == 0f;
    }

    public static boolean isNullOrZero(Byte b) {
        return b == null || b.byteValue() == (byte) 0;
    }

    public static boolean isNullOrZero(Short s) {
        return s == null || s.shortValue() == (short) 0;
    }

    public static boolean isNullOrZero(Integer i) {
        return i == null || i.intValue() == 0;
    }

    public static boolean isNullOrZero(Long l) {
        return l == null || l.longValue() == 0L;
    }

    public static boolean isNullOrZero(BigDecimal d) {
        return d == null || d.equals(BigDecimal.ZERO);
    }

    public static byte makeSafe(Byte value) {
        return makeSafe(value, (byte) 0);
    }

    public static BigDecimal makeSafe(BigDecimal value) {
        return makeSafe(value, BigDecimal.ZERO);
    }

    public static BigDecimal makeSafe(BigDecimal value, BigDecimal defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static byte makeSafe(Byte value, byte defaultValue) {
        return value == null ? defaultValue : value.byteValue();
    }

    public static short makeSafe(Short value) {
        return makeSafe(value, (short) 0);
    }

    public static short makeSafe(Short value, short defaultValue) {
        return value == null ? defaultValue : value.shortValue();
    }

    public static int makeSafe(Integer value) {
        return makeSafe(value, 0);
    }

    public static int makeSafe(Integer value, int defaultValue) {
        return value == null ? defaultValue : value.intValue();
    }

    public static long makeSafe(Long value) {
        return makeSafe(value, 0L);
    }

    public static long makeSafe(Long value, long defaultValue) {
        return value == null ? defaultValue : value.longValue();
    }

    public static double makeSafe(Double value) {
        return makeSafe(value, 0d);
    }

    public static float makeSafe(Float value, float defaultValue) {
        return value == null ? defaultValue : value.floatValue();
    }

    public static float makeSafe(Float value) {
        return makeSafe(value, 0f);
    }

    public static double makeSafe(Double value, double defaultValue) {
        return value == null ? defaultValue : value.doubleValue();
    }

    public static boolean safeEqual(Float f1, Float f2) {
        return safeFloatValue(f1) == safeFloatValue(f2);
    }

    public static boolean safeEqual(Double d1, Double d2) {
        return safeDoubleValue(d1) == safeDoubleValue(d2);
    }

    public static boolean safeEqual(Byte b1, Byte b2) {
        return safeByteValue(b1) == safeByteValue(b2);
    }

    public static boolean safeEqual(Short s1, Short s2) {
        return safeShortValue(s1) == safeShortValue(s2);
    }

    public static boolean safeEqual(Integer i1, Integer i2) {
        return safeIntValue(i1) == safeIntValue(i2);
    }

    public static boolean safeEqual(Long l1, Long l2) {
        return safeLongValue(l1) == safeLongValue(l2);
    }
}
