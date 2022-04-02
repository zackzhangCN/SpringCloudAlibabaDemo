package cn.zack.controller;

import com.alibaba.fastjson.JSONObject;
//import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支持dubbo调用和rest调用
 */
@RestController
//@Service(protocol = {"dubbo", "rest"})
public class UserInfoProviderController {

    @GetMapping(path = "fun1")
    public JSONObject fun1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "调用了userInfoProvider1, 方法: fun1");
        return jsonObject;
    }
}
