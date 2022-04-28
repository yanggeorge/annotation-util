package com.threelambda;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yangming 2018/9/29
 */
@Configuration
@ComponentScan(basePackages = {"com.threelambda"})
@PropertySource("classpath:test.properties")
@ImportResource(locations = {"classpath:interceptor-test.xml", "classpath:appConfig.xml"})
public class TestConfig {

}
