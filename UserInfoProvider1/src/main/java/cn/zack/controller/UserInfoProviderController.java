package cn.zack.controller;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支持dubbo调用和rest调用
 */
@RestController
@Service(protocol = {"dubbo", "rest"})
public class UserInfoProviderController {

    @Value("${config.test}")
    private String configTest;

    @GetMapping(path = "fun1")
    public String fun1() {
        return "调用了userInfoProvider1, 方法: fun1, configTest=" + configTest;
    }
}
