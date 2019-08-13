package com.gengyu.msgnotification.timedJob;

import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.validation.constraints.Email;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 11:25
 */
@Slf4j
public class MyJob2 implements Job {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        String toList = jobDataMap.getString("toList");
//        String subject = jobDataMap.getString("subject");
//        String content = jobDataMap.getString("content");
//        log.info("===========传到Job2里的EmailInfo对象为:{},{},{}",toList, subject, content);

        EmailInfo emailInfo = (EmailInfo) jobDataMap.get("emailInfo");
        log.info("====Job2得到的EmailInfo为：{}", emailInfo);

        System.out.println("现在去获取EmailService实例！");
        /// 调用MyJob中的静态方法获取EmailService实例
        EmailService emailService = MyJob.getEmailServiceInstance(jobExecutionContext);
        System.out.println("已获取EmailService实例！" + emailService);
//        emailService.sendPlainEmail();
        log.info("现在调用发邮件。。。");
        emailService.sendRichEmail(emailInfo);

        log.info("调用发邮件完毕！");

    }
}
