package cn.zack.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支持dubbo调用和rest调用
 */
@RestController
@RequestMapping(path = "provider")
@Service(protocol = {"dubbo", "rest"})
public class UserInfoProviderController {

    @Value("${config.test}")
    private String configTest;

    @GetMapping(path = "fun1")
    public JSONObject fun1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "调用了userInfoProvider1, 方法: fun1, configTest=" + configTest);
        return jsonObject;
    }
}
