package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
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
     * 上传单个附件
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


    /**
     * 上传多个附件
     * @param request
     * @param username
     * @return
     */
    @PostMapping("/test02")
    public String uploadMulti(HttpServletRequest request, String username) throws IOException {

        if (null == request){
            return "请求参数为空！";
        }

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("attachment");
        if (CollectionUtils.isEmpty(files)){
            return "上传的附件为空，请选择合适的文件上传！";
        }

        List<MultipartFile> invalidFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.getSize() == 0){
                invalidFiles.add(file);
                log.info("======此文件没有，已移除！");
            }
        }

        log.info("invalidFiles的长度为:{}", invalidFiles.size());
        files.removeAll(invalidFiles);
        log.info("files移除invalid后的files的长度为:{}", files.size());

        return uploadService.uploadMultiFile(files, username);

    }


}
