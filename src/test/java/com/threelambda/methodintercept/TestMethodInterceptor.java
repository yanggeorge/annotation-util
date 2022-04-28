package com.threelambda.methodintercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/10/22
 */
public class TestMethodInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TestMethodInterceptor.class);

    private String order;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("TestMethodInterceptor enter.|order={}", order);
        Object proceed = invocation.proceed();
        logger.info("TestMethodInterceptor exit.|order={}", order);
        return proceed;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
