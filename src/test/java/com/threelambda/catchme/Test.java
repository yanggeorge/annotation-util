package com.threelambda.catchme;

import com.threelambda.response.BaseSetterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/10/25
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class returnType = CustomResult.class;

        BaseSetterResult result = (BaseSetterResult)returnType.newInstance();
        result.setSuccess(false);
        result.setErrCode("NULL");
        result.setErrMsg("空值");
        System.out.println(result);
    }
}
