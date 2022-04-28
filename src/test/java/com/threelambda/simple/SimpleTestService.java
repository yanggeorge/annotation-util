package com.threelambda.simple;

import com.threelambda.log.LogMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yangming 2018/9/29
 */
@Component
public class SimpleTestService {

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
