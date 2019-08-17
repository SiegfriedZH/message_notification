package com.gengyu.msgnotification.repository;

import com.gengyu.msgnotification.entity.UploadFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siegfried GENG
 * @date 2019/8/17 - 15:41
 */
public interface UploadFileRepository extends JpaRepository<UploadFileEntity, Integer>{

    UploadFileEntity findByFileId(String fileId);

}
