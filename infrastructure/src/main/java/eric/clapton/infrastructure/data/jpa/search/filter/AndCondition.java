/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core AndCondition.java 2014-5-4 15:06:06 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search.filter;

import java.util.List;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * and 条件.
 * 
 * @author wuwei 2014-4-29
 */
public class AndCondition implements SearchFilter {

	/** The and filters. */
	private List<SearchFilter> andFilters = Lists.newArrayList();

	/**
	 * Instantiates a new and condition.
	 */
	AndCondition() {
	}

	/**
	 * Adds the.
	 * 
	 * @param filter
	 *            the filter
	 * @return the and condition
	 */
	public AndCondition add(SearchFilter filter) {
		this.andFilters.add(filter);
		return this;
	}

	/**
	 * Gets the and filters.
	 * 
	 * @return the and filters
	 */
	public List<SearchFilter> getAndFilters() {
		return andFilters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AndCondition{" + andFilters + '}';
	}
}
