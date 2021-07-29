package com.sast.atSast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.sast.atSast.mapper")
public class AtSastApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtSastApplication.class, args);
    }
}