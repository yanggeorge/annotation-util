package com.threelambda.response;

/**
 * 返回结果的接口
 *
 * @author yangming 2018/10/24
 */
public interface BaseSetterResult {

    void setErrCode(String errCode);

    void setErrMsg(String errMsg);

    void setSuccess(Boolean success);

}
