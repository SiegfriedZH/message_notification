package com.gengyu.msgnotification.exception;

import com.gengyu.msgnotification.enums.ResultEnum;

/**
 * @author Siegfried GENG
 * @date 2019/8/18 - 15:47
 */
public class WechatException extends RuntimeException {

    private Integer code;

    public WechatException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public WechatException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
