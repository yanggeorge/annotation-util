package com.threelambda.methodintercept;

import com.threelambda.exceptions.BaseError;
import com.threelambda.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/10/23
 */
public class FoolException extends BaseException {

    private static final Logger logger = LoggerFactory.getLogger(FoolException.class);

    public FoolException(BaseError baseError) {
        super(baseError);
    }

    public FoolException(BaseError baseError, Throwable cause) {
        super(baseError, cause);
    }

    public FoolException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public FoolException(String errCode, String errMsg, Throwable cause) {
        super(errCode, errMsg, cause);
    }
}
