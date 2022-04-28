package com.threelambda.xmlconfig;

import com.threelambda.log.LogMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/11/5
 */
public class XmlConfigService {

    private static final Logger logger = LoggerFactory.getLogger(XmlConfigService.class);

    @LogMe
    public String say(String name) {
        return name + " hello.";
    }
}
