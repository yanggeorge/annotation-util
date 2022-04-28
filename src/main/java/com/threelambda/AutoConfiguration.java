package com.threelambda;

import com.threelambda.log.CatchMeConfigProperties;
import com.threelambda.log.LogMeConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangming 2018/9/28
 */
@Configuration
@EnableConfigurationProperties({ConfigProperties.class, LogMeConfigProperties.class, CatchMeConfigProperties.class})
public class AutoConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(AutoConfiguration.class);

    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private LogMeConfigProperties logMeConfigProperties;
    @Autowired
    private CatchMeConfigProperties catchMeConfigProperties;

    @Bean
    @ConditionalOnProperty(name = "alijk.util.annotation.enabled", matchIfMissing = true)
    public MainBeanPostProcessor mainBeanPostProcessor() {
        logger.info("init mainBeanPostProcessor");
        logger.debug(configProperties.getEnabled());
        logger.debug(logMeConfigProperties.getEnabled());
        logger.debug(catchMeConfigProperties.getEnabled());
        return new MainBeanPostProcessor();
    }
}
