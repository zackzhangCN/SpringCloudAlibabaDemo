package cn.zack.controller;

import cn.zack.feign.UserInfoProviderFeign;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoConsumerController {

    /**
     * 注入userInfoProvider的feign接口, 通过feign进行调用
     */
    @Autowired
    private UserInfoProviderFeign userInfoProviderFeign;

    @GetMapping(path = "testFun1")
    public JSONObject testFun1() {
        JSONObject result = userInfoProviderFeign.fun1();
        return result;
    }
}
