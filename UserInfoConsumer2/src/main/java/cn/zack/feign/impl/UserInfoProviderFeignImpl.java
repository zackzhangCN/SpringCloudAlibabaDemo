package cn.zack.feign.impl;

import cn.zack.feign.UserInfoProviderFeign;
import org.springframework.stereotype.Component;

@Component
public class UserInfoProviderFeignImpl implements UserInfoProviderFeign {
    @Override
    public String fun1() {
        return "UserInfo Fall Back";
    }
}
