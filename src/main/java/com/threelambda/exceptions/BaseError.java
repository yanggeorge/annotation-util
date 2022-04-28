package com.threelambda.exceptions;

import java.io.Serializable;

/**
 * @author yangming 2018/10/10
 */
public interface BaseError extends Serializable {
    /**
     * 返回错误的编码
     *
     * @return
     */
    String getErrCode();

    /**
     * 返回错误的描述
     *
     * @return
     */
    String getErrMsg();
}
