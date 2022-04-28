package com.threelambda.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * 配合{@link Service}等spring注解使用
 * <p>
 * <pre>
 * {@code @}Service
 * public class MyLogFool{
 *
 *   {@code @}LogMe
 *    public void print(String name) {
 *     ...
 *    }
 *
 *   {@code @}LogMe(tag = "xflush-MyLogFool")
 *    public void say(FoolRequest request) {
 *      ...
 *    }
 *  }
 *  </pre>
 *
 * @author yangming 2018/9/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMe {

    /**
     * add |tag| in log
     *
     * @return
     */
    String tag() default "";
}
