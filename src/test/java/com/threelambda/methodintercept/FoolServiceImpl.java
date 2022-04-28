package com.threelambda.methodintercept;

import java.util.Objects;

import com.threelambda.ServiceResult;
import com.threelambda.log.CatchMe;
import com.threelambda.log.LogMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author yangming 2018/10/22
 */
@Service("foolService")
public class FoolServiceImpl implements FoolService {

    private static final Logger logger = LoggerFactory.getLogger(FoolServiceImpl.class);

    @LogMe
    public String say(String name) {
        return "name";
    }

    @Override
    @LogMe
    @CatchMe
    public ServiceResult<String> throwMe(String name) {
        if (name == null || Objects.equals(name, "")) {
            throw new FoolException(FoolErrorEnum.NULL);
        }
        return ServiceResult.successResult(name);
    }
}
