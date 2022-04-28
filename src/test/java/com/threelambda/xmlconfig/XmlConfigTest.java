package com.threelambda.xmlconfig;

import javax.annotation.Resource;

import com.threelambda.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangming 2018/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class XmlConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(XmlConfigTest.class);

    @Resource
    private XmlConfigService xmlConfigService;

    @Before
    public void before() {

    }

    @Test
    public void test() {
        String result = xmlConfigService.say("ym");
        logger.info("result={}", result);
    }
}
