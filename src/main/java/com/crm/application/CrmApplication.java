package com.crm.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan
public class CrmApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(CrmApplication.class, args);
    }

}