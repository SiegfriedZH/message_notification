package com.gengyu.msgnotification.service;

import com.gengyu.msgnotification.config.EmailConfig;
import com.gengyu.msgnotification.controller.UploadController;
import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.entity.EmailTaskInfo;
import com.gengyu.msgnotification.entity.UploadFileEntity;
import com.gengyu.msgnotification.enums.EmailTaskStatusEnum;
import com.gengyu.msgnotification.repository.EmailTaskInfoRepository;
import com.gengyu.msgnotification.repository.UploadFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 发邮件的具体功能
 * @author Siegfried GENG
 * @date 2019/8/13 - 0:17
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    JavaMailSender jms;

    @Autowired
    EmailConfig emailConfig;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private EmailTaskInfoRepository emailTaskInfoRepository;

    /**
     * 普通文本邮件，实际不会用到
     */
    public void sendPlainEmail(){
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        String from = emailConfig.getFrom();
        log.info("读取到的from：{}",from);
//        mainMessage.setFrom("siegfried_G@163.com");
        mainMessage.setFrom(from);
        //接收者
        mainMessage.setTo("bachbrahms@163.com");
        //发送的标题
        mainMessage.setSubject("发送的标题");
        //发送的内容
        mainMessage.setText("发送的内容");
        jms.send(mainMessage);
    }

    /**
     * 富文本，可带附件的邮件，实际会用到
     * @param emailInfo
     */
    public void sendRichEmail (EmailInfo emailInfo) {

        try {
            MimeMessage mimeMessage = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String from = emailConfig.getFrom();
            helper.setFrom(from);
            helper.setSubject(emailInfo.getSubject());

            String content = "<html><head></head><body><h3 style=\"color:red\">" + emailInfo.getContent() +"</h3></body></html>";
            log.info("拼接好的邮件内容为:{}", content);
            helper.setText(content, true);

            /// 经测试，多个收件人的时候，不能直接helper.setTo(收件人列表数据)，而应该采用下面的方法
//            String[] toListArray = toListStr.split(";");
//            log.info("收件人列表的长度为:{}", toListArray.length);
//            helper.setTo(toListArray[0]);
//            helper.setTo(toListArray);
            String toListStr = emailInfo.getToList();
            InternetAddress[] sendTo = InternetAddress.parse(toListStr);
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, sendTo);

            /// 抄送给自己，才能不报错
            helper.setCc(new InternetAddress(emailConfig.getFrom()));

            String ccList = emailInfo.getCcList();
            if (!StringUtils.isEmpty(ccList)){
                String[] ccListArray = ccList.split(";");
                helper.setCc(ccListArray);
            }

            String bccList = emailInfo.getBccList();
            if (!StringUtils.isEmpty(bccList)){
                String[] bccListArray = bccList.split(";");
                helper.setCc(bccListArray);
            }

            /// 添加附件
            String attachmentFileIds = emailInfo.getAttachmentFileIds();
            if (!StringUtils.isEmpty(attachmentFileIds)){
                String[] fileIds = attachmentFileIds.split("/");    /// 多个附件id，中间用/分开！！
                for (int i = 0; i < fileIds.length; i++) {
                    String fileId = fileIds[i];
                    UploadFileEntity file = uploadFileRepository.findByFileId(fileId);
                    if (null != file){
                        helper.addAttachment(file.getFileNameOriginal(), new File(file.getFilePath()));
                        log.info(">>>>>>>>>>>>找到一个附件，已添加");
                    }
                }
            }

            jms.send(mimeMessage);
            log.info("已发送完毕！");


        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 测试带附件的邮件发送。
     * 已测试成功。
     */
    public void sendEmailWithAttachment(){

        MimeMessage mimeMessage = jms.createMimeMessage();

        try {
            log.info("=======现在开始构造邮件内容！！！");

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(emailConfig.getFrom());
            helper.setSubject("邮件主题(包含附件)");

            /// 加上图片内容后，就会报Failed messages: com.sun.mail.smtp.SMTPSendFailedException: 554
            /// 可能是被当成垃圾邮件。没有这个内容就不报错！
            String content = "<html><head></head><body><h3 style=\"color:red\">"
                    + "去你妹的！！！" +"</h3></body></html>"
//                    + "测试图片<br/><a href='http://https://ask.csdn.net/questions/751932'>" +
//                    "<img src='http://a.hiphotos.baidu.com/image/h%3D300" +
//                    "/sign=a62e824376d98d1069d40a31113eb807" +
//                    "/838ba61ea8d3fd1fc9c7b6853a4e251f94ca5f46.jpg'/>"
                    ;
            helper.setText(content, true);
            helper.setTo(InternetAddress.parse("bachbrahms@163.com,642447423@qq.com"));

            /// 老报这个错Failed messages: com.sun.mail.smtp.SMTPSendFailedException: 554
            /// 搜了说把自己设为抄送人可能能解决。经测试，有时有用，有时没用，仍会报错
            helper.setCc(emailConfig.getFrom());

//            String attachmentName = "d:/test/111.jpg";
//            String attachmentName = "d:/test/abc.txt";

//            String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            String rootPath = UploadService.ROOT_PATH;

            /// 这里怎么获取文件名？？？？

            /// helper本身就可以添加附件，不用这么麻烦
            helper.addAttachment(
//                    attachmentName.substring(attachmentName.lastIndexOf("/")+1),
                    "附件图片.jpg",
//                    new FileDataSource(attachmentName)
                    new FileDataSource(rootPath + "/static/img123.jpg")
                    );

            jms.send(mimeMessage);
            log.info("=======已发送完毕！");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将EmailInfo转为EmailTaskInfo并入库
     * @param emailInfo
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void saveEmailTask(EmailInfo emailInfo,
                              String jobName, String jobGroupName, String triggerName, String triggerGroupName){
        EmailTaskInfo emailTaskInfo = new EmailTaskInfo();
        BeanUtils.copyProperties(emailInfo, emailTaskInfo);
        emailTaskInfo.setJobName(jobName)
                .setJobGroupName(jobGroupName)
                .setTriggerName(triggerName)
                .setTriggerGroupName(triggerGroupName)
                .setStatus(EmailTaskStatusEnum.EMAIL_TASK_STATUS_ENUM_ON.getCode());  /// 因为这是启动状态的任务，所以设置为0
        log.info("========入库前包装好的emailTaskInfo对象为:{}", emailTaskInfo);
        emailTaskInfoRepository.save(emailTaskInfo);
        log.info("该EmailTaskInfo已入库");
    }

}
