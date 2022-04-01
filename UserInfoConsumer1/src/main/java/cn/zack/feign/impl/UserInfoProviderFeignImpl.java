package cn.zack.feign.impl;

import cn.zack.feign.UserInfoProviderFeign;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class UserInfoProviderFeignImpl implements UserInfoProviderFeign {
    @Override
    public JSONObject fun1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "UserInfo Fall Back");
        return jsonObject;
    }
}
