package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.entity.ReturnResult;
import com.jamedow.laodoufang.service.ReflexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/26.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("test")
public class TestController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReflexService reflexService;

    @RequestMapping(value = "three", method = RequestMethod.GET)
    public String play() {
        return "test/three";
    }


    @RequestMapping(value = "method", method = RequestMethod.GET)
    @ResponseBody
    public void startMethod() {

        Recipe recipe = new Recipe();
        recipe.setName("");
        recipe.setIsOfficial("1");
        try {
            ReturnResult returnResult = reflexService.implementsMethod("checkNameAndIsDeleted", recipe);
            if (!returnResult.isSuccess()) {
                logger.info(returnResult.getMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
