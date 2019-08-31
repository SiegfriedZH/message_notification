package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:09
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
@Api(value="微信功能相关接口")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 真实的获取openId方法
     * 这个接口不能直接请求，只能在微信里通过用户访问重定向的方式请求。格式如下：
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd93bf860fc21b36d&
     * redirect_uri=192.168.0.101/wechat/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
     * 但一般的个人订阅号没有此权限，死心吧！
     *
     * 另：结合中文乱码问题，目前GET方式怎么避免乱码，在SpringBoot中不好做，所以改成POST方式！
     * 但是POST方式，用body体里的text方式传参，又接收不到，所以最后还是要改成GET方式！实际也不会出现中文。
     * @param code
     */
    @GetMapping("/auth")
    public String auth(@RequestParam String code) throws UnsupportedEncodingException {
        String codeDecoded = URLDecoder.decode(code, "UTF-8");
        log.info("传入的code为:{}", codeDecoded);
        return "啊啊啊";
//        return weChatService.getRealOpenId(code);
    }

    /**
     * 模拟获取openId。
     * 其实就是用户一点击关注，引导他到一个事先构造好的url，然后就会重定向到这个请求，并携带code，再用code去请求openId。
     * @return
     */
    @ApiOperation(value = "获取openId", response = String.class)
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
