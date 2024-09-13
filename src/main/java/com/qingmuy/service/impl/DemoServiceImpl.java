package com.qingmuy.service.impl;

import com.qingmuy.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * Created withIntelliJ IDEA.
 *
 * @author: qingmuy
 * @date:2024/9/13
 * @time:15:24
 * @description : do some thing
 */
@DubboService
@Component
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello" + name;
    }
}
