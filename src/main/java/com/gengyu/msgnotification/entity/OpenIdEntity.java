package com.gengyu.msgnotification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 0:48
 */
@Data
@Table(name="open_id")
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Accessors(chain = true)
public class OpenIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="openId")
    private String openId;

    /// 状态，正常为0，如果用户取关了，为1
    @Column(name="status")
    private Integer status;

}
