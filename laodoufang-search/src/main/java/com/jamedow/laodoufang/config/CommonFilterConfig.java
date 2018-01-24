package com.jamedow.laodoufang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Date: 2017/2/13</p>
 *
 * @author XN
 * @version 1.0
 */
@Configuration
public class CommonFilterConfig {

    /**
     * utf8字符集过滤器
     *
     * @return
     */
    @Bean
    public CharacterEncodingFilter getEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}
