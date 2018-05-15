package com.skcc.cloudz.zcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.skcc.cloudz.zcp"})
public class ZcpPortalApplication {
	
    public static void main(String[] args) {
		SpringApplication.run(ZcpPortalApplication.class, args);
	}
}
