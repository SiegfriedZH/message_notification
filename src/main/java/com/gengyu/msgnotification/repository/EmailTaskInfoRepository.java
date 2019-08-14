package com.gengyu.msgnotification.repository;

import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.entity.EmailTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 22:16
 */
public interface EmailTaskInfoRepository extends JpaRepository<EmailTaskInfo, Integer>{

    EmailTaskInfo findByTaskName(String taskName);

    EmailTaskInfo findByJobName(String jobName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE email_task_info e SET e.status=?2 WHERE e.id = ?1"
            ,nativeQuery = true)
    Integer cancelEmailTask(Integer id, Integer toStatus);


}
