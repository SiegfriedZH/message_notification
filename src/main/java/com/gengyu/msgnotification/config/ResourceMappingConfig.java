package com.gengyu.msgnotification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceMappingConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将本地资源路径映射为网络路径
        //Windows下  此时前端访问路径：http://xx.xx.xx.xx:xx/uploads/文件名
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:D:/codes/message_notification/upload/");
        //Mac或Linux下(没有CDEF盘符)
//        registry.addResourceHandler("/uploads/**").addResourceLocations("file:/Users/uploads/");
    }
}
