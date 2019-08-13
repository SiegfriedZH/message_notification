package com.gengyu.msgnotification.utils;

import com.gengyu.msgnotification.entity.ResponseEntity;

/**
 * @author Siegfried GENG
 * @date 2019/8/12 - 22:45
 */
public class ResultUtil {

    public static ResponseEntity succeeded(Object data){

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(0);
        responseEntity.setMsg("成功");
        responseEntity.setData(data);

        return responseEntity;
    }

    public static ResponseEntity failed(){

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(1);
        responseEntity.setMsg("失败");
        return responseEntity;
    }

}
