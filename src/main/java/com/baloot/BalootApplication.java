package com.baloot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@SpringBootApplication
@ServletComponentScan
public class BalootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalootApplication.class, args);
    }

}
