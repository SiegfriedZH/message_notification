package com.gengyu.msgnotification.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:46
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeChatConfig {

    private String mpAppId;

    private String mpAppSecret;
}
