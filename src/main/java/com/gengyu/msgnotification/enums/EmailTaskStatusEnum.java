package com.gengyu.msgnotification.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author Siegfried GENG
 * @date 2019/8/14 - 20:00
 */
@Getter
public enum EmailTaskStatusEnum {

    ON(0, "on"),
    OFF(1, "off"),
    ;

    EmailTaskStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

}
