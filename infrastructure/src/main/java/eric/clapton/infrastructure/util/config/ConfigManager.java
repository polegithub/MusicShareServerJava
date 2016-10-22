package eric.clapton.infrastructure.util.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表示一个配置管理器。
 * 
 * @author xuw
 *
 */
public interface ConfigManager {
    public final static Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    Boolean getBoolean(String key, Boolean defaultValue);

    byte getByte(String key);

    byte getByte(String key, byte defaultValue);

    Byte getByte(String key, Byte defaultValue);

    double getDouble(String key);

    double getDouble(String key, double defaultValue);

    Double getDouble(String key, Double defaultValue);

    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    Float getFloat(String key, Float defaultValue);

    int getInt(String key);

    Integer getInteger(String key, Integer defaultValue);

    int getInt(String key, int defaultValue);

    long getLong(String key);

    long getLong(String key, long defaultValue);

    short getShort(String key);

    short getShort(String key, short defaultValue);

    Long getLong(String key, Long defaultValue);

    Short getShort(String key, Short defaultValue);

    BigDecimal getBigDecimal(String key);

    BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

    BigInteger getBigInteger(String key);

    BigInteger getBigInteger(String key, BigInteger defaultValue);

    String getString(String key);

    String getString(String key, String defaultValue);

    String[] getStringArray(String key);

    List<Object> getList(String key);

    List<Object> getList(String key, List<?> defaultValue);
}
