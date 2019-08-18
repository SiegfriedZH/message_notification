package com.gengyu.msgnotification.enums;

import lombok.Getter;

/**
 * @author Siegfried GENG
 * @date 2019/8/14 - 20:00
 */
@Getter
public enum OpenIdStatusEnum {

    OPENID_STATUS_ENUM_ACTIVE(0, "active"),
    OPENID_STATUS_ENUM_INACTIVE(1, "inactive"),
    ;

    OpenIdStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

}
