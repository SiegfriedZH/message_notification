package com.gengyu.msgnotification.service;

import com.gengyu.msgnotification.config.EmailConfig;
import com.gengyu.msgnotification.entity.EmailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
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

    public void sendRichEmail (EmailInfo emailInfo) {

        try {
            MimeMessage mimeMessage = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String from = emailConfig.getFrom();
            helper.setFrom(from);
            helper.setSubject(emailInfo.getSubject());
            String content = "<html><head></head><body><h3 style=\"color:red\">"
                    + emailInfo.getContent()
                    +"</h3></body></html>";
            log.info("拼接好的邮件内容为:{}", content);
            helper.setText(content, true);

            String toListStr = emailInfo.getToList();

            /// 经测试，多个收件人的时候，不能直接helper.setTo(收件人列表数据)，而应该采用下面的方法
//            String[] toListArray = toListStr.split(";");
//            log.info("收件人列表的长度为:{}", toListArray.length);
//            helper.setTo(toListArray[0]);
//            helper.setTo(toListArray);

            InternetAddress[] sendTo = InternetAddress.parse(toListStr);
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, sendTo);

            jms.send(mimeMessage);

            log.info("已发送完毕！");
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }



}
