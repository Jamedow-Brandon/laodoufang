package com.jamedow.laodoufang.common;


import com.jamedow.laodoufang.config.ProducerMethods;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.jamedow.laodoufang.config.ProducerMethods)")
    public void aopAspect() {
    }

    @Around("aopAspect()")
    public void advice(ProceedingJoinPoint joinPoint) {
//        System.out.println("环绕通知之开始");
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
//        System.out.println("环绕通知之结束");
    }

//    @Before(value = "aopAspect()&&@annotation(requestMethods)")
//    public void doBefore(JoinPoint joinPoint, ConsumerMethods requestMethods) {
//        applicationContext.publishEvent(requestMethods.value());
//    }

    @After(value = "aopAspect()&&@annotation(producerMethods)")
    public void after(JoinPoint joinPoint, ProducerMethods producerMethods) throws IllegalAccessException, InstantiationException {
        applicationContext.publishEvent(producerMethods.value().newInstance());
    }

}
