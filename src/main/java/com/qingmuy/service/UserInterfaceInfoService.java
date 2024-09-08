package com.qingmuy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingmuy.model.entity.UserInterfaceInfo;

/**
* @author Muy
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-09-08 10:22:35
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return 接口调用次数
     */
    Boolean invokeCount(Long interfaceInfoId, Long userId);
}
