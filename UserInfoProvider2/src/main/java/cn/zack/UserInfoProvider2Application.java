package cn.zack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient 开启服务注册发现
 * userInfo服务提供者2
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserInfoProvider2Application {
    public static void main(String[] args) {
        SpringApplication.run(UserInfoProvider2Application.class, args);
    }
}
