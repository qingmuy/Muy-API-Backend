package com.qingmuy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingmuy.model.entity.InterfaceInfo;

/**
* @author Muy
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-22 22:54:15
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
