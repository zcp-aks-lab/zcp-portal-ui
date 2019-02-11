package com.skcc.cloudz.zcp.configuration.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="console")
public class ConsoleProperties {
    private Map<String, String> application;

    public Map<String, String> getApplication() {
        return application;
    }
    public void setApplication(Map<String, String> application) {
        this.application = application;
    }
}