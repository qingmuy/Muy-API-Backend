package com.qingmuy.service.impl;

import com.qingmuy.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceImplTest {

    @Resource
    UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        Boolean b = userInterfaceInfoService.invokeCount(1L, 1L);
        Assertions.assertTrue(b);
    }
}