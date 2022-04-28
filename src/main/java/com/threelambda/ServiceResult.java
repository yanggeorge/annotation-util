package com.threelambda;

import java.io.Serializable;

/**
 * @author yangming 2019-02-02
 */
public class ServiceResult<T> implements Serializable
{
    private static final long serialVersionUID = -828357066742441358L;

    private boolean success = true;

    private T data;

    private String errCode;

    private String errMessage;

    public ServiceResult()
    {

    }

    public ServiceResult(T data)
    {
        this.data = data;
    }

    public ServiceResult(boolean success, String errCode, String errMessage)
    {
        this.success = success;
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public static <T> ServiceResult<T> successResult(T t)
    {
        return new ServiceResult<T>(t);
    }

    public static <T> ServiceResult<T> failedResult(String errCode, String errMessage)
    {
        return new ServiceResult<T>(false, errCode, errMessage);
    }


    public static <T> ServiceResult<T> failedResult(EnumBase code)
    {
        return new ServiceResult<T>(false, code.getCode(), code.getValue());
    }



    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public String getErrCode()
    {
        return errCode;
    }

    public void setErrCode(String errCode)
    {
        this.errCode = errCode;
    }

    public String getErrMessage()
    {
        return errMessage;
    }

    public void setErrMessage(String errMessage)
    {
        this.errMessage = errMessage;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

}

