package eric.clapton.infrastructure.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class ConfigUtils {
    private static Configuration configuration;

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

    private ConfigUtils(Resource configDefinitionFile) throws ConfigurationException, IOException {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder(
                configDefinitionFile.getFile());

        ConfigUtils.configuration = builder.getConfiguration();

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

    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Get a boolean associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated boolean.
     */
    public static boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    /**
     * Get a boolean associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated boolean.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Boolean.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    /**
     * Get a {@link Boolean} associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated boolean if key is found and has valid format,
     *         default value otherwise.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Boolean.
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    /**
     * Get a byte associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated byte.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Byte.
     */
    public static byte getByte(String key) {
        return configuration.getByte(key);
    }

    /**
     * Get a byte associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated byte.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Byte.
     */
    public static byte getByte(String key, byte defaultValue) {
        return configuration.getByte(key, defaultValue);
    }

    /**
     * Get a {@link Byte} associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated byte if key is found and has valid format, default
     *         value otherwise.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Byte.
     */
    public static Byte getByte(String key, Byte defaultValue) {
        return configuration.getByte(key, defaultValue);
    }

    /**
     * Get a double associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated double.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Double.
     */
    public static double getDouble(String key) {
        return configuration.getDouble(key);
    }

    /**
     * Get a double associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated double.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Double.
     */
    public static double getDouble(String key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }

    /**
     * Get a {@link Double} associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated double if key is found and has valid format,
     *         default value otherwise.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Double.
     */
    public static Double getDouble(String key, Double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }

    /**
     * Get a float associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated float.
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Float.
     */
    public static float getFloat(String key) {
        return configuration.getFloat(key);
    }

    /**
     * Get a float associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated float.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a Float.
     */
    public static float getFloat(String key, float defaultValue) {
        return configuration.getFloat(key, defaultValue);
    }

    /**
     * Get a {@link Float} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated float if key is found and has valid format,
     *         default value otherwise.
     *
     */
    public static Float getFloat(String key, Float defaultValue) {
        return configuration.getFloat(key, defaultValue);
    }

    /**
     * Get a int associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated int.
     *
     */
    public static int getInt(String key) {
        return configuration.getInt(key);
    }

    /**
     * Get a int associated with the given configuration key. If the key doesn't
     * map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated int.
     * 
     */
    public static int getInt(String key, int defaultValue) {
        return configuration.getInt(key, defaultValue);
    }

    /**
     * Get an {@link Integer} associated with the given configuration key. If
     * the key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated int if key is found and has valid format, default
     *         value otherwise.
     *
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        return configuration.getInteger(key, defaultValue);
    }

    /**
     * Get a long associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated long.
     * 
     */
    public static long getLong(String key) {
        return configuration.getLong(key);
    }

    /**
     * Get a long associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated long.
     *
     */
    public static long getLong(String key, long defaultValue) {
        return configuration.getLong(key, defaultValue);
    }

    /**
     * Get a {@link Long} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated long if key is found and has valid format, default
     *         value otherwise.
     *
     */
    public static Long getLong(String key, Long defaultValue) {
        return configuration.getLong(key, defaultValue);
    }

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated short.
     *
     */
    public static short getShort(String key) {
        return configuration.getShort(key);
    }

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated short.
     *
     */
    public static short getShort(String key, short defaultValue) {
        return configuration.getShort(key, defaultValue);
    }

    /**
     * Get a {@link Short} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated short if key is found and has valid format,
     *         default value otherwise.
     *
     */
    public static Short getShort(String key, Short defaultValue) {
        return configuration.getShort(key, defaultValue);
    }

    /**
     * Get a {@link BigDecimal} associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated BigDecimal if key is found and has valid format
     */
    public static BigDecimal getBigDecimal(String key) {
        return configuration.getBigDecimal(key);
    }

    /**
     * Get a {@link BigDecimal} associated with the given configuration key. If
     * the key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     *
     * @return The associated BigDecimal if key is found and has valid format,
     *         default value otherwise.
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return configuration.getBigDecimal(key, defaultValue);
    }

    /**
     * Get a {@link BigInteger} associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     *
     * @return The associated BigInteger if key is found and has valid format
     */
    public static BigInteger getBigInteger(String key) {
        return configuration.getBigInteger(key);
    }

    /**
     * Get a {@link BigInteger} associated with the given configuration key. If
     * the key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     *
     * @return The associated BigInteger if key is found and has valid format,
     *         default value otherwise.
     */
    public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return configuration.getBigInteger(key, defaultValue);
    }

    /**
     * Get a string associated with the given configuration key.
     *
     * @param key
     *            The configuration key.
     * @return The associated string.
     *
     */
    public static String getString(String key) {
        return configuration.getString(key);
    }

    /**
     * Get a string associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated string if key is found and has valid format,
     *         default value otherwise.
     *
     * @throws ConversionException
     *             is thrown if the key maps to an object that is not a String.
     */
    public static String getString(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    /**
     * Get an array of strings associated with the given configuration key. If
     * the key doesn't map to an existing object an empty array is returned
     *
     * @param key
     *            The configuration key.
     * @return The associated string array if key is found.
     */
    public static String[] getStringArray(String key) {
        return configuration.getStringArray(key);
    }

    /**
     * Get a List of strings associated with the given configuration key. If the
     * key doesn't map to an existing object an empty List is returned.
     *
     * @param key
     *            The configuration key.
     * @return The associated List.
     */
    public static List<Object> getList(String key) {
        return configuration.getList(key);
    }

    /**
     * Get a List of strings associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key
     *            The configuration key.
     * @param defaultValue
     *            The default value.
     * @return The associated List of strings.
     */
    public static List<Object> getList(String key, List<?> defaultValue) {
        return configuration.getList(key, defaultValue);
    }
}
