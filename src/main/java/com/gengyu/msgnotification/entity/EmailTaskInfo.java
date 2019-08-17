package com.gengyu.msgnotification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Accessors(chain = true)    ///lombok的注解，可以set时连缀访问。
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class EmailTaskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username")
    private String username;

    /// 用户为每次邮件任务所起的名字，须唯一。
    @Column(name="taskName")
    private String taskName;

    /// 收件人列表，支持多人，中间以逗号隔开。
    @Column(name="toList")
    private String toList;

    /// 抄送
    @Column(name="ccList")
    private String ccList;

    /// 密送
    @Column(name="bccList")
    private String bccList;

    @Column(name="subject")
    private String subject;

    @Column(name="content")
    private String content;

    @Column(name="attachmentFileIds")
    private String attachmentFileIds;

    ///发邮件的起始时间
    @Column(name="timeToSend")
    private String timeToSend;

    ///发邮件间隔时间（小时）
    @Column(name="intervalTime")
    private Integer intervalTime;

    /*
    以下四个是定时任务需要的任务识别字段
     */
    @Column(name="jobName")
    private String jobName;

    @Column(name="jobGroupName")
    private String jobGroupName;

    @Column(name="triggerName")
    private String triggerName;

    @Column(name="triggerGroupName")
    private String triggerGroupName;

    /// 0即ON，代表启动状态，1即OFF，代表终止状态
    @Column(name="status")
    private Integer status;

}
