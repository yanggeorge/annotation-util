package com.threelambda.multi;


import com.threelambda.ServiceResult;
import com.threelambda.catchme.TestCustomException;
import com.threelambda.catchme.TestErrorEnum;
import com.threelambda.log.CatchMe;
import com.threelambda.log.LogMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author yangming 2018/9/30
 */
@Service
public class MultiTestService {
    private static final Logger logger = LoggerFactory.getLogger(MultiTestService.class);

    @LogMe
    @CatchMe
    public String simple(String name, int age) {
        logger.info("...");
        return "simple";
    }

    @LogMe
    @CatchMe
    public ServiceResult getSimpleResult(String name, int age) {
        if (name == null || "".equals(name)) {
            throw new TestCustomException(TestErrorEnum.NULL_PARAMETER);
        }

        if (age < 0) {
            throw new RuntimeException("age cannot be negative");
        }
        return ServiceResult.successResult("yes");
    }


}
