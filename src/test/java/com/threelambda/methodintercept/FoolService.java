package com.threelambda.methodintercept;

import com.threelambda.ServiceResult;

/**
 * @author yangming 2018/10/22
 */
public interface FoolService {

    String say(String name);

    ServiceResult<String> throwMe(String name);
}
