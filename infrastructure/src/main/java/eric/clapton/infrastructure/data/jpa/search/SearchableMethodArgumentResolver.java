/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core SearchableMethodArgumentResolver.java 2014-5-4 16:15:35 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * 请求查询参数字符串及分页/排序参数绑定到Searchable
 * 
 * <pre>
 * 查询参数格式如下：
 * 1.1、默认查询字符串
 * search.baseInfo.realname_like=zhang
 * search.age_lt=12
 * 排序及分页请参考 {@link PageableMethodArgumentResolver}
 * 1.2、控制器处理方法写法
 * public void test(Searchable searchable);
 * 
 * 2.1、自定义查询字符串
 * foo_search.baseInfo.realname_like=zhang
 * foo_search.age_lt=12
 * test_search.age_gt=12
 * 排序及分页请参考 {@link PageableMethodArgumentResolver}
 * 2.2、控制器处理方法写法
 * public void test(@Qualifier("foo") Searchable searchable1, @Qualifier("test") Searchable searchable2);
 * 
 * 3.1、禁用查询时分页及排序
 * public void test(@Search(page = false, sort = false) Searchable searchable);
 * </pre>
 * 
 * @author wuwei 2014-5-4
 */
public class SearchableMethodArgumentResolver extends BaseMethodArgumentResolver {

	/** The Constant DEFAULT_PAGEABLE_RESOLVER. */
	private static final PageableMethodArgumentResolver DEFAULT_PAGEABLE_RESOLVER = new PageableMethodArgumentResolver();

	/** The Constant DEFAULT_SEARCH_PREFIX. */
	private static final String DEFAULT_SEARCH_PREFIX = "search";

	/** The prefix. */
	private String prefix = DEFAULT_SEARCH_PREFIX;

	/**
	 * 设置查询参数前缀.
	 * 
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/** 分页参数解析器. */
	private PageableMethodArgumentResolver pageableMethodArgumentResolver = DEFAULT_PAGEABLE_RESOLVER;

	/**
	 * Sets the pageable method argument resolver.
	 * 
	 * @param pageableMethodArgumentResolver
	 *            the new pageable method argument resolver
	 */
	public void setPageableMethodArgumentResolver(PageableMethodArgumentResolver pageableMethodArgumentResolver) {
		this.pageableMethodArgumentResolver = pageableMethodArgumentResolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Searchable.class.isAssignableFrom(parameter.getParameterType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * resolveArgument(org.springframework.core.MethodParameter,
	 * org.springframework.web.method.support.ModelAndViewContainer,
	 * org.springframework.web.context.request.NativeWebRequest,
	 * org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		String searchPrefix = getSearchPrefix(parameter);

		Map<String, String[]> searcheableMap = getPrefixParameterMap(searchPrefix, webRequest, true);

		boolean hasCustomSearchFilter = searcheableMap.size() > 0;

		SearchableDefaults searchDefaults = getSearchableDefaults(parameter);

		boolean needMergeDefault = searchDefaults != null && searchDefaults.merge();

		Searchable searchable = null;
		// 自定义覆盖默认
		if (needMergeDefault || !hasCustomSearchFilter) {
			searchable = getDefaultFromAnnotation(searchDefaults);
		}
		if (hasCustomSearchFilter) {
			if (searchable == null) {
				searchable = Searchable.newSearchable();
			}
			for (String name : searcheableMap.keySet()) {
				String[] mapValues = filterSearchValues(searcheableMap.get(name));

				if (mapValues.length == 1) {
					if (name.endsWith("in")) {
						searchable.addSearchParam(name, StringUtils.split(mapValues[0], ",; "));
					} else {
						searchable.addSearchParam(name, mapValues[0]);
					}
				} else {
					searchable.addSearchParam(name, mapValues);
				}
			}
		}

		Pageable pageable = (Pageable) pageableMethodArgumentResolver.resolveArgument(parameter, mavContainer,
				webRequest, binderFactory);
		// 默认分页及排序
		if (searchDefaults == null) {
			searchable.setPage(pageable);
		}
		// needPage=true 分页及排序
		if (searchDefaults != null && searchDefaults.needPage()) {
			searchable.setPage(pageable);
		}
		// needPage=false needSort=true 不要分页，但排序
		if (searchDefaults != null && !searchDefaults.needPage() && searchDefaults.needSort()) {
			searchable.addSort(pageable.getSort());
		}

		return searchable;
	}

	/**
	 * Filter search values.
	 * 
	 * @param values
	 *            the values
	 * @return the string[]
	 */
	@SuppressWarnings("unchecked")
	private String[] filterSearchValues(String[] values) {
		List<String> result = Lists.newArrayList(CollectionUtils.arrayToList(values));
		for (int i = 0; i < result.size(); i++) {
			if (StringUtils.isBlank(result.get(i))) {
				result.remove(i);
			}
		}
		return result.toArray(values);
	}

	/**
	 * Gets the search prefix.
	 * 
	 * @param parameter
	 *            the parameter
	 * @return the search prefix
	 */
	private String getSearchPrefix(MethodParameter parameter) {
		Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

		if (qualifier != null) {
			return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(prefix).toString();
		}

		return prefix;
	}

	/**
	 * Gets the searchable defaults.
	 * 
	 * @param parameter
	 *            the parameter
	 * @return the searchable defaults
	 */
	private SearchableDefaults getSearchableDefaults(MethodParameter parameter) {
		// 首先从参数上找
		SearchableDefaults searchDefaults = parameter.getParameterAnnotation(SearchableDefaults.class);
		// 找不到从方法上找
		if (searchDefaults == null) {
			searchDefaults = parameter.getMethodAnnotation(SearchableDefaults.class);
		}
		return searchDefaults;
	}

	/**
	 * Gets the default from annotation.
	 * 
	 * @param searchableDefaults
	 *            the searchable defaults
	 * @return the default from annotation
	 */
	private Searchable getDefaultFromAnnotation(SearchableDefaults searchableDefaults) {

		Searchable searchable = defaultSearchable(searchableDefaults);
		if (searchable != null) {
			return searchable;
		}

		return Searchable.newSearchable();
	}

	/**
	 * Default searchable.
	 * 
	 * @param searchableDefaults
	 *            the searchable defaults
	 * @return the searchable
	 */
	private Searchable defaultSearchable(SearchableDefaults searchableDefaults) {

		if (searchableDefaults == null) {
			return null;
		}

		Searchable searchable = Searchable.newSearchable();
		for (String searchParam : searchableDefaults.value()) {
			String[] searchPair = searchParam.split("=");
			String paramName = searchPair[0];
			String paramValue = searchPair[1];
			if (paramName.endsWith("in")) {
				searchable.addSearchParam(paramName, StringUtils.split(paramValue, ",; "));
			} else {
				searchable.addSearchParam(paramName, paramValue);
			}
		}

		return searchable;
	}

}
