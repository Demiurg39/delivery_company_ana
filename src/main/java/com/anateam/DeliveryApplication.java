package com.anateam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import tech.ailef.snapadmin.external.SnapAdminAutoConfiguration;

@EnableScheduling
@SpringBootApplication
@ImportAutoConfiguration(SnapAdminAutoConfiguration.class)
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }
}
