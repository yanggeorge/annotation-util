package com.threelambda.methodintercept;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.threelambda.ServiceResult;
import com.threelambda.TestConfig;
import com.threelambda.log.CatchMeAdvice;
import com.threelambda.log.LogMeAdvice;
import com.threelambda.logme.MyLogger;
import org.aopalliance.aop.Advice;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.threelambda.AnnotationUtil.setFinalStatic;

/**
 * @author yangming 2018/10/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FoolDetailTest {

    @Autowired
    private FoolServiceImpl foolService;

    private MyLogger myLogger;

    @Before
    public void setup() throws Exception {
        myLogger = new MyLogger();
        Advised advised = (Advised)foolService;
        Advisor[] advisors = advised.getAdvisors();
        for (Advisor advisor : advisors) {
            Advice advice = advisor.getAdvice();
            if (advice instanceof TestMethodInterceptor) {
                setFinalStatic(advice.getClass().getDeclaredField("logger"), myLogger);
            } else if (advice instanceof CatchMeAdvice) {
                setFinalStatic(advice.getClass().getDeclaredField("logger"), myLogger);
            } else if (advice instanceof LogMeAdvice) {
                setFinalStatic(advice.getClass().getDeclaredField("logger"), myLogger);
            }
        }

    }

    @Test
    public void test1() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        foolService.say("ym");

        System.out.println(baos.toString());
        String expected = "TestMethodInterceptor enter.|order=111"
            + "TestMethodInterceptor enter.|order=222"
            + "enter=com.threelambda.methodintercept.FoolServiceImpl.say|name=\"ym\""
            + "exit=com.threelambda.methodintercept.FoolServiceImpl.say"
            + "TestMethodInterceptor exit.|order=222"
            + "TestMethodInterceptor exit.|order=111";
        Assertions.assertThat(baos.toString()).isEqualTo(expected);
    }

    @Test
    public void test2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));

        ServiceResult<String> result = foolService.throwMe("");

        System.out.println(baos.toString());
        Assertions.assertThat(result.getErrCode()).isEqualTo(FoolErrorEnum.NULL.getErrCode());

        String expectedStartString = ""
            + "TestMethodInterceptor enter.|order=111"
            + "TestMethodInterceptor enter.|order=222"
            + "enter=com.threelambda.methodintercept.FoolServiceImpl.throwMe|name=\"\""
            + "com.threelambda.methodintercept.FoolServiceImpl.throwMe fail.\n"
            + "com.threelambda.methodintercept.FoolException: NULL|参数为空\n"
            + "\tat com.threelambda.methodintercept.FoolServiceImpl.throwMe(FoolServiceImpl.java";
        Assertions.assertThat(baos.toString()).startsWith(expectedStartString);

        String expectedEndString
            = "exit=com.threelambda.methodintercept.FoolServiceImpl.throwMe"
            + "TestMethodInterceptor exit.|order=222"
            + "TestMethodInterceptor exit.|order=111";
        Assertions.assertThat(baos.toString()).endsWith(expectedEndString);
    }

    @Test
    public void test3() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myLogger.setStream(new PrintStream(baos));
        //正例
        ServiceResult<String> result = foolService.throwMe("ym");

        System.out.println(baos.toString());
        String expected = "TestMethodInterceptor enter.|order=111"
            + "TestMethodInterceptor enter.|order=222"
            + "enter=com.threelambda.methodintercept.FoolServiceImpl.throwMe|name=\"ym\""
            + "com.threelambda.methodintercept.FoolServiceImpl.throwMe success."
            + "exit=com.threelambda.methodintercept.FoolServiceImpl.throwMe"
            + "TestMethodInterceptor exit.|order=222"
            + "TestMethodInterceptor exit.|order=111";

        Assertions.assertThat(baos.toString()).isEqualTo(expected);

    }
}
