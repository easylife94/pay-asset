package com.pay.asset.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.pay.*"})
@MapperScan("com.pay.asset.core.dao")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.pay.*"})
@EnableCaching
public class PayAssetWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayAssetWebApplication.class, args);
    }

}
