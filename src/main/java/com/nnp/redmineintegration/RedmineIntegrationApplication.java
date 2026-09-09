package com.nnp.redmineintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RedmineIntegrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedmineIntegrationApplication.class, args);
    }
}
