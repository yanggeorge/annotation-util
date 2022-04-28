package com.threelambda.catchme;

import com.threelambda.response.BaseSetterResult;

/**
 * @author yangming 2018/10/24
 */
public class CustomResult<T> implements BaseSetterResult {

    private boolean success = true;

    private T data;

    private String errCode;

    private String errMessage;

    public boolean isSuccess() {
        return success;
    }

    public static CustomResult<Object> successResult(Object data) {
        CustomResult<Object> result = new CustomResult<Object>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Override
    public void setErrMsg(String errMsg) {
        this.errMessage = errMsg;
    }

    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
