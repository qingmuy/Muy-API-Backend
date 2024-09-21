package com.qingmuy.muyinterface.service.impl;

import com.qingmuy.muyinterface.service.NameService;
import org.springframework.stereotype.Service;

/**
 * Created withIntelliJ IDEA.
 *
 * @author: qingmuy
 * @date:2024/9/20
 * @time:22:31
 * @description : do some thing
 */
@Service
public class NameServiceImpl implements NameService {
    @Override
    public String Getname(String name) {
        return "POST 用户名字是" + name;
    }
}
