package com.threelambda.catchme;


import com.threelambda.exceptions.BaseError;
import com.threelambda.exceptions.BaseException;

/**
 * @author yangming 2018/10/10
 */
public class TestCustomException extends BaseException {

    private static final long serialVersionUID = 498746881796684056L;

    public TestCustomException(BaseError baseError) {
        super(baseError);
    }

    public TestCustomException(BaseError baseError, Throwable cause) {
        super(baseError, cause);
    }

    public TestCustomException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public TestCustomException(String errCode, String errMsg, Throwable cause) {
        super(errCode, errMsg, cause);
    }
}
