package com.threelambda.multi;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.threelambda.AnnotationUtil;
import com.threelambda.ServiceResult;
import com.threelambda.TestConfig;
import com.threelambda.catchme.TestErrorEnum;
import com.threelambda.log.AbstractBaseAdvice;
import com.threelambda.log.CatchMe;
import com.threelambda.log.CatchMeAdvice;
import com.threelambda.log.LogMe;
import com.threelambda.log.LogMeAdvice;
import com.threelambda.logme.MyLogger;
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
public class MultiDetailTest {

    private static final Logger logger = LoggerFactory.getLogger(MultiDetailTest.class);

    private MultiTestService multiTestService;
    private MyLogger myLogger;

    @Before
    public void setUp() throws Exception {
        multiTestService = new MultiTestService();
        myLogger = new MyLogger();
        Map<Class<?>, AbstractBaseAdvice> map = new HashMap<>();
        setFinalStatic(CatchMeAdvice.class.getDeclaredField("logger"), myLogger);
        CatchMeAdvice catchMeAdvice = new CatchMeAdvice();
        map.put(CatchMe.class, catchMeAdvice);
        setFinalStatic(LogMeAdvice.class.getDeclaredField("logger"), myLogger);
        LogMeAdvice logMeAdvice = new LogMeAdvice();
        map.put(LogMe.class, logMeAdvice);
        multiTestService = (MultiTestService)AnnotationUtil.makeProxy(multiTestService, map);
    }

    @Test
    public void test1() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        ServiceResult simpleResult = multiTestService.getSimpleResult(null, 11);

        logger.info("baos=" + baos.toString());
        assertThat(simpleResult.getErrCode()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrCode());

    }

    @Test
    public void test2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        ServiceResult simpleResult = multiTestService.getSimpleResult("ym", 11);
        logger.info("baos=" + baos.toString());

    }
}
