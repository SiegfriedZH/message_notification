package com.gengyu.msgnotification.repository;

import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.entity.EmailTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 22:16
 */
public interface EmailTaskInfoRepository extends JpaRepository<EmailTaskInfo, Integer>{

    EmailTaskInfo findByTaskName(String taskName);
}
