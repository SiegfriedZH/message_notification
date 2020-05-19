package com.gengyu.msgnotification.timedJob;

import com.gengyu.msgnotification.service.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 11:25
 */
public class MyJob implements Job {

//    @Autowired
//    private EmailService emailService;

    /**
     * Job的坑就是无法自动注入其他组件，必须以下述方式才行。
     * @param jobContext
     * @return
     */
    public static EmailService getEmailServiceInstance(JobExecutionContext jobContext){
        ServletContext context = null;
        try {
            context = (ServletContext)jobContext.getScheduler().getContext()
                    .get(QuartzServletContextListener.MY_CONTEXT_NAME);
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }
        WebApplicationContext cxt = (WebApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        return (EmailService) cxt.getBean("emailService");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("JOB1--去你妹的！");
        /*System.out.println("现在去获取EmailService实例！");
        EmailService emailService = getEmailServiceInstance(jobExecutionContext);
        System.out.println("已获取EmailService实例！");*/
//        emailService.sendPlainEmail();
    }
}
