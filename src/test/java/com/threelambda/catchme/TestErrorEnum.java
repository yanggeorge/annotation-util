package com.threelambda.catchme;

import com.threelambda.exceptions.BaseError;

/**
 * @author yangming 2018/10/10
 */
public enum TestErrorEnum implements BaseError {
    BIZ_ERR("系统错误"),
    NULL_PARAMETER("空值"),;

    private String errMsg;

    TestErrorEnum(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String getErrCode() {
        return name();
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }
}
