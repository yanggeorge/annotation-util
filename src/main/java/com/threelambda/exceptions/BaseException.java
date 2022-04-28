package com.threelambda.exceptions;

/**
 * 运行时异常基类
 *
 * @author yangming 2018/10/10
 */
public class BaseException extends RuntimeException implements BaseError {

    private static final long serialVersionUID = 6283438883190932483L;

    private BaseError baseError;

    public BaseException(BaseError baseError) {
        super(format(baseError.getErrCode(), baseError.getErrMsg()));
        this.baseError = baseError;
    }

    public BaseException(BaseError baseError, Throwable cause) {
        super(format(baseError.getErrCode(), baseError.getErrMsg()), cause);
        this.baseError = baseError;
    }

    public BaseException(String errCode, String errMsg) {
        super(format(errCode, errMsg));
        this.baseError = new BaseError() {

            @Override
            public String getErrCode() {
                return errCode;
            }

            @Override
            public String getErrMsg() {
                return errMsg;
            }
        };
    }

    public BaseException(String errCode, String errMsg, Throwable cause) {
        super(format(errCode, errMsg), cause);
        this.baseError = new BaseError() {

            @Override
            public String getErrCode() {
                return errCode;
            }

            @Override
            public String getErrMsg() {
                return errMsg;
            }
        };
    }

    private static String format(String errCode, String errMsg) {
        return errCode + "|" + errMsg;
    }

    @Override
    public String getErrCode() {
        if (baseError != null) {
            return baseError.getErrCode();
        }
        return null;
    }

    @Override
    public String getErrMsg() {
        if (baseError != null) {
            return baseError.getErrMsg();
        }
        return null;
    }
}
