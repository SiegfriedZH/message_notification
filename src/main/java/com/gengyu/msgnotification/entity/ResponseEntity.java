package com.gengyu.msgnotification.entity;

import lombok.Data;

/**
 * @author Siegfried GENG
 * @date 2019/8/12 - 22:37
 */
@Data
public class ResponseEntity<T> {

    private Integer code;
    private String msg;
    private T data;

}
