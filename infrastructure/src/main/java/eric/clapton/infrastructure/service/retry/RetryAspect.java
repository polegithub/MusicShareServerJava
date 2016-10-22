package eric.clapton.infrastructure.service.retry;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.util.JavaScriptUtils;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.util.StringUtils;

/**
 * 实现一个对任何方法进行重试的切面。
 * 
 * @author xuw
 *
 */
@Component
@Aspect
public class RetryAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryAspect.class);

    public RetryAspect() {
    }

    @Around("@annotation(eric.clapton.infrastructure.service.retry.RetryOn)")
    public Object retry(ProceedingJoinPoint pjp) throws Throwable {
        return proceed(pjp, getRetryOnAnnotation(pjp));
    }

    private Object proceed(ProceedingJoinPoint pjp, RetryOn retryOnAnnotation) throws Throwable {
        if (retryOnAnnotation == null) {
            return pjp.proceed();
        }

        int times = retryOnAnnotation.times();
        Class<? extends Throwable>[] retryOn = retryOnAnnotation.value();

        if (retryOnAnnotation.failIfInTransaction()
                && TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new IllegalTransactionStateException("无法在现有的事务内重试操作。");
        }

        return tryProceeding(pjp, times, retryOn);
    }

    private Object tryProceeding(ProceedingJoinPoint pjp, int times,
            Class<? extends Throwable>[] retryOn) throws Throwable {
        // 开始重试
        String methodInfo = null;

        try {
            if (LOGGER.isTraceEnabled()) {
                methodInfo = getMethodInfo(pjp);
                LOGGER.trace("准备执行/重试方法。剩余可重试次数 {}。{}{}", times, StringUtils.NEW_LINE, methodInfo);
            }

            Object r = pjp.proceed();

            if (LOGGER.isTraceEnabled()) {
                if (methodInfo == null) {
                    methodInfo = getMethodInfo(pjp);
                }
                LOGGER.trace("方法调用成功。 {}{}", StringUtils.NEW_LINE, methodInfo);
            }

            return r;
        } catch (Throwable t) {
            if (shouldRetry(t, retryOn) && times-- > 0) {
                if (LOGGER.isInfoEnabled()) {
                    if (methodInfo == null) {
                        methodInfo = getMethodInfo(pjp);
                    }
                    LOGGER.info("方法调用失败，出现了异常 {} 需要重试，剩余重试次数 {}。{}{}", t.getClass().getName(),
                            times, StringUtils.NEW_LINE, methodInfo);
                }

                return tryProceeding(pjp, times, retryOn);
            }
            if (LOGGER.isWarnEnabled()) {
                if (methodInfo == null) {
                    methodInfo = getMethodInfo(pjp);
                }
                LOGGER.warn("方法调用重试全部失败。{}{}", methodInfo, StringUtils.NEW_LINE, times);
            }
            throw t;
        }
    }

    private static boolean shouldRetry(Throwable throwable, Class<? extends Throwable>[] retryOn) {
        if (retryOn == null || retryOn.length == 0) {
            return true;
        }

        Throwable[] causes = ExceptionUtils.getThrowables(throwable);
        for (Throwable cause : causes) {
            for (Class<? extends Throwable> retryThrowable : retryOn) {
                if (retryThrowable.isAssignableFrom(cause.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

    private static final String getMethodInfo(ProceedingJoinPoint pjp) {
        StringBuilder methodInfo = new StringBuilder();
        Method method = getMethod(pjp);

        methodInfo.append("方法：").append(method.toString());
        methodInfo.append(StringUtils.NEW_LINE);

        Object[] args = pjp.getArgs();
        if (args.length == 0) {
            methodInfo.append("参数：(无)");
        } else {
            methodInfo.append("参数列表：");
            for (int i = 0; i < args.length; i++) {
                methodInfo.append(StringUtils.NEW_LINE);

                methodInfo.append("  参数 ").append(i + 1).append("：");
                toString(methodInfo, args[i]);
            }
        }

        return methodInfo.toString();
    }

    private static final StringBuilder toString(StringBuilder b, Object v) {
        if (v == null) {
            return b.append("null");
        }

        Class<?> c = v.getClass();

        try {
            if (String.class.equals(c)) {
                return b.append('"').append(JavaScriptUtils.javaScriptEscape((String) v))
                        .append('"');
            } else if (BaseEntity.class.isAssignableFrom(c)) {
                return b.append("{").append(c.getName()).append(", ID: ")
                        .append(((BaseEntity) v).getId()).append("}");
            } else if (c.isArray()) {
                b.append('[');
                Object[] a = (Object[]) v;
                for (int i = 0; i < a.length; i++) {
                    if (i > 0) {
                        b.append(", ");
                    }

                    b = toString(b, a[i]);
                }

                return b.append(']');
            } else if (v instanceof Iterable<?> || v instanceof Iterator<?>) {
                b.append('[');

                Iterator<?> iterator = v instanceof Iterable<?> ? ((Iterable<?>) v).iterator()
                        : (Iterator<?>) v;

                boolean visit = false;
                while (iterator.hasNext()) {
                    if (visit) {
                        b.append(", ");
                    }
                    visit = true;

                    toString(b, iterator.next());
                }
                return b.append(']');
            }
            return b.append(v);
        } catch (Throwable t) {
            return b.append(c.getName());
        }
    }

    private RetryOn getRetryOnAnnotation(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Method method = getMethod(pjp);
        RetryOn annotation = AnnotationUtils.findAnnotation(method, RetryOn.class);

        if (annotation == null) {
            Object[] args = pjp.getArgs();
            Class<?>[] argClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argClasses[i] = args[i].getClass();
            }
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argClasses);
            annotation = AnnotationUtils.findAnnotation(method, RetryOn.class);
        }

        if (annotation != null) {
            if (annotation.times() <= 0) {
                LOGGER.warn("方法 {} 上的 {} 注解无效。times 属性应为正数。", method, RetryOn.class);

                annotation = null;
            }
        }
        return annotation;
    }
}
