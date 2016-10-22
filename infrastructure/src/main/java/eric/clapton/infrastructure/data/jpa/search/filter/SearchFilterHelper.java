/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core SearchFilterHelper.java 2014-5-4 15:06:06 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import eric.clapton.infrastructure.data.jpa.search.SearchOperator;
import eric.clapton.infrastructure.data.jpa.search.exception.SearchException;

/**
 * The Class SearchFilterHelper.
 * 
 * @author wuwei 2014-4-29
 */
public final class SearchFilterHelper {

    /**
     * 根据查询key和值生成Condition.
     * 
     * @param key
     *            如 name_like
     * @param value
     *            the value
     * @return the search filter
     * @throws SearchException
     *             the search exception
     */
    public static SearchFilter newCondition(final String key, final Object value)
            throws SearchException {

        return Condition.newCondition(key, value);
    }

    public static List<SearchFilter> newConditionList(final String key,
            final Object value) throws SearchException {
        List<SearchFilter> filterList = new ArrayList<SearchFilter>();
        for (String spiltedValue : value.toString().split(",")) {
            filterList.add(Condition.newCondition(key, spiltedValue));
        }
        return filterList;
    }

    /**
     * 根据查询属性、操作符和值生成Condition.
     * 
     * @param expression
     *            the search property
     * @param operator
     *            the operator
     * @param value
     *            the value
     * @return the search filter
     */
    public static SearchFilter newCondition(final String expression,
            final SearchOperator operator, final Object value) {
        return Condition.newCondition(expression, operator, value);
    }

    public static final SearchFilter equal(final String expression,
            final Object value) {
        return newCondition(expression, SearchOperator.eq, value);
    }

    public static final SearchFilter notEqual(final String expression,
            final Object value) {
        return newCondition(expression, SearchOperator.ne, value);
    }

    public static final SearchFilter greaterThan(final String expression,
            final Object value) {
        return newCondition(expression, SearchOperator.gt, value);
    }

    public static final SearchFilter lowerThan(final String expression,
            final Object value) {
        return newCondition(expression, SearchOperator.lt, value);
    }

    public static final SearchFilter greaterThanOrEqual(
            final String expression, final Object value) {
        return newCondition(expression, SearchOperator.gte, value);
    }

    public static final SearchFilter lowerThanOrEqual(final String expression,
            final Object value) {
        return newCondition(expression, SearchOperator.lte, value);
    }

    public static final SearchFilter isNull(final String expression) {
        return newCondition(expression, SearchOperator.isNull, null);
    }

    public static final SearchFilter isNotNull(final String expression) {
        return newCondition(expression, SearchOperator.isNotNull, null);
    }

    public static final SearchFilter startsWith(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.prefixLike, value);
    }

    public static final SearchFilter notStartsWith(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.prefixNotLike, value);
    }

    public static final SearchFilter notEndsWith(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.suffixNotLike, value);
    }

    public static final SearchFilter endsWith(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.suffixLike, value);
    }

    public final static SearchFilter contains(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.like, value);
    }

    public final static SearchFilter notContains(final String expression,
            String value) {
        return newCondition(expression, SearchOperator.notLike, value);
    }

    public final SearchFilter in(final String expression, Object... values) {
        return newCondition(expression, SearchOperator.in, values);
    }

    /**
     * 拼or条件.
     * 
     * @param first
     *            the first
     * @param others
     *            the others
     * @return the search filter
     */
    public static SearchFilter or(SearchFilter first, SearchFilter... others) {
        OrCondition orCondition = new OrCondition();
        orCondition.getOrFilters().add(first);
        if (ArrayUtils.isNotEmpty(others)) {
            orCondition.getOrFilters().addAll(Arrays.asList(others));
        }
        return orCondition;
    }

    public static SearchFilter or(List<SearchFilter> filters) {
        if (filters == null || filters.size() <= 0) {
            return null;
        }
        OrCondition orCondition = new OrCondition();
        orCondition.getOrFilters().addAll(filters);
        return orCondition;
    }

    /**
     * 拼and条件.
     * 
     * @param first
     *            the first
     * @param others
     *            the others
     * @return the search filter
     */
    public static SearchFilter and(SearchFilter first, SearchFilter... others) {
        AndCondition andCondition = new AndCondition();
        andCondition.getAndFilters().add(first);
        if (ArrayUtils.isNotEmpty(others)) {
            andCondition.getAndFilters().addAll(Arrays.asList(others));
        }
        return andCondition;
    }

}
