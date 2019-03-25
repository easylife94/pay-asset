package com.pay.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.pay.*"})
@MapperScan("com.pay.api.core.dao")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.pay.*"})
@EnableCaching
public class PayAssetWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayAssetWebApplication.class, args);
    }

}
