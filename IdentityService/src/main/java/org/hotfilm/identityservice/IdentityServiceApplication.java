package org.hotfilm.identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Schedules;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class IdentityServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

}
