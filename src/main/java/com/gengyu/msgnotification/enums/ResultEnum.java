package com.gengyu.msgnotification.enums;

import lombok.Getter;

/**
 * @author Siegfried GENG
 * @date 2019/8/18 - 15:51
 */
@Getter
public enum ResultEnum {

    RESULT_ENUM_OPENID_NULL(0, "openId列表为空，无人关注，无法发送消息"),

    RESULT_ENUM_OPENID_ONLYONE(1, "openId列表只有一个用户，无法发送消息"),

    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
