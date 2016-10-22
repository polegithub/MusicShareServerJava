package eric.clapton.infrastructure.service.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对方法执行时出现异常时进行重试。
 * 
 * @author xuw
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface RetryOn {
    /**
     * 对出现在这些列表中的异常进行重试。如果不指定，则对所有引发的异常进行重试。
     *
     * @return
     */
    Class<? extends Throwable>[] value();

    /**
     * 重试方法的次数。默认为 3。
     *
     * @return
     */
    int times() default 3;

    /**
     * 如果方法在事务中执行，则抛出错误。默认为 <code>true</code>。
     *
     * @return
     */
    boolean failIfInTransaction() default true;
}
