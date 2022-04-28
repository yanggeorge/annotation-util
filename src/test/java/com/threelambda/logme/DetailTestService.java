package com.threelambda.logme;

import com.threelambda.log.LogMe;
import com.threelambda.simple.SimpleTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangming 2018/9/30
 */
public class DetailTestService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleTestService.class);

    @LogMe
    public void say(String name, int age) {
        logger.info("...");
    }

    @LogMe
    public void handle(TestRequest testRequest) {
        logger.info("...");
    }

    @LogMe(tag = "xflush=tagMe")
    public void tagMe(String input) {
        logger.info("...");
    }

    @LogMe
    public void noInput() {
        logger.info("...");
    }

    public void notLogMe(String input) {
        logger.info("...");
    }

    public static class TestRequest {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
