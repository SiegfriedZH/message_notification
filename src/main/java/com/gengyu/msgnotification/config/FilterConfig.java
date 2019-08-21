package com.gengyu.msgnotification.config;

import com.gengyu.msgnotification.filters.EncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Siegfried GENG
 * @date 2019/8/20 - 10:25
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new EncodingFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/wechat/auth"));
        return filterRegistrationBean;
    }

}
