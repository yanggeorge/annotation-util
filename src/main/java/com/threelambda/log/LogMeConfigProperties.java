package com.threelambda.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangming 2018/9/28
 */
@ConfigurationProperties(prefix = "alijk.util.annotation.log.me")
public class LogMeConfigProperties {

    private String enabled;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
