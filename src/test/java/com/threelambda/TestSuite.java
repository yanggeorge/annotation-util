package com.threelambda;


import com.threelambda.catchme.CatchMeDetailTest;
import com.threelambda.logme.LogMeDetailTest;
import com.threelambda.methodintercept.FoolDetailTest;
import com.threelambda.multi.MultiSimpleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yangming 2018/10/12
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({LogMeDetailTest.class, CatchMeDetailTest.class, MultiSimpleTest.class, FoolDetailTest.class})
public class TestSuite {

}
