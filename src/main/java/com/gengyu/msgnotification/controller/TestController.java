package com.gengyu.msgnotification.controller;

import com.gengyu.msgnotification.entity.EmailEntity;
import com.gengyu.msgnotification.entity.EmailInfo;
import com.gengyu.msgnotification.repository.EmailRepository;
import com.gengyu.msgnotification.entity.ResponseEntity;
import com.gengyu.msgnotification.service.EmailService;
import com.gengyu.msgnotification.service.ScheduledTaskService;
import com.gengyu.msgnotification.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Siegfried GENG
 * @date 2019/8/12 - 22:20
 */
@RestController
@RequestMapping("/email")
@Slf4j
public class TestController {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @GetMapping("/test01")
    public String test(){
        return "去你妹";
    }

    @GetMapping("/test02/{id}")
    public ResponseEntity<EmailRepository> getEmail(@PathVariable("id")Integer id){

        log.info("传入的id：{}", id);
//        System.out.println("id="+id);
        Optional<EmailEntity> optionalEmailEntity = emailRepository.findById(id);
        EmailEntity emailEntity = optionalEmailEntity.get();
        if(emailEntity != null){
            return ResultUtil.succeeded(emailEntity);
        }
        return ResultUtil.failed();
    }

    @GetMapping("/test03")
    public String test03(){
        emailService.sendPlainEmail();
        log.info("已发送");
        return "已发送";
    }

    @PostMapping("/test04")
    public String test04(@RequestBody EmailInfo emailInfo){
        log.info("传入的EmailInfo为：{}", emailInfo);
        emailService.sendRichEmail(emailInfo);
        log.info("已发送富文本邮件");
        return "已发送富文本邮件";
    }

    @GetMapping("/test05")
    public String test05(){
        scheduledTaskService.scheduledTest();
        return "test05";
    }

    @PostMapping("/test06")
    public String test06(@RequestBody EmailInfo emailInfo){

        log.info("接收到的前端emailInfo参数为：", emailInfo);
        if (emailInfo != null) {
            scheduledTaskService.scheduledTask(emailInfo);
        }
        return "test06";
    }

    @GetMapping("/test07")
    public String test07(@RequestParam("jobName") String jobName,
                         @RequestParam("jobGroupName") String jobGroupName,
                         @RequestParam("triggerName") String triggerName,
                         @RequestParam("triggerGroupName") String triggerGroupName){

        log.info("接收到的4个参数为：{}，{}，{}，{}", jobName, jobGroupName, triggerName, triggerGroupName);
        log.info("现在调用删除任务");
        scheduledTaskService.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
        log.info("删除任务完毕");
        return "test07";
    }

}
