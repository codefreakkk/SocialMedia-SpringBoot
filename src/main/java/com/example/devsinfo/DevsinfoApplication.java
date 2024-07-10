package com.example.devsinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DevsinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevsinfoApplication.class, args);
    }

}
