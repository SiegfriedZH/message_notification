package com.gengyu.msgnotification.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class ResourceMappingConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将本地资源路径映射为网络路径
        //Windows下
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:D:/codes/message_notification/upload/");
        //Mac或Linux下(没有CDEF盘符)
//        registry.addResourceHandler("/uploads/**").addResourceLocations("file:/Users/uploads/");
//        super.addResourceHandlers(registry);
    }
}
