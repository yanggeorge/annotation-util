package com.threelambda.log;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author yangming 2018/9/30
 */
public abstract class AbstractBaseAdvice implements MethodInterceptor {
    protected Class<?> targetClass;
    protected List<String> annotatedWithCatchMeMethods;

    public List<String> getAnnotatedWithCatchMeMethods() {
        return annotatedWithCatchMeMethods;
    }

    public void setAnnotatedWithCatchMeMethods(List<String> annotatedWithCatchMeMethods) {
        this.annotatedWithCatchMeMethods = annotatedWithCatchMeMethods;
    }

    public Object getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract DefaultPointcutAdvisor makeAdvisor(Class<?> targetClass, List<String> methodsWithAnnotation);
}
