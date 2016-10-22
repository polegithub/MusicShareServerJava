package eric.clapton.infrastructure.data.jpa.search;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import eric.clapton.infrastructure.data.jpa.search.exception.InvalidSearchPropertyException;
import eric.clapton.infrastructure.data.jpa.search.exception.InvalidSearchValueException;
import eric.clapton.infrastructure.data.jpa.search.exception.SearchException;
import eric.clapton.infrastructure.data.jpa.search.filter.AndCondition;
import eric.clapton.infrastructure.data.jpa.search.filter.Condition;
import eric.clapton.infrastructure.data.jpa.search.filter.OrCondition;
import eric.clapton.infrastructure.data.jpa.search.filter.SearchFilter;
import eric.clapton.infrastructure.util.SpringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchableConvertUtils.
 * 
 * @author wuwei 2014-4-29
 */
public final class SearchableConvertUtils {

	/** The conversion service. */
	private static volatile ConversionService conversionService;

	/**
	 * 设置用于类型转换的conversionService 把如下代码放入spring配置文件即可 <bean class=
	 * "org.springframework.beans.factory.config.MethodInvokingFactoryBean">
	 * <property name="staticMethod"
	 * value=SearchableConvertUtils.setConversionService" />
	 * <property name="arguments" ref="conversionService"/> </bean>
	 * 
	 * @param conversionService
	 *            the new conversion service
	 */
	public static void setConversionService(ConversionService conversionService) {
		SearchableConvertUtils.conversionService = conversionService;
	}

	public static ConversionService getConversionService() {
		if (conversionService == null) {
			synchronized (SearchableConvertUtils.class) {
				if (conversionService == null) {
					try {
						conversionService = SpringUtils.getBean(ConversionService.class);
					} catch (Exception e) {
						throw new SearchException(
								"conversionService is null, " + "search param convert must use conversionService. "
										+ "please see [com.sishuok.es.common.entity.search.utils."
										+ "SearchableConvertUtils#setConversionService]");
					}
				}
			}
		}
		return conversionService;
	}

	public static <T> void convertSearchValueToEntityValue(final Searchable search, final Class<T> entityClass) {

		if (search.isConverted()) {
			return;
		}

		Collection<SearchFilter> searchFilters = search.getSearchFilters();
		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(entityClass);
		beanWrapper.setAutoGrowNestedPaths(true);
		beanWrapper.setConversionService(getConversionService());

		for (SearchFilter searchFilter : searchFilters) {
			convertSearchValueToEntityValue(beanWrapper, searchFilter);

		}
	}

	private static void convertSearchValueToEntityValue(BeanWrapperImpl beanWrapper, SearchFilter searchFilter) {
		if (searchFilter instanceof Condition) {
			Condition condition = (Condition) searchFilter;
			convert(beanWrapper, condition);
			return;
		}

		if (searchFilter instanceof OrCondition) {
			for (SearchFilter orFilter : ((OrCondition) searchFilter).getOrFilters()) {
				convertSearchValueToEntityValue(beanWrapper, orFilter);
			}
			return;
		}

		if (searchFilter instanceof AndCondition) {
			for (SearchFilter andFilter : ((AndCondition) searchFilter).getAndFilters()) {
				convertSearchValueToEntityValue(beanWrapper, andFilter);
			}
			return;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void convert(BeanWrapperImpl beanWrapper, Condition condition) {
		String searchProperty = condition.getSearchProperty();

		// 自定义的也不转换
		if (condition.getOperator() == SearchOperator.custom) {
			return;
		}

		// 一元运算符不需要计算
		if (condition.isUnaryFilter()) {
			return;
		}

		String entityProperty = condition.getEntityProperty();

		Object value = condition.getValue();

		Object newValue = null;
		boolean isCollection = value instanceof Collection;
		boolean isArray = value != null && value.getClass().isArray();
		if (isCollection || isArray) {
			List<Object> list = Lists.newArrayList();
			if (isCollection) {
				list.addAll((Collection) value);
			} else {
				list = Lists.newArrayList(CollectionUtils.arrayToList(value));
			}
			int length = list.size();
			for (int i = 0; i < length; i++) {
				list.set(i, getConvertedValue(beanWrapper, searchProperty, entityProperty, list.get(i)));
			}
			newValue = list;
		} else {
			newValue = getConvertedValue(beanWrapper, searchProperty, entityProperty, value);
		}
		condition.setValue(newValue);
	}

	private static Object getConvertedValue(final BeanWrapperImpl beanWrapper, final String searchProperty,
			final String entityProperty, final Object value) {

		Object newValue;
		try {
			beanWrapper.setPropertyValue(entityProperty, value);
			newValue = beanWrapper.getPropertyValue(entityProperty);
		} catch (InvalidPropertyException e) {
			throw new InvalidSearchPropertyException(searchProperty, entityProperty, e);
		} catch (Exception e) {
			throw new InvalidSearchValueException(searchProperty, entityProperty, value, e);
		}

		return newValue;
	}

}
