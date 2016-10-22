package eric.clapton.infrastructure.util.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.springframework.core.io.Resource;

import eric.clapton.infrastructure.util.StringUtils;

public class ConfigManagerImpl implements ConfigManager {
    private final Configuration configuration;

    public ConfigManagerImpl(Resource configDefinitionFile) throws IOException,
            ConfigurationException {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder(
                configDefinitionFile.getFile());

        configuration = builder.getConfiguration();

        if (LOGGER.isTraceEnabled()) {
            StringBuffer buffer = new StringBuffer(1024);
            buffer.append("Configuration loaded successfully. Available configuration keys are: ")
                    .append(StringUtils.NEW_LINE);

            Iterator<String> iterator = configuration.getKeys();
            while (iterator.hasNext()) {
                buffer.append(iterator.next()).append(StringUtils.NEW_LINE);
            }
            LOGGER.trace(buffer.toString());
        }
    }

    protected Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    @Override
    public byte getByte(String key) {
        return configuration.getByte(key);
    }

    @Override
    public byte getByte(String key, byte defaultValue) {
        return configuration.getByte(key, defaultValue);
    }

    @Override
    public Byte getByte(String key, Byte defaultValue) {
        return configuration.getByte(key, defaultValue);
    }

    @Override
    public double getDouble(String key) {
        return configuration.getDouble(key);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return configuration.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return configuration.getFloat(key, defaultValue);
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return configuration.getFloat(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return configuration.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return configuration.getInt(key, defaultValue);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        return configuration.getInteger(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return configuration.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return configuration.getLong(key, defaultValue);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return configuration.getLong(key, defaultValue);
    }

    @Override
    public short getShort(String key) {
        return configuration.getShort(key);
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return configuration.getShort(key, defaultValue);
    }

    @Override
    public Short getShort(String key, Short defaultValue) {
        return configuration.getShort(key, defaultValue);
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return configuration.getBigDecimal(key);
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return configuration.getBigDecimal(key, defaultValue);
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return configuration.getBigInteger(key);
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return configuration.getBigInteger(key, defaultValue);
    }

    @Override
    public String getString(String key) {
        return configuration.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    @Override
    public String[] getStringArray(String key) {
        return configuration.getStringArray(key);
    }

    @Override
    public List<Object> getList(String key) {
        return configuration.getList(key);
    }

    @Override
    public List<Object> getList(String key, List<?> defaultValue) {
        return configuration.getList(key, defaultValue);
    }
}
