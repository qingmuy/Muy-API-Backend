package com.qingmuy.muygateway;

import com.qingmuy.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MuyGatewayApplicationTests {

    @DubboReference
    private DemoService demoService;

    @Test
    void contextLoads() {
        System.out.println(demoService.sayHello("dingzhen"));
    }

}


