package com.threelambda.methodintercept;

import com.threelambda.exceptions.BaseError;

/**
 * @author yangming 2018/10/23
 */
public enum FoolErrorEnum implements BaseError {
    NULL("参数为空");

    private String msg;

    FoolErrorEnum(String msg) {
        this.msg = msg;
    }

    @Override
    public String getErrCode() {
        return name();
    }

    @Override
    public String getErrMsg() {
        return msg;
    }
}
