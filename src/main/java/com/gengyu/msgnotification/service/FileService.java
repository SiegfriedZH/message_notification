package com.gengyu.msgnotification.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 10:39
 */

@Service
public class FileService {

    public String getFilePath(){
        File file = new File("img123.jpg");
        String absolutePath = file.getAbsolutePath();
        String canonicalPath = "";
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return absolutePath+"\n"+canonicalPath;

    }

}
