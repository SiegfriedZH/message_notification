package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:09
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 模拟获取openId。（群发消息，用户数必须大于等于2，否则报错）
     * @return
     */
    @GetMapping("/openid")
    public String getOpenId(){
        return weChatService.generateOpenId();
    }

    /**
     * 获取accesstoken。真实方法。
     * 若请求不成功，原因很可能是微信公众平台上，它识别我们的ip地址出错，到公众平台里把它识别出来的ip加到白名单里即可。
     * @return
     */
    @GetMapping("/getaccesstoken")
    public String getAccessToken(){
        return weChatService.getAccessToken();
    }


    /**
     * 群发消息接口，在请求体中直接写入内容即可。
     * 现在返回值是{"errcode":48001,"errmsg":"api unauthorized hint: [jE0L05931528!]"}
     * 就代表正常。（因为个人订阅号没有此接口调用权限）
     * @param text
     * @return
     */
    @PostMapping("/sendmessage")
    public String sendMsgTest(@RequestBody String text){
        log.info("传入的text内容为:{}", text);
        return weChatService.sendMsg("text");
    }

}
