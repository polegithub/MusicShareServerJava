/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core QueryJoin.java 2014-5-4 15:05:29 wuwei$$
 */

package eric.clapton.infrastructure.data.jpa.repository.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.criteria.JoinType;

// TODO: Auto-generated Javadoc
/**
 * The Interface QueryJoin.
 * 
 * @author wuwei 2014-5-4
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryJoin {

    /**
     * 连接的名字.
     * 
     * @return the string
     */
    String property();

    /**
     * Join type.
     * 
     * @return the join type
     */
    JoinType joinType();

}
