package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:09
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    // 本类里的所有方法都为模拟，因为个人订阅号根本没有权限进行所有必要操作。

    @Autowired
    private WeChatService weChatService;

    /**
     * 模拟获取openId列表。（群发消息，用户数必须大于等于2，否则报错）
     * @return
     */
    @GetMapping("/openid")
    public List<String> getOpenId(){
        return weChatService.generateOpenIds();
    }

    /**
     * 获取accesstoken。真实方法。
     * @return
     */
    @GetMapping("/getaccesstoken")
    public String getAccessToken(){
        String accessToken = weChatService.getAccessToken();
        return accessToken;
    }

    @GetMapping("/test01")
    public String sendMsgTest(){
        String result = weChatService.sendMsg("啊啊啊");
        return result;

    }

}
