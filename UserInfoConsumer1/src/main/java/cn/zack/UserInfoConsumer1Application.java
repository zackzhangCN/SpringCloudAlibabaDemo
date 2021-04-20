package cn.zack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableDiscoveryClient 开启服务注册发现
 * @EnableFeignClients 开启feign声明式调用
 * <p>
 * userInfo服务消费者1
 * 通过feign进行服务调用
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class UserInfoConsumer1Application {
    public static void main(String[] args) {
        SpringApplication.run(UserInfoConsumer1Application.class, args);
    }
}
