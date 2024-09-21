package com.qingmuy.muyapiclientsdk.aop;

import com.qingmuy.muyapiclientsdk.MuyApiClientConfig;
import com.qingmuy.muyapiclientsdk.common.ErrorCode;
import com.qingmuy.muyapiclientsdk.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP
 */
@Aspect
@Component
public class SourceInterceptor {

    public static final String SOURCE = "source";

    @Resource
    MuyApiClientConfig muyApiClientConfig;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @return
     */
    @Around("@annotation(com.qingmuy.muyapiclientsdk.annotation.SourceCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取请求的来源，若无直接返回
        String source = request.getHeader(SOURCE);
        if (source == null || source.isEmpty()) {
            System.out.println("没有来源标识");
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 必须有该权限才通过
        String sourceKey = muyApiClientConfig.getSourceKey();
        if (!sourceKey.equals(source)) {
            System.out.println("来源标识异常" + source);
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

