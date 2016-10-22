package eric.clapton.infrastructure.util;

import java.util.Locale;

import eric.clapton.infrastructure.Convertible;

public class Convert {
    protected Convert() {

    }

    /**
     * 尝试将对象装换为 {@link Long} 类型。
     * 
     * @param o
     *            要转换的对象。
     * @return <ul>
     *         <li>如果 <code>o</code> 为 <code>null</code>，则为 <code>0</code>。</li>
     *         <li>如果 <code>o</code> 为 {@link Number} 类型的对象，则调用并返回
     *         <code>{@linkplain Number#longValue()}</code> 方法的结果。</li>
     *         <li>如果 <code>o</code> 为字符串，则将该字符串解析为等效的 {@link Long} 类型对象。</li>
     *         <li>如果 <code>o</code> 实现 {@link Convertible} 接口，则调用并返回该对象上
     *         {@link Convertible#toLong() toLong()} 方法的结果。</li>
     *         <li>否则抛出 {@link ClassCastException} 异常。</li>
     *         </ul>
     * @throws ClassCastException
     *             无法将指定的对象转换为 {@link Long} 类型。
     */
    public static Long toLong(Object o) {
        if (o == null) {
            return 0L;
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        if (o instanceof String) {
            String s = StringUtils.makeSafe((String) o, true);
            if (s.isEmpty()) {
                return 0L;
            }
            return Long.valueOf(s);
        }
        if (o instanceof Convertible) {
            return ((Convertible) o).toLong();
        }

        throw makeConversionException(o, Long.class);
    }

    public static Integer toInteger(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        if (o instanceof String) {
            String s = StringUtils.makeSafe((String) o, true);
            if (s.isEmpty()) {
                return 0;
            }
            return Integer.valueOf(s);
        }
        if (o instanceof Convertible) {
            return ((Convertible) o).toInteger();
        }

        throw makeConversionException(o, Integer.class);
    }

    public static Double toDouble(Object o) {
        if (o == null) {
            return 0d;
        }
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        if (o instanceof String) {
            String s = StringUtils.makeSafe((String) o, true);
            if (s.isEmpty()) {
                return 0d;
            }
            return Double.valueOf(s);
        }
        if (o instanceof Convertible) {
            return ((Convertible) o).toDouble();
        }

        throw makeConversionException(o, Double.class);
    }

    private static ConversionException makeConversionException(Object o, Class<?> targetType) {
        return new ConversionException(String.format(Locale.CHINA, "无法将类型为“%s”的对象装换为类型“%s”。",
                o.getClass(), targetType));
    }
}
