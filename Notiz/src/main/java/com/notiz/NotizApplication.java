package com.notiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NotizApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotizApplication.class, args);
    }

}
