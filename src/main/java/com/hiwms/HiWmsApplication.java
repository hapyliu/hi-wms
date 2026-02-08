package com.hiwms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hiwms.**.mapper")
public class HiWmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiWmsApplication.class, args);
    }
}
