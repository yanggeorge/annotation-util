package com.threelambda.multi;

import com.threelambda.ServiceResult;
import com.threelambda.TestConfig;
import com.threelambda.catchme.TestErrorEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yangming 2018/9/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MultiSimpleTest {
    private static final Logger logger = LoggerFactory.getLogger(MultiSimpleTest.class);
    @Autowired
    private MultiTestService multiTestService;

    @Test
    public void test1() {
        multiTestService.simple("ym", 11);
    }

    @Test
    public void test2() {
        ServiceResult simpleResult = multiTestService.getSimpleResult(null, 11);
        assertThat(simpleResult.getErrCode()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrCode());
    }

    @Test
    public void test21() {
        ServiceResult simpleResult = multiTestService.getSimpleResult("ym", -1);
        assertThat(simpleResult.getErrCode()).isEqualTo("SYSTEM_ERROR");
        simpleResult = multiTestService.getSimpleResult(null, 11);
        assertThat(simpleResult.getErrCode()).isEqualTo(TestErrorEnum.NULL_PARAMETER.getErrCode());

    }

}
