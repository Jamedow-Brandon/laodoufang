package com.jamedow.laodoufang.common;


import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PageAspect {
    @Pointcut("execution(public * com.jamedow.laodoufang.service..*.queryPage*(..))")
    public void aopAspect() {
    }

    @Before("aopAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String limit = request.getParameter("page.limit");
        String offset = request.getParameter("page.offset");
        if (!StringUtils.isBlank(limit) && !StringUtils.isBlank(offset)) {
            PageHelper.startPage(Integer.valueOf(offset), Integer.valueOf(limit));
        }
    }
}
