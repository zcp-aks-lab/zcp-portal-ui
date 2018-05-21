package com.skcc.cloudz.zcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.Compression;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.skcc.cloudz.zcp"})
public class ZcpPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZcpPortalApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return (ConfigurableEmbeddedServletContainer container) -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                Compression compression = new Compression();
                compression.setEnabled(true);
                compression.setMinResponseSize(2048);
                container.setCompression(compression);
            }
        };
    }
}
