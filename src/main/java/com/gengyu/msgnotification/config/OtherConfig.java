package com.gengyu.msgnotification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 15:04
 */
@Configuration
public class OtherConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
