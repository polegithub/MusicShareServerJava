/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core Condition.java 2014-5-4 15:06:06 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;

import eric.clapton.infrastructure.data.jpa.search.SearchOperator;
import eric.clapton.infrastructure.data.jpa.search.exception.InvlidSearchOperatorException;
import eric.clapton.infrastructure.data.jpa.search.exception.SearchException;

public final class Condition implements SearchFilter {

    // 查询参数分隔符
    /** The Constant separator. */
    public static final String separator = "_";

    /** The key. */
    private String key;

    /** The search property. */
    private String searchProperty;

    /** The operator. */
    private SearchOperator operator;

    /** The value. */
    private Object value;

    /**
     * 根据查询key和值生成Condition.
     * 
     * @param key
     *            如 name_like
     * @param value
     *            the value
     * @return the condition
     * @throws SearchException
     *             the search exception
     */
    @SuppressWarnings("rawtypes")
    public static Condition newCondition(final String key, final Object value)
            throws SearchException {

        Assert.notNull(key, "Condition key must not null");

        String[] searchs = StringUtils.split(key, separator);

        if (searchs.length == 0) {
            throw new SearchException("Condition key format must be : property or property_op");
        }

        String searchProperty = searchs[0];

        SearchOperator operator = null;
        if (searchs.length == 1) {
            operator = SearchOperator.custom;
        } else {
            try {
                operator = SearchOperator.valueOf(searchs[1]);
            } catch (IllegalArgumentException e) {
                throw new InvlidSearchOperatorException(searchProperty, searchs[1]);
            }
        }

        boolean allowBlankValue = SearchOperator.isAllowBlankValue(operator);
        boolean isValueBlank = (value == null);
        isValueBlank = isValueBlank
                || (value instanceof String && StringUtils.isBlank((String) value));
        isValueBlank = isValueBlank || (value instanceof List && ((List) value).size() == 0);
        // 过滤掉空值，即不参与查询
        if (!allowBlankValue && isValueBlank) {
            return null;
        }
        if (searchs.length == 3) {
            if ("date".equalsIgnoreCase(searchs[2])) {
                try {
                    Date dateSearch = null;
                    Pattern pattern1 = Pattern
                            .compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])([-/.]?)(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0?[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2([-/.]?)29)$");
                    Pattern pattern2 = Pattern
                            .compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
                    if (pattern1.matcher(value.toString()).matches()) {
                        if (operator != null
                                && (operator == SearchOperator.gte || operator == SearchOperator.gt))
                            dateSearch = DateUtils.parseDate(value.toString() + " 00:00:00",
                                    "yyyy-MM-dd HH:mm:ss");
                        else if (operator != null
                                && (operator == SearchOperator.lte || operator == SearchOperator.lt))
                            dateSearch = DateUtils.parseDate(value.toString() + " 23:59:59",
                                    "yyyy-MM-dd HH:mm:ss");
                        else
                            dateSearch = DateUtils.parseDate(value.toString(), "yyyy-MM-dd");
                    } else if (pattern2.matcher(value.toString()).matches()) {
                        dateSearch = DateUtils.parseDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        throw new SearchException(
                                "date search type must be search.xxxx_op_date:'yyyy-MM-dd HH:mm:ss'");
                    }
                    Condition searchFilter = newCondition(searchProperty, operator, dateSearch);
                    return searchFilter;
                } catch (ParseException e) {
                    throw new SearchException(
                            "date search type must be search.xxxx_op_date:'yyyy-MM-dd HH:mm:ss'");
                }
            }
        }

        Condition searchFilter = newCondition(searchProperty, operator, value);

        return searchFilter;
    }

    /**
     * 根据查询属性、操作符和值生成Condition.
     * 
     * @param searchProperty
     *            the search property
     * @param operator
     *            the operator
     * @param value
     *            the value
     * @return the condition
     */
    static Condition newCondition(final String searchProperty, final SearchOperator operator,
            final Object value) {
        return new Condition(searchProperty, operator, value);
    }

    /**
     * Instantiates a new condition.
     * 
     * @param searchProperty
     *            属性名
     * @param operator
     *            操作
     * @param value
     *            值
     */
    private Condition(final String searchProperty, final SearchOperator operator, final Object value) {
        this.searchProperty = searchProperty;
        this.operator = operator;
        this.value = value;
        this.key = this.searchProperty + separator + this.operator;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;

    }

    /**
     * Gets the search property.
     * 
     * @return the search property
     */
    public String getSearchProperty() {
        return searchProperty;
    }

    /**
     * 获取 操作符.
     * 
     * @return the operator
     * @throws InvlidSearchOperatorException
     *             the invlid search operator exception
     */
    public SearchOperator getOperator() throws InvlidSearchOperatorException {
        return operator;
    }

    /**
     * 获取自定义查询使用的操作符 1、首先获取前台传的 2、返回空.
     * 
     * @return the operator str
     */
    public String getOperatorStr() {
        if (operator != null) {
            return operator.getSymbol();
        }
        return "";
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * Sets the operator.
     * 
     * @param operator
     *            the new operator
     */
    public void setOperator(final SearchOperator operator) {
        this.operator = operator;
    }

    /**
     * Sets the search property.
     * 
     * @param searchProperty
     *            the new search property
     */
    public void setSearchProperty(final String searchProperty) {
        this.searchProperty = searchProperty;
    }

    /**
     * 得到实体属性名.
     * 
     * @return the entity property
     */
    public String getEntityProperty() {
        return searchProperty;
    }

    /**
     * 是否是一元过滤 如is null is not null.
     * 
     * @return true, if is unary filter
     */
    public boolean isUnaryFilter() {
        String operatorStr = getOperator().getSymbol();
        return StringUtils.isNotEmpty(operatorStr) && operatorStr.startsWith("is");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Condition that = (Condition) o;

        if (key != null ? !key.equals(that.key) : that.key != null)
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Condition{" + "searchProperty='" + searchProperty + '\'' + ", operator=" + operator
                + ", value=" + value + '}';
    }
}
