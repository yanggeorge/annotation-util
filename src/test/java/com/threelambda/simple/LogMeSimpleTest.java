package com.threelambda.simple;

import com.threelambda.TestConfig;
import com.threelambda.simple.SimpleTestService.TestRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangming 2018/9/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LogMeSimpleTest {
    private static final Logger logger = LoggerFactory.getLogger(LogMeSimpleTest.class);
    @Autowired
    private SimpleTestService testService;

    @Test
    public void test1() {
        testService.say("nihao", 123);
    }

    @Test
    public void test2() {
        TestRequest testRequest = new TestRequest();
        testRequest.setAge(123);
        testRequest.setName("ym");
        testService.handle(testRequest);
    }

    @Test
    public void test3() {
        testService.tagMe("abc");
    }
}
