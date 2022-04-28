package com.threelambda.catchme;

import com.threelambda.ServiceResult;
import com.threelambda.log.CatchMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/9/30
 */
public class CatchMeTestService {
    private static final Logger logger = LoggerFactory.getLogger(CatchMeTestService.class);

    @CatchMe
    public String simple(String name, int age) {
        logger.info("...");
        return "simple";
    }

    @CatchMe
    public ServiceResult getSimpleResult(String name, int age) {
        if (name == null || "".equals(name)) {
            throw new TestCustomException(TestErrorEnum.NULL_PARAMETER);
        }
        return ServiceResult.successResult("yes");
    }

    @CatchMe
    public CustomResult getCustomResult(String name) {
        if (name == null || "".equals(name)) {
            throw new TestCustomException(TestErrorEnum.NULL_PARAMETER);
        }
        return CustomResult.successResult("yes:" + name);
    }

    //public ServiceResult getSimpleResult(String name, int age) {
    //    try {
    //        if (name == null || "".equals(name)) {
    //            throw new TestCustomException(TestErrorEnum.NULL_PARAMETER);
    //        }
    //        return ServiceResult.successResult("yes");
    //    } catch (TestCustomException e) {
    //        logger.error("getSimpleResult fail.", e);
    //        return ServiceResult.failedResult(e.getErrCode(), e.getErrMsg());
    //    } catch (Exception e) {
    //        logger.error("getSimpleResult fail unexpectedly.", e);
    //        return ServiceResult.failedResult("SYSTEM_CODE", "系统错误");
    //    }
    //}

}
