package com.qingmuy.muygateway;

import com.qingmuy.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MuyGatewayApplicationTests {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void contextLoads() {
        System.out.println(innerUserInterfaceInfoService.invokeCount(1L, 1L));
    }

}


