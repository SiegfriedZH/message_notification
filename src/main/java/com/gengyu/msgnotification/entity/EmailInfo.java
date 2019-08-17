package com.gengyu.msgnotification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 0:48
 */
@Data
@Table(name="email_info")
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class EmailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column()
    private String attachmentFileIds;

    ///发邮件的起始时间
    @Column(name="timeToSend")
    private String timeToSend;

    ///发邮件间隔时间（小时）
    @Column(name="intervalTime")
    private Integer intervalTime;
}
