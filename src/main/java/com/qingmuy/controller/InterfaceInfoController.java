package com.qingmuy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qingmuy.annotation.AuthCheck;
import com.qingmuy.common.*;
import com.qingmuy.constant.CommonConstant;
import com.qingmuy.constant.UserConstant;
import com.qingmuy.exception.BusinessException;
import com.qingmuy.exception.ThrowUtils;
import com.qingmuy.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.qingmuy.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.qingmuy.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.qingmuy.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.qingmuy.model.entity.InterfaceInfo;
import com.qingmuy.model.entity.User;
import com.qingmuy.model.enums.InterfaceInfoStatusEnum;
import com.qingmuy.muyapiclientsdk.client.MuyApiClient;
import com.qingmuy.service.InterfaceInfoService;
import com.qingmuy.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    MuyApiClient muyApiClient;

    /**
     * 创建接口
     * @param interfaceInfoAddRequest 接口信息
     * @param request 请求头
     * @return 是否添加成功
     */
    @ApiOperation(value = "添加")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addInterface(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtil.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setCreateUser(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        Long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("/get/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<InterfaceInfo> getInterfaceById(@PathVariable("id") Long id) {
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该接口不存在");
        }
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 更新接口
     *
     * @param interfaceInfoUpdateRequest 更新接口数据
     * @param request 请求头
     * @return 修改结果
     */
    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> updateInterface(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoUpdateRequest.getId());
        BeanUtil.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 仅本人或管理员可修改
        User user = userService.getLoginUser(request);
        if (!interfaceInfo.getCreateUser().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success("修改成功");
    }

    /**
     * 分页查询接口信息
     *
     * @param queryRequest
     * @return
     */
    @ApiOperation(value = "查询")
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listInterface(@RequestBody InterfaceInfoQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(queryRequest, interfaceInfo);
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();
        String description = interfaceInfo.getDescription();
        // description需支持模糊搜索
        interfaceInfo.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 删除接口
     * @param deleteRequest 接口信息
     * @return 是否删除成功
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> deleteInterface(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        interfaceInfoService.removeById(deleteRequest.getId());
        return ResultUtils.success("删除成功");
    }

    /**
     * 发布接口
     *
     * @param idRequest 接口对应的id
     * @param request 请求信息
     * @return 上线结果
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterface(@RequestBody IdRequest idRequest,
                                                HttpServletRequest request) {
        // 判断请求信息是否合法
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        // 判断接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判断接口是否可以调用
        // TODO 判断真实数据
        com.qingmuy.muyapiclientsdk.model.User user = new com.qingmuy.muyapiclientsdk.model.User();
        user.setUsername("test");
        String username = muyApiClient.getUsernameByPost(user);
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }
        // 进本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterface(@RequestBody IdRequest idRequest,
                                                HttpServletRequest request) {
        // 判断请求信息是否合法
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        // 判断接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判断接口是否可以调用
        // TODO 判断真实数据
        com.qingmuy.muyapiclientsdk.model.User user = new com.qingmuy.muyapiclientsdk.model.User();
        user.setUsername("test");
        String username = muyApiClient.getUsernameByPost(user);
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }
        // 进本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试接口
     *
     * @param interfaceInfoInvokeRequest 接口信息
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                  HttpServletRequest request) {
        // 判断请求信息是否合法
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        // 判断接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (oldInterfaceInfo.getStatus() != InterfaceInfoStatusEnum.ONLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }
        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        // TODO： 从数据库校验ak，sk
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        MuyApiClient apiClient = new MuyApiClient(accessKey, secretKey);
        Gson gson = new Gson();
        com.qingmuy.muyapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.qingmuy.muyapiclientsdk.model.User.class);
        // TODO： 测试接口改为根据测试地址调用
        String result = apiClient.getUsernameByPost(user);
        return ResultUtils.success(result);
    }

}