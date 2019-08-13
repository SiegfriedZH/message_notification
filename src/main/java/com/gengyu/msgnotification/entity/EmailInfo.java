package com.gengyu.msgnotification.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 0:48
 */
@Data
public class EmailInfo {

    /// 收件人列表，支持多人，中间以逗号隔开。
    private String toList;

    private String title;

    private String content;

    ///发邮件的起始时间
    private String timeToSend;

    ///发邮件间隔时间（小时）
    private Integer interval;
}
