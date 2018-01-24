package com.jamedow.laodoufang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Date: 2017/3/26</p>
 *
 * @author XN
 * @version 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "error";
    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        logger.error(e.getMessage(), e);
        return mav;
    }
}
