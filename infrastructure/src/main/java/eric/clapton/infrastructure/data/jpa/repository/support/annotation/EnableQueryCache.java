package eric.clapton.infrastructure.data.jpa.repository.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * 开启查询缓存.
 * 
 * @author wuwei 2014-4-29
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    /**
     * Value.
     * 
     * @return true, if successful
     */
    boolean value() default true;

}
