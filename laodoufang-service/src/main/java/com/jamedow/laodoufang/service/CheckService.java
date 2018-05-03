package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.entity.ReturnResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckService<T> {
    ReturnResult returnResult = new ReturnResult();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ReturnResult checkName(T t) throws Exception {
        returnResult.setSuccess(true);
        Recipe recipe = (Recipe) t;
        logger.info("=======check name start===========");
        if (StringUtils.isBlank(recipe.getName())) {
            returnResult.setSuccess(false);
            returnResult.setMsg("名称不能为空！");
        }

        logger.info("=======check name end===========");
        return returnResult;
    }

    public ReturnResult checkParams(T t) throws Exception {
        returnResult.setSuccess(true);
        Recipe recipe = (Recipe) t;
        logger.info("=======check params start===========");
        logger.info("=======check params end===========");
        return returnResult;
    }

    public ReturnResult checkIsDeleted(T t) throws Exception {
        returnResult.setSuccess(true);
        Recipe recipe = (Recipe) t;
        logger.info("=======check isDeleted start===========");
        if (recipe.getIsOfficial().equals("1")) {
            returnResult.setSuccess(false);
            returnResult.setMsg("该食谱已被删除！");
        }
        logger.info("=======check isDeleted  end===========");
        return returnResult;
    }
}
