package com.itclopedia.cources.starter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.itclopedia.cources")
@EnableJpaRepositories(basePackages = "com.itclopedia.cources.dao")
@EntityScan(basePackages = "com.itclopedia.cources.model")
@EnableAspectJAutoProxy
@Slf4j
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}