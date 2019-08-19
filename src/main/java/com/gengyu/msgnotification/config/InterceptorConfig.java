package com.gengyu.msgnotification.config;

import com.gengyu.msgnotification.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring5以前是继承WebMvcConfigurerAdapter，
 * 现在声明为过时，改成了implements WebMvcConfigurer
 * @author Siegfried GENG
 * @date 2019/8/20 - 0:36
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/wechat/auth");
    }
}
