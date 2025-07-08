package com.example.bookify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookifyApplication.class, args);
    }

}
