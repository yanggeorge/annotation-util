package com.threelambda;

import java.util.HashMap;
import java.util.Map;

import com.threelambda.log.AbstractBaseAdvice;
import com.threelambda.log.CatchMe;
import com.threelambda.log.CatchMeAdvice;
import com.threelambda.log.LogMe;
import com.threelambda.log.LogMeAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import static com.threelambda.AnnotationUtil.makeProxy;

/**
 * @author yangming 2018/9/25
 */
public class MainBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MainBeanPostProcessor.class);

    private Map<Class<?>, AbstractBaseAdvice> map;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object target, String s) throws BeansException {
        logger.debug("postProcessAfterInitialization|BeanClassName=" + s);
        try {
            if (map == null) {
                map = new HashMap<>();
                map.put(LogMe.class, new LogMeAdvice());
                map.put(CatchMe.class, new CatchMeAdvice());
            }
            target = makeProxy(target, map);
        } catch (Exception e) {
            logger.warn("MainBeanPostProcessor has something wrong.", e);
        }
        return target;
    }

}
