package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 1.整合mybatis-plus
 *  (1) 导入依赖
 *  (2) 配置
 *      1. 配置数据源
 *          (1) 导入数据库驱动
 *          (2) 在application.yml中配置数据源信息
 *      2. 配置mybatis-plus相关信息
 *          (1) 使用@MapperScan扫描dao文件
 *          (2) 告诉mybatis-plus,sql映射位置
 *
 *
 */

@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.atguigu.gulimall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
