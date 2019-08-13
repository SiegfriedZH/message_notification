package com.gengyu.msgnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsgnotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgnotificationApplication.class, args);
	}

}
