package com.qingmuy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingmuy.annotation.AuthCheck;
import com.qingmuy.common.BaseResponse;
import com.qingmuy.common.DeleteRequest;
import com.qingmuy.common.ErrorCode;
import com.qingmuy.common.ResultUtils;
import com.qingmuy.constant.CommonConstant;
import com.qingmuy.constant.UserConstant;
import com.qingmuy.exception.BusinessException;
import com.qingmuy.exception.ThrowUtils;
import com.qingmuy.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.qingmuy.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.qingmuy.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.qingmuy.model.entity.UserInterfaceInfo;
import com.qingmuy.service.UserInterfaceInfoService;
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
@RequestMapping("/userinterfaceInfo")
public class UserInterfaceInfoController {

    @Resource
    private UserService userService;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 创建接口
     * @param userInterfaceInfoAddRequest 接口信息
     * @return 是否添加成功
     */
    @ApiOperation(value = "添加")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addInterface(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtil.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        // 校验
        userInterfaceInfoService.validInterfaceInfo(userInterfaceInfo, true);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        Long newInterfaceInfoId = userInterfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 更新接口
     *
     * @param userInterfaceInfoUpdateRequest 更新接口数据
     * @param request 请求头
     * @return 修改结果
     */
    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> updateInterface(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest,
                                                HttpServletRequest request) {
        if (userInterfaceInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(userInterfaceInfoUpdateRequest.getId());
        BeanUtil.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success("修改成功");
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
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(userInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        userInterfaceInfoService.removeById(deleteRequest.getId());
        return ResultUtils.success("删除成功");
    }

    /**
     * 根据id查询
     * @param id 信息id
     * @return 用户订单信息
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/get/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getInterfaceById(@PathVariable("id") Long id) {
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该接口不存在");
        }
        return ResultUtils.success(userInterfaceInfo);
    }

    /**
     * 分页查询接口信息
     *
     * @param queryRequest 请求信息
     * @return 查询结果
     */
    @ApiOperation(value = "查询")
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listInterface(@RequestBody UserInterfaceInfoQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(queryRequest, userInterfaceInfo);
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfo);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userInterfaceInfoPage);
    }



}