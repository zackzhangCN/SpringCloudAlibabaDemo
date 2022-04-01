package cn.zack.feign;

import cn.zack.feign.impl.UserInfoProviderFeignImpl;
import com.alibaba.cloud.dubbo.annotation.DubboTransported;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 使用@FeignClient进行rest调用, 并指定要调用哪个服务, 并指定容错处理方法
 */
@FeignClient(name = "userInfoProvider", fallback = UserInfoProviderFeignImpl.class)
@Component
@DubboTransported(protocol = "dubbo")
public interface UserInfoProviderFeign {

    /**
     * 此服务下的方法
     *
     * @return
     */
    @GetMapping(path = "fun1")
    JSONObject fun1();
}