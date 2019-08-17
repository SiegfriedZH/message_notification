package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 10:56
 */

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileSerivce;

    @GetMapping("/test01")
    public String getImg(){
        return "/static/img123.jpg";
    }
}
