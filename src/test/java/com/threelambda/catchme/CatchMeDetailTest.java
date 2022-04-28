package com.threelambda.catchme;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


import com.threelambda.AnnotationUtil;
import com.threelambda.ServiceResult;
import com.threelambda.TestConfig;
import com.threelambda.log.AbstractBaseAdvice;
import com.threelambda.log.CatchMe;
import com.threelambda.log.CatchMeAdvice;
import com.threelambda.logme.MyLogger;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.threelambda.AnnotationUtil.setFinalStatic;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yangming 2018/10/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CatchMeDetailTest {

    private static final Logger logger = LoggerFactory.getLogger(CatchMeDetailTest.class);

    private CatchMeTestService proxy;
    private MyLogger myLogger;

    @Before
    public void setUp() throws Exception {
        CatchMeTestService catchMeTestService = new CatchMeTestService();
        myLogger = new MyLogger();
        setFinalStatic(CatchMeAdvice.class.getDeclaredField("logger"), myLogger);
        Map<Class<?>, AbstractBaseAdvice> map = new HashMap<>();
        map.put(CatchMe.class, new CatchMeAdvice());
        proxy = (CatchMeTestService)AnnotationUtil.makeProxy(catchMeTestService, map);
    }

    @Test
    public void test1() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        ServiceResult result = proxy.getSimpleResult(null, 123);
        assertThat(result.getErrCode()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrCode());
        assertThat(result.getErrMessage()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrMsg());

        logger.info(baos.toString());
        String expected = "com.threelambda.catchme.CatchMeTestService.getSimpleResult fail.\n"
            + "com.threelambda.catchme.TestCustomException: NULL_PARAMETER|空值\n"
            + "\tat com.threelambda.catchme.CatchMeTestService.getSimpleResult(CatchMeTestService.java";

        assertThat(baos.toString()).startsWith(expected);
    }

    @Test
    public void test2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        proxy.getSimpleResult("ym", 123);

        logger.info(baos.toString());
        String expected = "com.threelambda.catchme.CatchMeTestService.getSimpleResult success.";
        assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test4() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        CustomResult customResult = proxy.getCustomResult("");
        logger.info(baos.toString());
        Assertions.assertThat(customResult.getErrCode()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrCode());
    }
}
