package com.qingmuy.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingmuy.annotation.AuthCheck;
import com.qingmuy.common.BaseResponse;
import com.qingmuy.common.ErrorCode;
import com.qingmuy.common.ResultUtils;
import com.qingmuy.constant.UserConstant;
import com.qingmuy.exception.BusinessException;
import com.qingmuy.mapper.UserInterfaceInfoMapper;
import com.qingmuy.model.entity.InterfaceInfo;
import com.qingmuy.model.entity.UserInterfaceInfo;
import com.qingmuy.model.vo.InterfaceInfoVO;
import com.qingmuy.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created withIntelliJ IDEA.
 *
 * @author: qingmuy
 * @date:2024/9/15
 * @time:11:08
 * @description : do some thing
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        // 根据接口的Id进行分组，Map中的键即为接口Id
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        LambdaQueryWrapper<InterfaceInfo> qw = new LambdaQueryWrapper<>();
        qw.in(InterfaceInfo::getId, interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(qw);
        // 一般情况下不会为空
        if (CollectionUtil.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 将查询得到的接口信息处理成VO封装类
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            Integer totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }
}
