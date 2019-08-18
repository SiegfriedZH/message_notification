package com.gengyu.msgnotification.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gengyu.msgnotification.config.WeChatConfig;
import com.gengyu.msgnotification.entity.OpenIdEntity;
import com.gengyu.msgnotification.enums.OpenIdStatusEnum;
import com.gengyu.msgnotification.enums.ResultEnum;
import com.gengyu.msgnotification.exception.WechatException;
import com.gengyu.msgnotification.repository.OpenIdRepository;
import com.gengyu.msgnotification.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:10
 */
@Service
@Slf4j
public class WeChatService {

    private static String ACCESS_TOKEN = "";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private OpenIdRepository openIdRepository;

    /**
     * 真实的获取openId的方法
     * @param code
     * @return
     */
    public String getRealOpenId(String code){

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid=" + weChatConfig.getMpAppId()
                + "secret=" + weChatConfig.getMpAppSecret()
                + "&code=" + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
        JSONObject resultJson = JSONObject.parseObject(response);
        String openid = resultJson.getString("openid");

        /// 入库
        this.saveOpenIdEntity(openid);

        return openid;
    }

    /**
     * 模拟调接口去获取用户openId，每隔5分钟过期！
     * @return
     */
    public String generateOpenId(){

        /// 真正的openId是28位的，如"oDyjy0pvqxQMV66D7rPzekfxPOUg","oDyjy0hVQSWUU4Np3isCFPy_zC2U"
        String openId = StringUtil.generateSingleStr(28);
        /// 入库
        this.saveOpenIdEntity(openId);
        return openId;
    }

    private void saveOpenIdEntity(String openId){
        OpenIdEntity openIdEntity = new OpenIdEntity();
        openIdEntity.setOpenId(openId).setStatus(OpenIdStatusEnum.OPENID_STATUS_ENUM_ACTIVE.getCode());
        openIdRepository.save(openIdEntity);
        log.info("存入的OPEN_IDs为:{}", openIdEntity);
    }

    /**
     * 公众号的AccessToken每隔两小时失效，所以要定期去获取
     * 这个方法是真实的！
     * @return
     */
//    @Scheduled(initialDelay = 2000, fixedDelay = 10000)
    public String getAccessToken(){

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                weChatConfig.getMpAppId() + "&secret=" + weChatConfig.getMpAppSecret();
        log.info("拼接好的获取access_token的url为：{}", url);
        String response = restTemplate.getForObject(url, String.class);
//        log.info("====得到的resposne为:{}", response);

        JSONObject resultJson = JSONObject.parseObject(response);
        String accessToken = resultJson.getString("access_token");

        ACCESS_TOKEN = accessToken;
//        log.info("现在的OPEN_IDs为:{}", OPEN_IDs);
        log.info("现在的ACCESS_TOKEN为：{}", ACCESS_TOKEN);

        return accessToken;
    }

    /**
     * 模拟发消息
     * 这里需要三个参数，自己的access_token，OPEN_IDs，text
     * @param text
     * @return
     */
    public String sendMsg(String text){

        /// 需要先校验前两个参数是不是为空，若为空，须先调用前两个方法去获取。
        if(StringUtils.isEmpty(ACCESS_TOKEN)){
            log.info("ACCESS_TOKEN为空，现在去获取");
            ACCESS_TOKEN = this.getAccessToken();
            log.info("获取到的ACCESS_TOKEN为:{}", ACCESS_TOKEN);

            if (StringUtils.isEmpty(ACCESS_TOKEN)){
                log.info("无法获取ACCESS_TOKEN");
                return "无法获取ACCESS_TOKEN";
            }
        }

        List<OpenIdEntity> openIdList = openIdRepository.findAll();
        log.info("现在库里的openId列表长度为:{}，list为:{}", openIdList.size(), openIdList);

        if(CollectionUtils.isEmpty(openIdList)){
            throw new WechatException(ResultEnum.RESULT_ENUM_OPENID_NULL);
        } else if(openIdList.size() == 1){
            throw new WechatException(ResultEnum.RESULT_ENUM_OPENID_ONLYONE);
        }

        /// 从openIdList取出所有openId字段，转为数组，因为底下的发消息接口的入参需要的数组。
        List<String> openIdStrList = openIdList.stream().map(e -> e.getOpenId()).collect(Collectors.toList());
        String[] openIds = openIdStrList.toArray(new String[]{});

        log.info("转为数组的openIds为：{}, 该数组的长度为:{}", openIds, openIds.length);

        String resp = "";

        try {
            String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+ ACCESS_TOKEN;

            // 构造httprequest设置
            HttpClient client = new HttpClient();
            PostMethod request = new PostMethod(reqUrl);

            // 添加request headers
            request.addRequestHeader("Content-type", "application/json");
            request.addRequestHeader("Accept", "application/json");

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("touser", openIds);   /// 这里使用数组
            param.put("msgtype", "text");
            Map<String, Object> content =  new HashMap<String, Object>();
            content.put("content", text);
            param.put("text",content);

//            String jsonStr = new Gson().toJson(param);

            /// 使用FastJson
            String jsonStr = JSON.toJSONString(param);
            log.info("jsonStr为：{}", jsonStr);
            request.setRequestEntity(new ByteArrayRequestEntity(jsonStr.getBytes("UTF-8")));

            client.executeMethod(request);
            resp = request.getResponseBodyAsString();
//            System.out.println(resp);
            log.info("resp为:{}", resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

}
