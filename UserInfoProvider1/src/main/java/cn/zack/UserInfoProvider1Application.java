package cn.zack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 使用@EnableDiscoveryClient注解表明为服务发现客户端
 * userInfo服务提供者1
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserInfoProvider1Application {
    public static void main(String[] args) {
        SpringApplication.run(UserInfoProvider1Application.class, args);
    }
}
