package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 23:34
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传单个文件
     * @return
     */
    @PostMapping("/test01")
    public String uploadSingle(@RequestParam("attachment") MultipartFile file, String username){

        log.info("传入的username为:{}", username);
        if (null == file){
            return "上传的附件为空，请选择合适的文件上传！";
        }
        String fileId = uploadService.uploadSingleFile(file, username);

        return fileId;

    }

    @PostMapping("/test02")
    public String uploadMulti(HttpServletRequest request, String username){

        if (null == request){
            return "请求参数为空！";
        }

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("attachment");
        if (CollectionUtils.isEmpty(files)){
            return "上传的附件为空，请选择合适的文件上传！";
        }
        return uploadService.uploadMultiFile(files, username);

    }


}
