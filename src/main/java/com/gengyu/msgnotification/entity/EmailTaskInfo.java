package com.gengyu.msgnotification.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 22:39
 */
@Data
@Table(name="email_task_info")
@Entity
public class EmailTaskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// 用户为每次邮件任务所起的名字，须唯一。
    private String taskName;

    /// 收件人列表，支持多人，中间以逗号隔开。
    private String toList;

    /// 抄送
    private String ccList;

    /// 密送
    private String BccList;

    private String subject;

    private String content;

    ///发邮件的起始时间
    private String timeToSend;

    ///发邮件间隔时间（小时）
    private Integer interval;

    /*
    以下四个是定时任务需要的任务识别字段
     */
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;

}
