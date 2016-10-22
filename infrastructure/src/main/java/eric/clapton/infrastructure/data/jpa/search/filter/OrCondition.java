/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core OrCondition.java 2014-5-4 15:06:06 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search.filter;

import java.util.List;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * or 条件.
 * 
 * @author wuwei 2014-4-29
 */
public class OrCondition implements SearchFilter {

	/** The or filters. */
	private List<SearchFilter> orFilters = Lists.newArrayList();

	/**
	 * Instantiates a new or condition.
	 */
	OrCondition() {
	}

	/**
	 * Adds the.
	 * 
	 * @param filter
	 *            the filter
	 * @return the or condition
	 */
	public OrCondition add(SearchFilter filter) {
		this.orFilters.add(filter);
		return this;
	}

	/**
	 * Gets the or filters.
	 * 
	 * @return the or filters
	 */
	public List<SearchFilter> getOrFilters() {
		return orFilters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrCondition{" + orFilters + '}';
	}
}
