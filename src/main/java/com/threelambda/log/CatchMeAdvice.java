package com.threelambda.log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threelambda.ServiceResult;
import com.threelambda.exceptions.BaseError;
import com.threelambda.response.BaseSetterResult;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * @author yangming 2018/9/26
 */
public class CatchMeAdvice extends AbstractBaseAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CatchMeAdvice.class);

    private Map<String, ReturnTypeEnum> methodReturnTypeEnumMap;

    private Boolean hasPointCut = false;

    public CatchMeAdvice() {
    }

    public CatchMeAdvice(Class<?> targetClass, Boolean hasPointCut) {
        this.targetClass = targetClass;
        this.hasPointCut = hasPointCut;
        this.methodReturnTypeEnumMap = new HashMap<>();
        this.annotatedWithCatchMeMethods = new ArrayList<>();
    }

    private CatchMeAdvice(Class<?> targetClass, List<String> annotatedWithCatchMeMethods) {
        this.targetClass = targetClass;
        this.annotatedWithCatchMeMethods = annotatedWithCatchMeMethods;
        this.methodReturnTypeEnumMap = new HashMap<>();
    }

    @Override
    public DefaultPointcutAdvisor makeAdvisor(Class<?> targetClass, List<String> methodsWithAnnotation) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(new CatchMeAdvice(targetClass, true));
        NameMatchMethodPointcut pointCut = new NameMatchMethodPointcut();
        for (String methodName : methodsWithAnnotation) {
            pointCut.addMethodName(methodName);
        }
        advisor.setPointcut(pointCut);
        advisor.setOrder(AnnotationOrderEnum.CatchMe.getOrder());
        return advisor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        //todo 应该改成方法签名过滤
        if (hasPointCut || annotatedWithCatchMeMethods.contains(method.getName())) {
            String fullMethodName = "";
            Class<?>[] types = {};
            ReturnTypeEnum returnTypeEnum = null;
            Class<?> returnType = null;
            Boolean hasImplementBaseSetterResult = false;
            try {
                fullMethodName = getCurrentInvocationMethodFullName(method);

                returnTypeEnum = methodReturnTypeEnumMap.getOrDefault(fullMethodName, null);
                if (returnTypeEnum == null) {
                    returnTypeEnum = ReturnTypeEnum.getByClazz(method.getReturnType());
                    assert returnTypeEnum != null;
                    methodReturnTypeEnumMap.put(fullMethodName, returnTypeEnum);
                }

                returnType = methodInvocation.getMethod().getReturnType();
                //返回类型是否实现了BaseSetterResult接口
                hasImplementBaseSetterResult = hasImplement(returnType, BaseSetterResult.class);
                if (ReturnTypeEnum.unknown.equals(returnTypeEnum) && !hasImplementBaseSetterResult) {
                    throw new RuntimeException("unknown return type");
                }

                CatchMe annotation = method.getAnnotation(CatchMe.class);
                try {
                    Object proceed = methodInvocation.proceed();
                    logger.info(fullMethodName + " success.");
                    return proceed;
                } catch (Exception e) {
                    logger.error(fullMethodName + " fail.", e);

                    BaseError baseError = getBaseError(e);
                    switch (returnTypeEnum) {
                        case service_result: {
                            return ServiceResult.failedResult(baseError.getErrCode(), baseError.getErrMsg());
                        }
                        case unknown: {
                            if (hasImplementBaseSetterResult) {
                                BaseSetterResult result = (BaseSetterResult)returnType.newInstance();
                                result.setErrMsg(baseError.getErrMsg());
                                result.setErrCode(baseError.getErrCode());
                                result.setSuccess(false);
                                return result;
                            }
                            throw new RuntimeException("unknown return type");
                        }
                    }

                }

            } catch (Exception e) {
                logger.warn("catchMeAdvice has something wrong.", e);
            }
            return methodInvocation.proceed();
        } else {
            return methodInvocation.proceed();
        }
    }

    private boolean hasImplement(Class<?> returnType, Class<?> interfaceClass) {
        Class<?>[] interfaces = returnType.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if (anInterface.equals(interfaceClass)) {
                return true;
            }
        }
        return false;
    }

    private BaseError getBaseError(Exception e) {
        BaseError baseError = null;

        List<Class<?>> interfaces = findAllInterfaces(e.getClass());
        for (Class<?> anInterface : interfaces) {
            if (anInterface.equals(BaseError.class)) {
                baseError = (BaseError)e;
                break;
            }
        }

        if (baseError == null) {
            baseError = new BaseError() {
                @Override
                public String getErrCode() {
                    return "SYSTEM_ERROR";
                }

                @Override
                public String getErrMsg() {
                    return "系统错误";
                }
            };
        }
        return baseError;
    }

    private List<Class<?>> findAllInterfaces(Class<?> aClass) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> current = aClass;
        while (!current.equals(RuntimeException.class)) {
            interfaces.addAll(Arrays.asList(current.getInterfaces()));
            current = current.getSuperclass();
        }
        return interfaces;
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

}
