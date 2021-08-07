package com.shine2share.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.shine2share")
@EntityScan(basePackages = "com.shine2share")
@EnableJpaRepositories(basePackages = "com.shine2share")
@SpringBootApplication
public class MainProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainProjectApplication.class, args);
    }
}
