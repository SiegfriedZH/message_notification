package com.gengyu.msgnotification.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 0:13
 */
@Component
@Data
//@ConfigurationProperties(prefix = "email")
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String from;

//    private String to;

}
