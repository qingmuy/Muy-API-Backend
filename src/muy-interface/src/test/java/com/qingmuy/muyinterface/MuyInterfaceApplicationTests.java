package com.qingmuy.muyinterface;

import com.qingmuy.muyapiclientsdk.client.MuyApiClient;
import com.qingmuy.muyapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MuyInterfaceApplicationTests {

    @Resource
    MuyApiClient muyApiClient;

    @Test
    void contextLoads() {
        String result = muyApiClient.getNameByGet("dingzhen");
        User user = new User();
        user.setUsername("dzzz");
        String usernameByPost = muyApiClient.getUsernameByPost(user);
        System.out.println(result);
        System.out.println(usernameByPost);
    }

}
