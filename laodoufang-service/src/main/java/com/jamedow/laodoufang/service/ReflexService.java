//package com.jamedow.laodoufang.service;
//
//import com.jamedow.laodoufang.entity.*;
//import com.jamedow.laodoufang.mapper.MethodConfigMapper;
//import com.jamedow.laodoufang.mapper.MethodConfigRelMapper;
//import com.jamedow.laodoufang.mapper.MethodInfoMapper;
//import com.jamedow.laodoufang.system.bean.ReturnResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ReflexService<T> {
//    ReturnResult returnResult = new ReturnResult();
//    private Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    private MethodConfigMapper methodConfigMapper;
//    @Autowired
//    private MethodConfigRelMapper methodConfigRelMapper;
//    @Autowired
//    private MethodInfoMapper methodInfoMapper;
//
//    public ReturnResult implementsMethod(String methodCode, T t) throws Exception {
//        MethodConfigExample methodConfigExample = new MethodConfigExample();
//        methodConfigExample.createCriteria().andMethodCodeEqualTo(methodCode);
//        List<MethodConfig> methodConfigList = methodConfigMapper.selectByExample(methodConfigExample);
//        if (ObjectUtils.isEmpty(methodConfigList)) {
//            throw new Exception("方法不存在");
//        }
//        MethodConfig methodConfig = methodConfigList.get(0);
//        logger.info("=======方法[{}]开始执行=======", methodConfig.getMethodName());
//        MethodConfigRelExample methodConfigRelExample = new MethodConfigRelExample();
//        methodConfigRelExample.createCriteria().andConfidIdEqualTo(methodConfig.getId());
//        List<MethodConfigRel> methodConfigRelList = methodConfigRelMapper.selectByExample(methodConfigRelExample);
//        if (ObjectUtils.isEmpty(methodConfigRelList)) {
//            throw new Exception("方法没有关联子方法");
//        }
//        List<Long> methodIds = new ArrayList<>();
//        for (MethodConfigRel rel : methodConfigRelList) {
//            methodIds.add(rel.getMethodId());
//        }
//        MethodInfoExample methodInfoExample = new MethodInfoExample();
//        methodInfoExample.createCriteria().andIdIn(methodIds);
//        List<MethodInfo> methodInfoList = methodInfoMapper.selectByExample(methodInfoExample);
//        if (ObjectUtils.isEmpty(methodInfoList)) {
//            throw new Exception("方法没有关联子方法");
//        }
//        for (MethodInfo methodInfo : methodInfoList) {
//            Class<?> checkClass = Class.forName(methodInfo.getClassPosition());
//            Method method = checkClass.getMethod(methodInfo.getMethodCode(), Object.class);
//            returnResult = (ReturnResult) method.invoke(checkClass.newInstance(), t);
//            if (!returnResult.isSuccess()) {
//                break;
//            }
//        }
//
//
//        return returnResult;
//    }
//
//
//}
