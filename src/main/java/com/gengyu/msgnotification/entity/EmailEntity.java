package com.gengyu.msgnotification.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;

/**
 * @author Siegfried GENG
 * @date 2019/8/12 - 22:24
 */
@Entity
@Table
@Data
//@DynamicUpdate
public class EmailEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String content;
    private String from;
    private String to;

}
