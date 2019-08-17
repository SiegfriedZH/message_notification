package com.gengyu.msgnotification.service;

import com.gengyu.msgnotification.entity.UploadFileEntity;
import com.gengyu.msgnotification.repository.UploadFileRepository;
import com.gengyu.msgnotification.utils.DateUtil;
import com.gengyu.msgnotification.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author Siegfried GENG
 * @date 2019/8/17 - 15:02
 */
@Service
@Slf4j
public class UploadService {

    public static String ROOT_PATH = "";

    @Autowired
    private UploadFileRepository uploadFileRepository;

    static{
//        String path1 = "";
//        try {
//            path1 = ResourceUtils.getURL("classpath:").getPath();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        log.info("得到的项目路径2为:{}", path1);
//        rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//      //得到的项目路径1为:/D:/codes/msgnotification/target/classes/
        ROOT_PATH = System.getProperty("user.dir");  /// 得到的项目路径1为:D:\codes\msgnotification
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>得到的项目路径为:{}", ROOT_PATH);
        /// 项目打成jar包运行后，这个路径为jar包所在目录，即target。
    }

    /**
     * 上传单个文件
     * @param file
     * @param username
     * @return
     */
    public String uploadSingleFile(MultipartFile file, String username){

        String fileNameOriginal = file.getOriginalFilename();
//        String filePath = rootPath + "/static/";

        /// 保存的新名字
        String fileNameSaved = username + "_" + System.currentTimeMillis() + "_" + fileNameOriginal;

        String localFilePath = ROOT_PATH + File.separator +"upload" + File.separator + DateUtil.getDateStr();
        log.info("创建/找到的本地上传目录为：{}",localFilePath);

        File localFile = new File(localFilePath);
        if (!localFile.exists()){
            log.info("目录不存在，现在创建！");
            localFile.mkdirs();
        }

        String localFilePathName = localFilePath + File.separator + fileNameSaved;
        File destLocal = new File(localFilePathName);
        log.info("上传目的地为：{}", destLocal);

        try {
            file.transferTo(destLocal); /// 上传
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.toString(), e);
            return "上传失败！";
        }

        /// 上传成功后，将数据入库，返回唯一识别标识fileId
        return this.saveUploadFile(username, fileNameOriginal, fileNameSaved, localFilePathName);
    }

    private String saveUploadFile(String username, String fileNameOriginal, String fileNameSaved, String filePathName){

        UploadFileEntity ufEntity = new UploadFileEntity();

        /// 构建每个文件的唯一标识id,规则是：当前系统时间戳_随机生成5位字符串
        String fileId = System.currentTimeMillis() + "_" + StringUtil.generateSingleStr(5);

        ufEntity.setUsername(username)
                .setFileNameOriginal(fileNameOriginal)
                .setFileNameSaved(fileNameSaved)
                .setFileId(fileId)
                .setFilePath(filePathName);
        uploadFileRepository.save(ufEntity);
        log.info("构建好的UploadFileEntity对象为:{}，已将其保存入库！", ufEntity);

        return fileId;
    }


    /**
     * 上传多个附件
     * @param files
     * @param username
     * @return
     */
    public String uploadMultiFile(List<MultipartFile> files, String username){

        StringBuffer sb = new StringBuffer();

        for (MultipartFile file : files) {
            sb.append("/").append(this.uploadSingleFile(file, username));
        }

        String result = sb.toString().substring(1);
        log.info("拼接好的uploadFileIds字符串为：" + result);
        return result;
    }

}
