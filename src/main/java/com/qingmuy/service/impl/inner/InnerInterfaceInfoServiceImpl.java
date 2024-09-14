package com.qingmuy.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingmuy.common.ErrorCode;
import com.qingmuy.exception.BusinessException;
import com.qingmuy.mapper.InterfaceInfoMapper;
import com.qingmuy.model.entity.InterfaceInfo;
import com.qingmuy.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Created withIntelliJ IDEA.
 *
 * @author: qingmuy
 * @date:2024/9/14
 * @time:17:33
 * @description : do some thing
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> qw = new LambdaQueryWrapper<>();
        qw.eq(InterfaceInfo::getUrl, url)
                .eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(qw);
    }
}
