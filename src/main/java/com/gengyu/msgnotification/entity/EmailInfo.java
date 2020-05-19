package com.gengyu.msgnotification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "发邮件参数实体", description = "发邮件参数实体")
public class EmailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name="username")
    @ApiModelProperty(value = "用户名", name = "username", example = "张三", required = true)
    private String username;

    /// 用户为每次邮件任务所起的名字，须唯一。
    @Column(name="taskName")
    @ApiModelProperty(value = "任务名", name = "taskName", example = "task01", required = true)
    private String taskName;

    /// 收件人列表，支持多人，中间以逗号隔开。
    @Column(name="toList")
    @ApiModelProperty(value = "收件人列表", name = "toList", example = "张三,李四", required = true)
    private String toList;

    /// 抄送
    @Column(name="ccList")
    @ApiModelProperty(value = "抄送人列表", name = "ccList", example = "张三,李四", required = false)
    private String ccList;

    /// 密送
    @Column(name="bccList")
    @ApiModelProperty(value = "密送人列表", name = "bccList", example = "张三,李四", required = false)
    private String bccList;

    @Column(name="subject")
    @ApiModelProperty(value = "邮件标题", name = "subject", example = "这是标题", required = true)
    private String subject;

    @Column(name="content")
    @ApiModelProperty(value = "邮件正文", name = "content", required = true)
    private String content;

    @Column(name="attachmentFileIds")
    @ApiModelProperty(value = "上传附件列表", name = "attachmentFileIds", required = false)
    private String attachmentFileIds;

    ///发邮件的起始时间
    @Column(name="timeToSend")
    @ApiModelProperty(value = "邮件发送的起始时间", name = "timeToSend",required = true)
    private String timeToSend;

    ///发邮件间隔时间（小时）
    @Column(name="intervalTime")
    @ApiModelProperty(value = "邮件发送的间隔时间", name = "intervalTime",required = true)
    private Integer intervalTime;
}
