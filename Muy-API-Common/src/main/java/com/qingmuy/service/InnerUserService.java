package com.qingmuy.service;

import com.qingmuy.model.entity.User;

/**
 * Created withIntelliJ IDEA.
 *
 * @author: qingmuy
 * @date:2024/9/14
 * @time:17:30
 * @description : do some thing
 */
public interface InnerUserService {

    User getInvokeUser(String accessKey);
}
