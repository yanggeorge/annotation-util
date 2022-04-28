package com.threelambda.logme;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.threelambda.AnnotationUtil;
import com.threelambda.TestConfig;
import com.threelambda.log.AbstractBaseAdvice;
import com.threelambda.log.LogMe;
import com.threelambda.log.LogMeAdvice;
import com.threelambda.logme.DetailTestService.TestRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.threelambda.AnnotationUtil.setFinalStatic;

/**
 * @author yangming 2018/9/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LogMeDetailTest {

    private static final Logger logger = LoggerFactory.getLogger(LogMeDetailTest.class);

    private DetailTestService proxy;
    private MyLogger myLogger;

    @Before
    public void setUp() throws Exception {
        DetailTestService detailTestService = new DetailTestService();
        myLogger = new MyLogger();
        setFinalStatic(LogMeAdvice.class.getDeclaredField("logger"), myLogger);
        Map<Class<?>, AbstractBaseAdvice> map = new HashMap<>();
        map.put(LogMe.class, new LogMeAdvice());
        proxy = (DetailTestService)AnnotationUtil.makeProxy(detailTestService, map);
    }

    @Test
    public void test1() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        proxy.say("nihao", 123);

        String expected = "enter=com.threelambda.logme.DetailTestService.say|name=\"nihao\",age=123"
            + "exit=com.threelambda.logme.DetailTestService.say";
        //Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        TestRequest request = new TestRequest();
        request.setName("ym");
        request.setAge(1234);
        proxy.handle(request);

        String expected =
            "enter=com.threelambda.logme.DetailTestService.handle|testRequest={\"age\":1234,"
                + "\"name\":\"ym\"}"
                + "exit=com.threelambda.logme.DetailTestService.handle";
        Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test3() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        proxy.tagMe("abc");

        String expected = "enter=com.threelambda.logme.DetailTestService.tagMe|xflush=tagMe|input=\"abc\""
            + "exit=com.threelambda.logme.DetailTestService.tagMe";
        Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test4() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        proxy.notLogMe("abc");

        String expected = "";
        Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test5() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        proxy.noInput();

        String expected = "enter=com.threelambda.logme.DetailTestService.noInput|"
            + "exit=com.threelambda.logme.DetailTestService.noInput";
        Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }
}
