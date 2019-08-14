package com.gengyu.msgnotification.service;

import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.entity.EmailTaskInfo;
import com.gengyu.msgnotification.enums.EmailTaskStatusEnum;
import com.gengyu.msgnotification.repository.EmailTaskInfoRepository;
import com.gengyu.msgnotification.timedJob.MyJob;
import com.gengyu.msgnotification.timedJob.MyJob2;
import com.gengyu.msgnotification.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 11:29
 */
@Service
@Slf4j
public class ScheduledTaskService {

    @Autowired
    private EmailTaskInfoRepository emailTaskInfoRepository;

    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    /**
     * 无参的定时任务
     */
    public void scheduledTest(){

        Date date = new Date();
        date.setTime(date.getTime() + 20000);   /// 把当前时间加20秒

        try {
            // 1、创建调度器Scheduler
//            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job1", "group1").build();
            // 3、构建Trigger实例,每隔1s执行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
//                    .startNow()//立即生效
                    .startAt(new Date())
                    .endAt(date)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(3)//每隔1s执行一次
//                            .withIntervalInMinutes(3)
                            .repeatForever()).build();//一直执行

            //4、执行
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("--------scheduler start ! ------------");
            scheduler.start();

            //睡眠
            TimeUnit.MINUTES.sleep(1);
            scheduler.shutdown();
            System.out.println("--------scheduler shutdown ! ------------");
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 有参的定时任务，将EmailInfo传给定时任务类，定时任务类再调用发邮件的Service。
     * @param emailInfo
     */
    @Transactional
    public void scheduledTask(EmailInfo emailInfo){

        try {

            String toList = emailInfo.getToList();
            String content = emailInfo.getContent();
            String subject = emailInfo.getSubject();
            String timeToSend = emailInfo.getTimeToSend();
            Date dateToSend = DateUtil.convertStrToDate(timeToSend);
            Integer interval = emailInfo.getIntervalTime(); /// 间隔的秒数

            String taskName = emailInfo.getTaskName();
            String jobName = "job"+taskName;
            String jobGroupName = "jgroup"+taskName;
            String triggerName = "tgr"+taskName;
            String triggerGroupName = "tgrgoup"+taskName;

            log.info("ScheduledTask方法接收到的参数为：{}, {}, {}, {}, {}, {}",
                    toList, content, subject, timeToSend, dateToSend, interval);

            // 1、创建调度器Scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)

            JobDataMap jobDataMap = new JobDataMap();
            // 还是按照对象绑定比较好，有利于后期增加属性
            jobDataMap.put("emailInfo", emailInfo);

//            jobDataMap.put("content", content);
//            jobDataMap.put("subject", subject);
//            jobDataMap.put("toList", toList);

            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class)
                    .setJobData(jobDataMap)     ///在此绑定数据，传给Job任务类
//                    .withIdentity("job1", "group1")
                    .withIdentity(jobName, jobGroupName)
                    .build();

            /// 在这里要进行一下startTime的校验，不能早于当前时间。只能大于等于当前时间。
            /// 且要加上几秒钟，作为程序运行时间缓冲。
            Date realDateToSend = DateUtil.validateDateTime(dateToSend);

            // 3、构建Trigger实例,每隔1s执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withIdentity("trigger1", "triggerGroup1")
                    .withIdentity(triggerName, triggerGroupName)
//                    .startNow()//立即生效
                    .startAt(realDateToSend)
//                    .endAt(date)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(5)//每隔5s执行一次
                            .withIntervalInSeconds(interval)
//                            .withIntervalInMinutes(3)
                            .repeatForever()).build();//一直执行    ///这里要不要改？？？？

            //4、执行
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("--------scheduler start ! ------------");
            scheduler.start();

            //睡眠
//            TimeUnit.MINUTES.sleep(3);
//            scheduler.shutdown();
//            System.out.println("--------scheduler shutdown ! ------------");
            /// 现在不用这种方式停止，而是一直定时发，直到调用方法取消任务，才停止！

            /// 入库操作
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


        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {

        try {
            Scheduler sched = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            log.info("现在已经删除任务===================");

            /// 从库中删除（实为修改状态）
            EmailTaskInfo emailTaskInfo = emailTaskInfoRepository.findByJobName(jobName);
            if(emailTaskInfo != null && emailTaskInfo.getStatus() == 0){
                log.info("=====得到的emailTaskInfo不为空，且status为0，现在修改状态");
                emailTaskInfoRepository.cancelEmailTask(emailTaskInfo.getId(),
                        EmailTaskStatusEnum.EMAIL_TASK_STATUS_ENUM_OFF.getCode());
                log.info("=====已修改状态为1");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
