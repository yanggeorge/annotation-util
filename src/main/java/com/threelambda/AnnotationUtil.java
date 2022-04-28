package com.threelambda;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.threelambda.log.AbstractBaseAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author yangming 2018/9/30
 */
public class AnnotationUtil {

    public static List<String> getMethods(Class<?> targetClass, Class<?> clazz) {
        List<String> logMeMethods = new ArrayList<>();
        Method[] declaredMethods = targetClass.getDeclaredMethods();
        if (declaredMethods != null) {
            for (Method method : declaredMethods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                if (find(annotations, clazz)) {
                    logMeMethods.add(method.getName());
                }
            }
        }
        return logMeMethods;
    }

    /**
     * @param target
     * @param map    map.put(LogMe.class, new LogMeAdvice());// AnnotationClass => AnnotationAdvice
     * @return
     */
    public static Object makeProxy(Object target, Map<Class<?>, AbstractBaseAdvice> map) {
        if (map == null) {
            throw new RuntimeException("map is null");
        }

        Class<?> targetClass = null;
        if (target instanceof Advised) {
            //如果已经通过auto-proxy增强了
            Advised advised = (Advised)target;
            targetClass = advised.getTargetClass();

            List<DefaultPointcutAdvisor> advisors = getDefaultPointcutAdvisors(map, targetClass);
            for (DefaultPointcutAdvisor advisor : advisors) {
                advised.addAdvisor((Advisor)advisor);
            }
            return advised;
        } else {
            //如果是普通的Bean
            targetClass = target.getClass();

            List<DefaultPointcutAdvisor> advisors = getDefaultPointcutAdvisors(map, targetClass);

            target = makeProxyHelper(target, advisors);
            return target;
        }
    }

    private static List<DefaultPointcutAdvisor> getDefaultPointcutAdvisors(Map<Class<?>, AbstractBaseAdvice> map,
                                                                           Class<?> targetClass) {
        List<DefaultPointcutAdvisor> advisors = new ArrayList<>();
        for (Entry<Class<?>, AbstractBaseAdvice> entry : map.entrySet()) {
            Class<?> annotationClazz = entry.getKey();
            AbstractBaseAdvice abstractBaseAdvice = entry.getValue();
            List<String> methodsWithAnnotation = getMethods(targetClass, annotationClazz);
            if (!methodsWithAnnotation.isEmpty()) {
                DefaultPointcutAdvisor advisor = abstractBaseAdvice.makeAdvisor(targetClass, methodsWithAnnotation);
                advisors.add(advisor);
            }
        }
        advisors.sort((advisor1, advisor2) -> {
            Integer order1 = advisor1.getOrder();
            Integer order2 = advisor2.getOrder();
            return order1.compareTo(order2);
        });
        return advisors;
    }

    /**
     * 构造一个代理
     *
     * @param target 目标对象
     * @return
     */
    private static Object makeProxyHelper(Object target, Advice advice) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        factory.addAdvice(advice);
        target = factory.getProxy();
        return target;
    }

    private static Object makeProxyHelper(Object target, List<DefaultPointcutAdvisor> advisors) {
        if (advisors == null || advisors.size() == 0) {
            return target;
        }
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        for (Advisor advisor : advisors) {
            factory.addAdvisor(advisor);
        }

        target = factory.getProxy();
        return target;
    }

    public static boolean find(Annotation[] annotations, Class<?> clazz) {
        if (annotations == null) {
            return false;
        }
        for (Annotation annotation : annotations) {
            String name = annotation.annotationType().getName();
            if (clazz.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
