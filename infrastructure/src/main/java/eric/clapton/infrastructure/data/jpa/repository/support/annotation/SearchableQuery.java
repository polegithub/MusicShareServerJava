/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core SearchableQuery.java 2014-5-4 15:05:35 wuwei$$
 */

package eric.clapton.infrastructure.data.jpa.repository.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eric.clapton.infrastructure.data.jpa.repository.callback.SearchCallback;

// TODO: Auto-generated Javadoc
/**
 * 覆盖默认的根据条件查询数据.
 * 
 * @author wuwei 2014-4-29
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchableQuery {

    /**
     * 覆盖默认的查询所有ql.
     * 
     * @return the string
     */
    String findAllQuery() default "";

    /**
     * 覆盖默认的统计所有ql.
     * 
     * @return the string
     */
    String countAllQuery() default "";

    /**
     * 给ql拼条件及赋值的回调类型.
     * 
     */
    Class<? extends SearchCallback> callbackClass() default SearchCallback.class;

    /**
     * Joins.
     * 
     * @return the query join[]
     */
    QueryJoin[] joins() default {};

}
