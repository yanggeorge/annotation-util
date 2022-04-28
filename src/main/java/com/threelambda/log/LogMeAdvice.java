package com.threelambda.log;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import com.alibaba.fastjson.JSON;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * @author yangming 2018/9/26
 */
public class LogMeAdvice extends AbstractBaseAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LogMeAdvice.class);

    private Boolean hasPointCut = false;

    public LogMeAdvice() {
    }

    public LogMeAdvice(Class<?> targetClass, Boolean hasPointCut) {
        this.targetClass = targetClass;
        this.hasPointCut = hasPointCut;
    }

    private LogMeAdvice(Class<?> targetClass, List<String> annotatedWithLogMeMethods) {
        this.targetClass = targetClass;
        this.annotatedWithCatchMeMethods = annotatedWithLogMeMethods;
    }

    @Override
    public DefaultPointcutAdvisor makeAdvisor(Class<?> targetClass, List<String> methodsWithAnnotation) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(new LogMeAdvice(targetClass, true));
        NameMatchMethodPointcut pointCut = new NameMatchMethodPointcut();
        for (String methodName : methodsWithAnnotation) {
            pointCut.addMethodName(methodName);
        }
        advisor.setPointcut(pointCut);
        advisor.setOrder(AnnotationOrderEnum.LogMe.getOrder());
        return advisor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        //todo 应该改成方法签名过滤
        if (hasPointCut || annotatedWithCatchMeMethods.contains(method.getName())) {
            //如果该方法有LogMe注解
            String inputInfo = "";
            String fullMethodName = "";
            try {
                Object[] arguments = methodInvocation.getArguments();
                inputInfo = getInputInfo(arguments, method);
                fullMethodName = getCurrentInvocationMethodFullName(method);
            } catch (Exception e) {
                logger.warn("getInputInfo has something wrong.", e);
            }

            logger.info("enter={}|{}", fullMethodName, inputInfo);
            Object proceed = methodInvocation.proceed();
            logger.info("exit={}", fullMethodName);
            return proceed;
        } else {
            return methodInvocation.proceed();
        }
    }

    /**
     * 获取拦截的方法的全名
     *
     * @param method
     * @return
     */
    private String getCurrentInvocationMethodFullName(Method method) {
        return targetClass.getName() + "." + method.getName();
    }

    /**
     * 获取入参信息
     *
     * @param arguments 实参
     * @param method
     * @return
     */
    private String getInputInfo(Object[] arguments, Method method) {
        StringBuilder inputInfoBuilder = new StringBuilder();

        if (arguments == null) {
            return inputInfoBuilder.toString();
        }

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();

        if (parameters == null) {
            return inputInfoBuilder.toString();
        }

        if (parameters.length < arguments.length) {
            throw new RuntimeException("arguments.length larger than parameters.length.");
        }

        LogMe annotation = method.getAnnotation(LogMe.class);
        //增加tag
        if (annotation.tag().length() > 0) {
            inputInfoBuilder.append(annotation.tag()).append("|");
        }

        if (arguments.length > 0 && parameterNames != null && parameterNames.length == arguments.length) {
            boolean first = true;
            for (int i = 0; i < arguments.length; i++) {
                Object argument = arguments[i];
                Parameter parameter = parameters[i];
                String name = null;
                if (parameter.isNamePresent()) {
                    name = parameter.getName();
                } else {
                    name = parameterNames[i];
                }

                String valueString = JSON.toJSONString(argument);
                String paramSep = ",";
                if (first) {
                    inputInfoBuilder.append(name).append("=").append(valueString);
                    first = false;
                } else {
                    inputInfoBuilder.append(paramSep).append(name).append("=").append(valueString);
                }
            }
        }
        return inputInfoBuilder.toString();
    }

}
