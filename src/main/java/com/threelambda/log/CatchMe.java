package com.threelambda.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.threelambda.exceptions.BaseError;
import com.threelambda.response.BaseSetterResult;

/**
 * 1. 捕获的异常需要实现  {@code BaseError} 接口  <br />
 * 2. 返回的Result需要实现 {@code BaseSetterError} 接口
 *
 * @author yangming 2018/10/9
 * @see BaseError
 * @see BaseSetterResult
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CatchMe {

    /**
     * 在日志中增加标识。
     *
     * @return
     */
    String tag() default "";
}
