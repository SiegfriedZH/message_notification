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
@Table(name="upload_file")
@Entity
@Accessors(chain = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class UploadFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// 上传人
    @Column(name="username")
    private String username;

    /// 上传时的原始文件名
    @Column(name="file_name_original")
    private String fileNameOriginal;

    /// 经加工后保存的文件名
    @Column(name="file_name_saved")
    private String fileNameSaved;

    /// 生成的文件id
    @Column(name="file_id")
    private String fileId;

    /// 文件的保存路径
    @Column(name="file_path")
    private String filePath;

}
